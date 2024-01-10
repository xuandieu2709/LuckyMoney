package vn.xdeuhug.luckyMoney.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.action.StatusAction
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.databinding.ActivityBrowserBinding
import vn.xdeuhug.luckyMoney.widget.BrowserView
import vn.xdeuhug.luckyMoney.widget.StatusLayout
import java.lang.Exception

class BrowserActivity : AppActivity(), StatusAction, OnRefreshListener {
    companion object {

        const val INTENT_KEY_IN_URL: String = "url"
        const val TITLE: String = ""

        fun start(context: Context, url: String, title: String) {
            if (TextUtils.isEmpty(url)) {
                return
            }
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra(INTENT_KEY_IN_URL, url)
            intent.putExtra(TITLE, title)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private val hintLayout: StatusLayout? by lazy { findViewById(R.id.hl_browser_hint) }
    private val progressBar: ProgressBar? by lazy { findViewById(R.id.pb_browser_progress) }
    private val refreshLayout: SmartRefreshLayout? by lazy { findViewById(R.id.sl_browser_refresh) }
    private val browserView: BrowserView? by lazy { findViewById(R.id.wv_browser_view) }

    private lateinit var binding: ActivityBrowserBinding

    override fun getLayoutView(): View {
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        // 设置 WebView 生命管控
        browserView?.setLifecycleOwner(this)
        // 设置网页刷新监听
        refreshLayout?.setOnRefreshListener(this)
    }

    override fun initData() {
        showLoading()
        browserView?.apply {
            setBrowserViewClient(AppBrowserViewClient())
            setBrowserChromeClient(AppBrowserChromeClient(this))
            loadUrl(getString(INTENT_KEY_IN_URL)!!)
        }
        binding.tvTitle.title = getString(TITLE)
    }

    override fun getStatusLayout(): StatusLayout? {
        return hintLayout
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        browserView?.apply {
            if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
                // 后退网页并且拦截该事件
                goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onHttpSuccess(result: Any) {
        //
    }

    override fun onHttpFail(e: Exception) {
        //
    }


    private fun reload() {
        browserView?.reload()
    }

    /**
     * [OnRefreshListener]
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        reload()
    }

    private inner class AppBrowserViewClient : BrowserView.BrowserViewClient() {

        /**
         * 网页加载错误时回调，这个方法会在 onPageFinished 之前调用
         */
        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            // 这里为什么要用延迟呢？因为加载出错之后会先调用 onReceivedError 再调用 onPageFinished
            post {
                showError(object : StatusLayout.OnRetryListener {
                    override fun onRetry(layout: StatusLayout) {
                        reload()
                    }
                })
            }
        }

        /**
         * 开始加载网页
         */
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            progressBar?.visibility = View.VISIBLE
        }

        /**
         * 完成加载网页
         */
        override fun onPageFinished(view: WebView, url: String) {
            progressBar?.visibility = View.GONE
            refreshLayout?.finishRefresh()
            showComplete()
        }
    }
    private inner class AppBrowserChromeClient constructor(view: BrowserView) :
        BrowserView.BrowserChromeClient(view) {

        /**
         * 收到网页标题
         */
        override fun onReceivedTitle(view: WebView, title: String?) {
            if (title == null) {
                return
            }
            setTitle(title)
        }

        override fun onReceivedIcon(view: WebView, icon: Bitmap?) {
            if (icon == null) {
                return
            }
            setRightIcon(BitmapDrawable(resources, icon))
        }

        /**
         * 收到加载进度变化
         */
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            progressBar?.progress = newProgress
        }
    }
}