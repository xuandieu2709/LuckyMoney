package vn.xdeuhug.luckyMoney.ui.activity

import android.content.Intent
import android.view.View
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.other.AppConfig
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.databinding.SplashActivityBinding
import vn.xdeuhug.luckyMoney.manager.ActivityManager
import java.util.Locale


/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class SplashActivity : AppActivity() {
    private lateinit var binding: SplashActivityBinding

    override fun getLayoutView(): View {
        binding = SplashActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        //
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        ActivityManager.getInstance().finishAllActivities()
    }

    override fun initData() {
        binding.ivSplashDebug.let {
            it.setText(getString(R.string.beta).uppercase(Locale.getDefault()))
            it.visibility = View.INVISIBLE
        }
        goToHome()
    }


    //Di chuyển đến màn hình sau khi đăng nhập thành công
    private fun goToHome() {
        postDelayed({
            try {
                val intent = Intent(
                    this@SplashActivity,
                    HomeActivity::class.java
                )
                startActivity(intent)
                finish()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }, 3000)
    }

    override fun createStatusBarConfig(): ImmersionBar {
        return super.createStatusBarConfig()
            // Ẩn thanh trạng thái và thanh điều hướng
            .hideBar(BarHide.FLAG_HIDE_BAR)
    }

    override fun initActivity() {
        // Nếu Hoạt động hiện tại không phải là Hoạt động đầu tiên trong ngăn xếp tác vụ
        if (!isTaskRoot) {
            val intent: Intent? = intent
            // Nếu Hoạt động hiện tại được bắt đầu từ biểu tượng màn hình
            if (((intent != null) && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                        && (Intent.ACTION_MAIN == intent.action))
            ) {
                // Hủy Hoạt động hiện tại để tránh lặp lại việc khởi tạo mục nhập
                finish()
                return
            }
        }
        super.initActivity()
    }
}