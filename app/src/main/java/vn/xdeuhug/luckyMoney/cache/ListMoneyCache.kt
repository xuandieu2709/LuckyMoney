package vn.xdeuhug.luckyMoney.cache

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import vn.xdeuhug.luckyMoney.constants.AppConstants
import vn.xdeuhug.luckyMoney.model.eventbus.Money

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 16 / 01 / 2024
 */
object ListMoneyCache {
    private var mListMoney = ArrayList<Money>()

    private val mmkv: MMKV = MMKV.mmkvWithID("caches_list_money_lucky_money_app")

    fun getList(): ArrayList<Money> {
        try {
            val jsonStr = mmkv.getString(AppConstants.CACHE_LIST_MONEY, "")
            if (!jsonStr.isNullOrEmpty()) {
                val listType = object : TypeToken<ArrayList<Money>>() {}.type
                mListMoney = Gson().fromJson(jsonStr, listType)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
        return mListMoney
    }

    fun saveList(moneyList: ArrayList<Money>) {
        try {
            mmkv.remove(AppConstants.CACHE_LIST_MONEY)
            mmkv.putString(AppConstants.CACHE_LIST_MONEY, Gson().toJson(moneyList)).commit()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}