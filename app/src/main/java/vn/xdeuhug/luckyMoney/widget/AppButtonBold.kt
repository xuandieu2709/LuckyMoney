package vn.xdeuhug.luckyMoney.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import vn.xdeuhug.luckyMoney.R

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class AppButtonBold : AppCompatButton {
    private var typeFont = ResourcesCompat.getFont(context, R.font.roboto_medium)

    constructor(context: Context?) : super(context!!) {
        typeface = typeFont
        stateListAnimator = null
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        typeface = typeFont
        stateListAnimator = null
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        typeface = typeFont
        stateListAnimator = null
    }
}