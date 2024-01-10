package vn.xdeuhug.luckyMoney.other

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * @Author: Bùi Hữu Thắng
 * @Date: 30/08/2022
 */
class CenterSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(
        viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
    ): Int {
        return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2) +
                layoutManager!!.height / 7
    }
}