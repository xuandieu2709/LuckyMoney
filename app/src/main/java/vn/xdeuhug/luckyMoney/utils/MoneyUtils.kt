package vn.xdeuhug.luckyMoney.utils

import vn.xdeuhug.luckyMoney.cache.ListMoneyCache

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 17 / 01 / 2024
 */
object MoneyUtils {
    fun checkMoneyCacheExist(): Boolean {
        val listMoney = ListMoneyCache.getList().filter { it.number > 0 }
        return listMoney.isNotEmpty()
    }

//    fun randomPosition(): Int {
//        val listMoney = ListMoneyCache.getList()
//
//        return listMoney.isNotEmpty()
//    }
}