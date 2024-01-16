package vn.xdeuhug.luckyMoney.ui.activity

import android.media.MediaPlayer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import org.jetbrains.anko.startActivity
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.cache.MusicCache
import vn.xdeuhug.luckyMoney.databinding.HomeActivityBinding
import vn.xdeuhug.luckyMoney.ui.dialog.SettingMoneyDialog
import vn.xdeuhug.luckyMoney.ui.dialog.SoundDialog
import kotlin.system.exitProcess

/**
 * @Author: Nguyễn Xuân Diệu
 * @Date: 12/01/2023
 */
class HomeActivity : AppActivity() {
    lateinit var binding: HomeActivityBinding
    private var twice = false
    //
    private var music = MusicCache.getMusic()
    //
    private var mediaPlayer: MediaPlayer? = null
    // You can use this method to check if the activity is currently resumed or not
    override fun getLayoutView(): View {
        binding = HomeActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
    }
    private var countResume = 0

    override fun onResume() {
        super.onResume()
        if(music.isSaveCache && music.sound)
        {
            if(countResume == 0)
            {
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.tap_notification)
//                mediaPlayer!!.start()
            }
            countResume++
        }
    }

    private fun playAudioPlayer() {
        //set up MediaPlayer
        if(music.isSaveCache && music.sound)
        {
            try {
                if(mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.tap_notification)
                }
                mediaPlayer!!.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
        binding.btnStart.clickWithDebounce(500) {
            playAudioPlayer()
            try {
                startActivity<SpinnerActivity>()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        binding.btnSound.clickWithDebounce(500) {
            playAudioPlayer()
            SoundDialog.Builder(getContext()).onCompleted(object : SoundDialog.Builder.OnCompleted{
                override fun onClose() {
                    //
                }

            }).create().show()
        }

        binding.btnSetting.clickWithDebounce(500) {
            playAudioPlayer()
            SettingMoneyDialog.Builder(getContext()).onCompleted(object : SettingMoneyDialog.Builder.OnCompleted{
                override fun onClose() {
                    //
                }

            }).create().show()
        }
    }

    override fun initData() {
        //
    }


}