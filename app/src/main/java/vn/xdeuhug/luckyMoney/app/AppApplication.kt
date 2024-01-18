package vn.xdeuhug.luckyMoney.app

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.net.ConnectivityManager
import android.net.Network
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.hjq.bar.TitleBar
import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestInterceptor
import com.hjq.http.model.HttpHeaders
import com.hjq.http.model.HttpParams
import com.hjq.http.request.HttpRequest
import com.hjq.toast.ToastUtils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import io.socket.client.Socket
import net.gotev.uploadservice.data.UploadNotificationAction
import net.gotev.uploadservice.data.UploadNotificationConfig
import net.gotev.uploadservice.data.UploadNotificationStatusConfig
import net.gotev.uploadservice.extensions.flagsCompat
import net.gotev.uploadservice.extensions.getCancelUploadIntent
import okhttp3.OkHttpClient
import timber.log.Timber
import vn.xdeuhug.luckyMoney.http.model.RequestServer
import vn.xdeuhug.luckyMoney.other.*
import vn.xdeuhug.luckyMoney.ui.activity.HomeActivity
import vn.xdeuhug.luckyMoney.manager.ActivityManager
import vn.xdeuhug.luckyMoney.other.AppConfig
import vn.xdeuhug.luckyMoney.other.CrashHandler
import vn.xdeuhug.luckyMoney.other.DebugLoggerTree
import vn.xdeuhug.luckyMoney.other.TitleBarStyle
import vn.xdeuhug.luckyMoney.other.ToastLogInterceptor
import vn.xdeuhug.luckyMoney.other.ToastStyle
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.cache.HttpLoggerCache
import vn.xdeuhug.luckyMoney.http.model.RequestHandler


/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initSdk(this)
//        createNotificationChannels(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        widthView = Resources.getSystem().displayMetrics.widthPixels
        heightView = Resources.getSystem().displayMetrics.heightPixels
    }

    companion object {
        lateinit var kv: MMKV
        var instance: AppApplication? = null

        var widthView = 0
        var heightView = 0

        fun initSdk(application: Application) {
            // Đặt trình khởi tạo thanh tiêu đề
            TitleBar.setDefaultStyle(TitleBarStyle())

            SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, _: RefreshLayout? ->
                MaterialHeader(
                    application
                ).setColorSchemeColors(
                    ContextCompat.getColor(
                        application,
                        R.color.common_accent_color
                    )
                )
            }

            // Khởi tạo toast
            ToastUtils.init(application, ToastStyle())

            //đặt chế độ gỡ lỗi
            ToastUtils.setDebugMode(AppConfig.isDebug())

            // cài đặt Toast
            ToastUtils.setInterceptor(ToastLogInterceptor())

            // cài đặt Crash
            CrashHandler.register(application)

            //Lưu cache
            MMKV.initialize(application)

            // Activity
            ActivityManager.getInstance().init(application)


            // Create the Collector
            val chuckerCollector = ChuckerCollector(
                context = application,
                // Toggles visibility of the notification
                showNotification = true,
                // Allows to customize the retention period of collected data
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )

            // Create the Interceptor
            val chuckerInterceptor = ChuckerInterceptor.Builder(application)
                // The previously created Collector
                .collector(chuckerCollector)
                // The max body content length in bytes, after this responses will be truncated.
                .maxContentLength(250_000L)
                // List of headers to replace with ** in the Chucker UI
                .redactHeaders("Auth-Token", "Bearer")
                // Read the whole response body even when the client does not consume the response completely.
                // This is useful in case of parsing errors or when the response body
                // is closed before being read like in Retrofit with Void and Unit types.
                .alwaysReadResponseBody(true)
                // Use decoder when processing request and response bodies. When multiple decoders are installed they
                // are applied in an order they were added.
                // Controls Android shortcut creation.
                .build()

            // Khởi tạo khung yêu cầu mạng
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .apply {
                    if (HttpLoggerCache.getHttpLogEnable()) {
                        addInterceptor(chuckerInterceptor)
                    }
                }
                .build()


            //Khởi tạo máy chủ java
            EasyConfig.with(okHttpClient)
                // có in nhật ký không
                .setLogEnabled(AppConfig.isLogEnable())
                //Thiết lập cấu hình máy chủ
                .setServer(RequestServer())
                //Đặt chính sách xử lý yêu cầu
                .setHandler(RequestHandler(application))
                // Đặt bộ chặn tham số yêu cầu
                .setInterceptor(object : IRequestInterceptor {
                    @SuppressLint("HardwareIds")
                    override fun interceptArguments(
                        httpRequest: HttpRequest<*>,
                        params: HttpParams,
                        headers: HttpHeaders
                    ) {
                        headers.put("time", System.currentTimeMillis().toString())
                        headers.put("Content-Type", "application/json")
                        headers.put("Cache-Control", "no-store")
                        headers.put("Cache-Control", "no-cache")
                        headers.put(
                            "udid", Settings.Secure.getString(
                                application.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )
                        )
                        headers.put("os_name", "android_supplier")
                    }
                })
                // Đặt số lần yêu cầu thử lại
                .setRetryCount(1)
                // Đặt thời gian thử lại yêu cầu
                .setRetryTime(2000)
                .into()

            // Khởi tạo in nhật ký
            if (AppConfig.isLogEnable()) {
                Timber.plant(DebugLoggerTree())
            }

            // Đăng ký theo dõi thay đổi trạng thái mạng
            val connectivityManager: ConnectivityManager? =
                ContextCompat.getSystemService(application, ConnectivityManager::class.java)
            if (connectivityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object :
                    ConnectivityManager.NetworkCallback() {
                    @SuppressLint("MissingPermission")
                    override fun onLost(network: Network) {
                        val topActivity: Activity? = ActivityManager.getInstance().getTopActivity()
                        if (topActivity !is LifecycleOwner) {
                            return
                        }
                        val lifecycleOwner: LifecycleOwner = topActivity
                        if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) {
                            return
                        }
                        ToastUtils.show(R.string.common_network_error)
                    }
                })
            }

        }

    }

}