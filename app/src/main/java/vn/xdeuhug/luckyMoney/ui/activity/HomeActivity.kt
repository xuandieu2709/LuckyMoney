package vn.xdeuhug.luckyMoney.ui.activity

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import org.jetbrains.anko.startActivity
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.databinding.HomeActivityBinding
import kotlin.system.exitProcess

/**
 * @Author: Nguyễn Xuân Diệu
 * @Date: 12/01/2023
 */
class HomeActivity : AppActivity() {
    lateinit var binding: HomeActivityBinding
    private var twice = false

    // You can use this method to check if the activity is currently resumed or not
    override fun getLayoutView(): View {
        binding = HomeActivityBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(33)
    override fun initView() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (twice) {
                    exitProcess(0)
                }
                twice = true
                toast(getString(R.string.back_confirm))
                postDelayed({ twice = false }, 2000)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
        setClickButton()
    }

    private fun setClickButton() {
        binding.btnStart.clickWithDebounce {
            try {
                startActivity<SpinnerActivity>()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun initData() {
        //
    }


}