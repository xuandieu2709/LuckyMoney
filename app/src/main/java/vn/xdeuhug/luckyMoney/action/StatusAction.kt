package vn.xdeuhug.luckyMoney.action

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.widget.StatusLayout

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/12/08
 *    desc   : 状态布局意图
 */
interface StatusAction {
    fun getStatusLayout(): StatusLayout?

    /**
     * 显示加载中
     */
    fun showLoading(@RawRes id: Int = R.raw.loading) {
        getStatusLayout()?.let {
            it.show()
            it.setAnimResource(id)
            it.setHint("")
            it.setOnRetryListener(null)
        }
    }

    /**
     * 显示加载完成
     */
    fun showComplete() {
        getStatusLayout()?.let {
            if (!it.isShow()) {
                return
            }
            it.hide()
        }
    }

    @SuppressLint("MissingPermission")
    fun showError(listener: StatusLayout.OnRetryListener?) {
        getStatusLayout()?.let {
            val manager: ConnectivityManager? = ContextCompat.getSystemService(it.context, ConnectivityManager::class.java)
            if (manager != null) {
                val network: Network? = manager.activeNetwork
                val capabilities: NetworkCapabilities? = manager.getNetworkCapabilities(network)
                // Check if the network is connected
                if (capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    showLayout(
                        R.drawable.status_network_ic,
                        R.string.status_layout_error_network,
                        listener
                    )
                    return
                }
            }

            showLayout(R.drawable.status_error_ic, R.string.status_layout_error_request, listener)
        }
    }

    /**
     * 显示自定义提示
     */
    fun showLayout(
        @DrawableRes drawableId: Int,
        @StringRes stringId: Int,
        listener: StatusLayout.OnRetryListener?
    ) {
        getStatusLayout()?.let {
            showLayout(
                ContextCompat.getDrawable(it.context, drawableId),
                it.context.getString(stringId),
                listener
            )
        }
    }

    fun showLayout(
        drawable: Drawable?,
        hint: CharSequence?,
        listener: StatusLayout.OnRetryListener?
    ) {
        getStatusLayout()?.let {
            it.show()
            it.setIcon(drawable)
            it.setHint(hint)
            it.setOnRetryListener(listener)
        }
    }
}