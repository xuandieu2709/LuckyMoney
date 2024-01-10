package vn.xdeuhug.base.action

import vn.xdeuhug.base.R

/**
 *    author : Bùi Hửu Thắng
 *    time   : 2022/09/17
 */
interface AnimAction {

    companion object {

        const val ANIM_DEFAULT: Int = -1

        const val ANIM_EMPTY: Int = 0

        val ANIM_SCALE: Int = R.style.ScaleAnimStyle

        val ANIM_IOS: Int = R.style.IOSAnimStyle

        const val ANIM_TOAST: Int = android.R.style.Animation_Toast

        val ANIM_TOP: Int = R.style.TopAnimStyle

        val ANIM_BOTTOM: Int = R.style.BottomAnimStyle

        val ANIM_LEFT: Int = R.style.LeftAnimStyle

        val ANIM_RIGHT: Int = R.style.RightAnimStyle
    }
}