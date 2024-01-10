package vn.xdeuhug.luckyMoney.ui.activity

import android.app.Activity
import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
class RestartActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RestartActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }

        fun restart(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)
        restart(this)
        finish()
    }
}