package vn.xdeuhug.luckyMoney.app

import android.os.Bundle
import android.view.*
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.action.TitleBarAction

/**
 * @Author: Phạm Văn Nhân
 * @Date: 28/09/2022
 */
abstract class TitleBarFragment<A : AppActivity> : AppFragment<A>(), TitleBarAction {

    /** Thanh tiêu đề */
    private var titleBar: TitleBar? = null

    /** Thanh trạng thái */
    private var immersionBar: ImmersionBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleBar = getTitleBar()
        // Đặt trình nghe nhấp chuột trên thanh tiêu đề
        titleBar?.setOnTitleBarListener(this)

        if (isStatusBarEnabled()) {
            // Khởi tạo thanh trạng thái đắm chìm
            getStatusBarConfig().init()
            if (titleBar != null) {
                // Đặt chìm trong thanh tiêu đề
                ImmersionBar.setTitleBar(this, titleBar)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isStatusBarEnabled()) {
            // Khởi động lại thanh trạng thái
            getStatusBarConfig().init()
        }
    }

    /**
     * Có sử dụng nhập vai trong Fragment không
     */
    open fun isStatusBarEnabled(): Boolean {
        return false
    }

    /**
     * Nhận đối tượng cấu hình để ngâm thanh trạng thái
     */
    private fun getStatusBarConfig(): ImmersionBar {
        if (immersionBar == null) {
            immersionBar = createStatusBarConfig()
        }
        return immersionBar!!
    }

    /**
     * Khởi tạo đắm chìm
     */
    private fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this)
            // Màu phông chữ thanh trạng thái mặc định là màu đen
            .statusBarDarkFont(isStatusBarDarkFont())
            // Chỉ định màu nền của thanh điều hướng
            .navigationBarColor(R.color.white)
            // Phông chữ của thanh trạng thái và nội dung của thanh điều hướng tự động đổi màu. Bạn phải chỉ định màu của thanh trạng thái và màu của thanh điều hướng để tự động đổi màu.
            .autoDarkModeEnable(true, 0.2f)
    }

    /**
     * Nhận màu phông chữ trên thanh trạng thái
     */
    protected open fun isStatusBarDarkFont(): Boolean {
        // Trả về true cho phông chữ đen
        return getAttachActivity()!!.isStatusBarDarkFont()
    }

    override fun getTitleBar(): TitleBar? {
        if (titleBar == null || !isLoading()) {
            titleBar = obtainTitleBar(view as ViewGroup)
        }
        return titleBar
    }
}