package vn.xdeuhug.luckyMoney.ui.activity

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rubikstudio.library.model.LuckyItem
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.databinding.ActivitySpinnerBinding
import vn.xdeuhug.luckyMoney.ui.dialog.LuckyBoxDialog
import vn.xdeuhug.luckyMoney.ui.dialog.LuckyMoneyDialog
import java.io.IOException
import java.util.Random


/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 11 / 01 / 2024
 */
class SpinnerActivity : AppActivity() {
    lateinit var binding: ActivitySpinnerBinding
    var data = ArrayList<LuckyItem>()
    private var twice = false

    private var mediaPlayer: MediaPlayer? = null

    // You can use this method to check if the activity is currently resumed or not
    override fun getLayoutView(): View {
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(33)
    override fun initView() {
        //
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            playAudioPlayer()
        }
    }

    private var lengthMP3 = 0

    override fun onPause() {
        super.onPause()
        mediaPlayer!!.pause()
        lengthMP3 = mediaPlayer!!.currentPosition
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
    }

    private fun playAudioPlayer() {
        //set up MediaPlayer
        try {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.bg_lucky_money)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun create(context: Context, musicID: Int): MediaPlayer {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
        }
        mediaPlayer = MediaPlayer.create(context, musicID)
        try {
            mediaPlayer?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return mediaPlayer!!
    }

    override fun initData() {
        initDataList()
    }

    private fun initDataList() {
        val luckyItem = LuckyItem()
        luckyItem.topText = "Hộp quà may mắn"
        luckyItem.icon = R.drawable.ic_gift_box
        luckyItem.color = -0x1f4e
        data.add(luckyItem)

        val luckyItem1 = LuckyItem()
        luckyItem1.topText = "1,000"
        luckyItem1.icon = R.drawable.ic_1000_dong
        luckyItem1.color = -0xc20
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.topText = "2,000"
        luckyItem2.icon = R.drawable.ic_2000_dong
        luckyItem2.color = -0x1f4e
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.topText = "5,000"
        luckyItem3.icon = R.drawable.ic_5000_dong
        luckyItem3.color = -0x3380
        data.add(luckyItem3)

        //////////////////

        //////////////////
        val luckyItem4 = LuckyItem()
        luckyItem4.topText = "10,000"
        luckyItem4.icon = R.drawable.ic_10000_dong
        luckyItem4.color = -0xc20
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.topText = "20,000"
        luckyItem5.icon = R.drawable.ic_20000_dong
        luckyItem5.color = -0x1f4e
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.topText = "50,000"
        luckyItem6.icon = R.drawable.ic_50000_dong
        luckyItem6.color = -0x3380
        data.add(luckyItem6)
        //////////////////

        //////////////////////
        //////////////////

        //////////////////////
        val luckyItem7 = LuckyItem()
        luckyItem7.topText = "100,000"
        luckyItem7.icon = R.drawable.ic_100000_dong
        luckyItem7.color = -0xc20
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.topText = "200,000"
        luckyItem8.icon = R.drawable.ic_200000_dong
        luckyItem8.color = -0x1f4e
        data.add(luckyItem8)


        val luckyItem9 = LuckyItem()
        luckyItem9.topText = "500,000"
        luckyItem9.icon = R.drawable.ic_500000_dong
        luckyItem9.color = -0x3380
        data.add(luckyItem9)
        ////////////////////////

        ////////////////////////
//        val luckyItem10 = LuckyItem()
//        luckyItem10.topText = "1000"
//        luckyItem10.icon = R.drawable.test10
//        luckyItem10.color = -0x1f4e
//        data.add(luckyItem10)
//
//        val luckyItem11 = LuckyItem()
//        luckyItem11.topText = "2000"
//        luckyItem11.icon = R.drawable.test10
//        luckyItem11.color = -0x1f4e
//        data.add(luckyItem11)
//
//        val luckyItem12 = LuckyItem()
//        luckyItem12.topText = "3000"
//        luckyItem12.icon = R.drawable.test10
//        luckyItem12.color = -0x1f4e
//        data.add(luckyItem12)

        /////////////////////


        /////////////////////
        binding.luckyWheel.setData(data)
        binding.luckyWheel.setRandomRound()

        binding.play.clickWithDebounce {
            val index = getRandomIndex()
            binding.luckyWheel.startLuckyWheelWithTargetIndex(index)
            binding.luckyWheel.isTouchEnabled = false
        }

        binding.luckyWheel.setLuckyRoundItemSelectedListener {
            if (it == 0) {
                LuckyBoxDialog.Builder(getContext()).onCompleted(object :
                    LuckyBoxDialog.Builder.OnCompleted {
                    override fun onOpen() {
                        LuckyMoneyDialog.Builder(getContext(), LuckyMoneyDialog.Builder.D_500000)
                            .onCompleted(object :
                                LuckyMoneyDialog.Builder.OnCompleted {
                                override fun onOpen() {
                                    //
                                }

                            }).create().show()
                    }

                }).create().show()
            } else {
                LuckyMoneyDialog.Builder(getContext(), data[it].topText.replace(",", "").toInt())
                    .onCompleted(object :
                        LuckyMoneyDialog.Builder.OnCompleted {
                        override fun onOpen() {
                            //
                        }

                    }).create().show()
            }
            postDelayed({
                binding.luckyWheel.isTouchEnabled = true
            },2000)
        }
    }

    private fun getRandomIndex(): Int {
        val rand = Random()
        return rand.nextInt(data.size - 1) + 0
    }

    private fun getRandomRound(): Int {
        val rand = Random()
        return rand.nextInt(10) + 15
    }
}