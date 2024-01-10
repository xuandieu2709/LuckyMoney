package vn.xdeuhug.luckyMoney.ui.activity

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import rubikstudio.library.model.LuckyItem
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppActivity
import vn.xdeuhug.luckyMoney.databinding.HomeActivityBinding
import java.util.Random
import kotlin.system.exitProcess

/**
 * @Author: Nguyễn Xuân Diệu
 * @Date: 12/01/2023
 */
class HomeActivity : AppActivity() {
    lateinit var binding: HomeActivityBinding
    var data = ArrayList<LuckyItem>()
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

    }

    override fun initData() {
        initDataList()
    }

    private fun initDataList()
    {
        val luckyItem1 = LuckyItem()
        luckyItem1.topText = "100"
        luckyItem1.icon = R.drawable.test1
        luckyItem1.color = -0xc20
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.topText = "200"
        luckyItem2.icon = R.drawable.test2
        luckyItem2.color = -0x1f4e
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.topText = "300"
        luckyItem3.icon = R.drawable.test3
        luckyItem3.color = -0x3380
        data.add(luckyItem3)

        //////////////////

        //////////////////
        val luckyItem4 = LuckyItem()
        luckyItem4.topText = "400"
        luckyItem4.icon = R.drawable.test4
        luckyItem4.color = -0xc20
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.topText = "500"
        luckyItem5.icon = R.drawable.test5
        luckyItem5.color = -0x1f4e
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.topText = "600"
        luckyItem6.icon = R.drawable.test6
        luckyItem6.color = -0x3380
        data.add(luckyItem6)
        //////////////////

        //////////////////////
        //////////////////

        //////////////////////
        val luckyItem7 = LuckyItem()
        luckyItem7.topText = "700"
        luckyItem7.icon = R.drawable.test7
        luckyItem7.color = -0xc20
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.topText = "800"
        luckyItem8.icon = R.drawable.test8
        luckyItem8.color = -0x1f4e
        data.add(luckyItem8)


        val luckyItem9 = LuckyItem()
        luckyItem9.topText = "900"
        luckyItem9.icon = R.drawable.test9
        luckyItem9.color = -0x3380
        data.add(luckyItem9)
        ////////////////////////

        ////////////////////////
        val luckyItem10 = LuckyItem()
        luckyItem10.topText = "1000"
        luckyItem10.icon = R.drawable.test10
        luckyItem10.color = -0x1f4e
        data.add(luckyItem10)

        val luckyItem11 = LuckyItem()
        luckyItem11.topText = "2000"
        luckyItem11.icon = R.drawable.test10
        luckyItem11.color = -0x1f4e
        data.add(luckyItem11)

        val luckyItem12 = LuckyItem()
        luckyItem12.topText = "3000"
        luckyItem12.icon = R.drawable.test10
        luckyItem12.color = -0x1f4e
        data.add(luckyItem12)

        /////////////////////


        /////////////////////
        binding.luckyWheel.setData(data)
        binding.luckyWheel.setRound(5)

        /*luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
                luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
                luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
                luckyWheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/
        binding.play.clickWithDebounce {
            val index = getRandomIndex()
            binding.luckyWheel.startLuckyWheelWithTargetIndex(index)
        }

        binding.luckyWheel.setLuckyRoundItemSelectedListener{
            toast(data[it].topText)
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