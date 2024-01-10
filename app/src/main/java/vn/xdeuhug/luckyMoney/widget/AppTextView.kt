package vn.xdeuhug.luckyMoney.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import vn.xdeuhug.luckyMoney.R

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
open class AppTextView : AppCompatTextView {
    var typeFont = ResourcesCompat.getFont(context, R.font.roboto_regular)

    constructor(context: Context?) : super(context!!) {
        setFontsTextView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        setFontsTextView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        setFontsTextView()
    }

    private fun setFontsTextView() {
        typeface = typeFont
    }
}