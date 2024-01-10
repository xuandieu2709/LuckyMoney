package vn.xdeuhug.luckyMoney.utils

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import vn.xdeuhug.luckyMoney.R

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
object TypeFaceUtils {
    private var robotoRegularTypeface: Typeface? = null
    fun getRobotoRegularTypeface(context: Context?): Typeface? {
        if (robotoRegularTypeface == null) {
            robotoRegularTypeface = ResourcesCompat.getFont(
                context!!, R.font.roboto_regular
            )
        }
        return robotoRegularTypeface
    }
}