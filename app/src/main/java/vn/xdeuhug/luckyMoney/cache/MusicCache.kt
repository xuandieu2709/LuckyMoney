package vn.xdeuhug.luckyMoney.cache

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import vn.xdeuhug.luckyMoney.constants.AppConstants
import vn.xdeuhug.luckyMoney.model.eventbus.Music

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 15 / 01 / 2024
 */
object MusicCache {
    private var mMusicInfo = Music()

    private val mmkv: MMKV = MMKV.mmkvWithID("cache_music")

    fun getMusic(): Music {
        try {
            mMusicInfo =
                Gson().fromJson(mmkv.getString(AppConstants.CACHE_MUSIC, ""), Music::class.java)
        } catch (e: Exception) {
            e.stackTrace
        }
        return mMusicInfo
    }

    fun saveMusic(musicInfo: Music) {
        try {
            mmkv.remove(AppConstants.CACHE_MUSIC)
            mmkv.putString(AppConstants.CACHE_MUSIC, Gson().toJson(musicInfo)).commit()
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun isSaveCache():Boolean
    {
        val mMusicInfo = getMusic()
        return mMusicInfo.isSaveCache
    }
}