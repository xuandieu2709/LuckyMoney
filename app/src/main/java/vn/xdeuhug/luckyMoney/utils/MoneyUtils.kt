package vn.xdeuhug.luckyMoney.utils

import com.hjq.toast.ToastUtils
import rubikstudio.library.model.LuckyItem
import vn.xdeuhug.luckyMoney.cache.ListMoneyCache
import vn.xdeuhug.luckyMoney.model.eventbus.Money
import kotlin.random.Random

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 17 / 01 / 2024
 */
object MoneyUtils {
    fun checkMoneyCacheExist(): Boolean {
        val listMoney = ListMoneyCache.getList().filter { it.number > 0 }
        return listMoney.isNotEmpty()
    }

    fun randomPosition(data: ArrayList<LuckyItem>): Int {
        val listMoney = ListMoneyCache.getList().filter { it.number > 0 }
        if (listMoney.isNotEmpty()) {
            val randomIndex = Random.nextInt(listMoney.size)
            val randomElement = listMoney[randomIndex]
            return if ((Random.nextInt((100 - 50) + 1) + 100) in 80..101) {
                0
            } else {
                data.indexOf(data.first {
                    it.topText.replace(
                        ",", ""
                    ) == randomElement.money.toString()
                })
            }
        } else {
            return -1
        }
    }

    fun getHighestMoney(): Money {
        return ListMoneyCache.getList().filter { it.number > 0 }.maxByOrNull { it.money }!!
    }

    fun minusNumberItem(item: Money, listMoney: ArrayList<Money>) {
        val itemFilter = listMoney.first { it.money == item.money }
        val index = listMoney.indexOf(itemFilter)
        itemFilter.number -= 1
        if (itemFilter.number < 0) itemFilter.number = 0
        listMoney[index] = itemFilter
        ListMoneyCache.saveList(listMoney)
    }
}