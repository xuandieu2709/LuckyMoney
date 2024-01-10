package vn.xdeuhug.luckyMoney.widget

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.util.AttributeSet
import android.webkit.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.ActivityAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.other.AppConfig
import vn.xdeuhug.luckyMoney.ui.dialog.InputDialog
import vn.xdeuhug.luckyMoney.ui.dialog.MessageDialog
import vn.xdeuhug.luckyMoney.ui.dialog.TipsDialog
import vn.xdeuhug.widget.layout.NestedScrollWebView
import java.util.*

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
@Suppress("SetJavaScriptEnabled")
class BrowserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle, defStyleRes: Int = 0
) :
    NestedScrollWebView(context, attrs, defStyleAttr, defStyleRes),
    LifecycleEventObserver, ActivityAction {

    companion object {

        init {
            // WebView 调试模式开关
            setWebContentsDebuggingEnabled(AppConfig.isDebug())
        }
    }

    init {
        val settings: WebSettings = settings
        // 允许文件访问
        settings.allowFileAccess = true
        // 允许网页定位
        settings.setGeolocationEnabled(true)
        // 允许保存密码
        //settings.setSavePassword(true);
        // 开启 JavaScript
        settings.javaScriptEnabled = true
        // 允许网页弹对话框
        settings.javaScriptCanOpenWindowsAutomatically = true
        // 加快网页加载完成的速度，等页面完成再加载图片
        settings.loadsImagesAutomatically = true
        // 本地 DOM 存储（解决加载某些网页出现白板现象）
        settings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 解决 Android 5.0 上 WebView 默认不允许加载 Http 与 Https 混合内容
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // 不显示滚动条
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
    }

    /**
     * 获取当前的 url
     *
     * @return      返回原始的 url，因为有些url是被WebView解码过的
     */
    override fun getUrl(): String? {
        // 避免开始时同时加载两个地址而导致的崩溃
        return super.getOriginalUrl() ?: return super.getUrl()
    }

    /**
     * 设置 WebView 生命管控（自动回调生命周期方法）
     */
    fun setLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    /**
     * [LifecycleEventObserver]
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_STOP -> onPause()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            else -> {}
        }
    }

    /**
     * 销毁 WebView
     */
    fun onDestroy() {
        // 停止加载网页
        stopLoading()
        // 清除历史记录
        clearHistory()
        // 取消监听引用
        setBrowserChromeClient(null)
        setBrowserViewClient(null)
        // 移除WebView所有的View对象
        removeAllViews()
        // 销毁此的WebView的内部状态
        destroy()
    }

    /**
     * 已过时
     */
    @Deprecated(
        "请使用 {@link BrowserViewClient}", ReplaceWith(
            "super.setWebViewClient(client)",
            "com.hjq.widget.layout.NestedScrollWebView"
        )
    )
    override fun setWebViewClient(client: WebViewClient) {
        super.setWebViewClient(client)
    }

    fun setBrowserViewClient(client: BrowserViewClient?) {
        if (client == null) {
            super.setWebViewClient(WebViewClient())
            return
        }
        super.setWebViewClient(client)
    }

    /**
     * 已过时
     */
    @Deprecated(
        "请使用 {@link BrowserChromeClient}", ReplaceWith(
            "super.setWebChromeClient(client)",
            "com.hjq.widget.layout.NestedScrollWebView"
        )
    )
    override fun setWebChromeClient(client: WebChromeClient?) {
        super.setWebChromeClient(client)
    }

    fun setBrowserChromeClient(client: BrowserChromeClient?) {
        super.setWebChromeClient(client)
    }

    open class BrowserViewClient : WebViewClient() {

        /**
         * 网站证书校验错误
         */
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            // 如何处理应用中的 WebView SSL 错误处理程序提醒：https://support.google.com/faqs/answer/7071387?hl=zh-Hans
            MessageDialog.Builder(view.context)
                .setMessage(R.string.common_web_ssl_error_title)
                .setConfirm(R.string.common_web_ssl_error_allow)
                .setCancel(R.string.common_web_ssl_error_reject)
                .setCancelable(false)
                .setListener(object : MessageDialog.OnListener {

                    override fun onConfirm(dialog: BaseDialog?) {
                        handler.proceed()
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                        handler.cancel()
                    }
                })
                .show()
        }

        /**
         * 同名 API 兼容
         */
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            if (request.isForMainFrame) {
                onReceivedError(
                    view,
                    error.errorCode,
                    error.description.toString(),
                    request.url.toString()
                )
            }
        }

        /**
         * 加载错误
         */
        @Deprecated("Deprecated in Java", ReplaceWith(
            "super.onReceivedError(view, errorCode, description, failingUrl)",
            "vn.xdeuhug.luckyMoney.webkit.WebViewClient"
        )
        )
        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
        }

        /**
         * 同名 API 兼容
         */
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return shouldOverrideUrlLoading(view, request.url.toString())
        }

        /**
         * 跳转到其他链接
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Timber.i("WebView shouldOverrideUrlLoading：%s", url)
            val scheme: String = Uri.parse(url).scheme ?: return false
            when (scheme) {
                "http", "https" -> view.loadUrl(url)
                "tel" -> dialing(view, url)
            }
            return true
        }

        /**
         * 跳转到拨号界面
         */
        private fun dialing(view: WebView, url: String) {
            val context: Context = view.context
            MessageDialog.Builder(context)
                .setMessage(
                    String.format(
                        view.resources.getString(R.string.common_web_call_phone_title),
                        url.replace("tel:", "")
                    )
                )
                .setConfirm(R.string.common_web_call_phone_allow)
                .setCancel(R.string.common_web_call_phone_reject)
                .setCancelable(false)
                .setListener(object : MessageDialog.OnListener {

                    override fun onConfirm(dialog: BaseDialog?) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(url)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                })
                .show()
        }
    }

    open class BrowserChromeClient constructor(private val webView: BrowserView) :
        WebChromeClient() {

        /**
         * 网页弹出警告框
         */
        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            val activity: Activity = webView.getActivity() ?: return false
            TipsDialog.Builder(activity)
                .setIcon(TipsDialog.ICON_WARNING)
                .setMessage(message)
                .setCancelable(false)
                .addOnDismissListener(object : BaseDialog.OnDismissListener {

                    override fun onDismiss(dialog: BaseDialog?) {
                        result.confirm()
                    }
                })
                .show()
            return true
        }

        /**
         * 网页弹出确定取消框
         */
        override fun onJsConfirm(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            val activity: Activity = webView.getActivity() ?: return false
            MessageDialog.Builder(activity)
                .setMessage(message)
                .setCancelable(false)
                .setListener(object : MessageDialog.OnListener {

                    override fun onConfirm(dialog: BaseDialog?) {
                        result.confirm()
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                        result.cancel()
                    }
                })
                .show()
            return true
        }

        /**
         * 网页弹出输入框
         */
        override fun onJsPrompt(
            view: WebView,
            url: String,
            message: String,
            defaultValue: String,
            result: JsPromptResult
        ): Boolean {
            val activity: Activity = webView.getActivity() ?: return false
            InputDialog.Builder(activity)
                .setContent(defaultValue)
                .setHint(message)
                .setCancelable(false)
                .setListener(object : InputDialog.OnListener {

                    override fun onConfirm(dialog: BaseDialog?, content: String) {
                        result.confirm(content)
                    }

                    override fun onCancel(dialog: BaseDialog?) {
                        result.cancel()
                    }
                })
                .show()
            return true
        }


    }
}