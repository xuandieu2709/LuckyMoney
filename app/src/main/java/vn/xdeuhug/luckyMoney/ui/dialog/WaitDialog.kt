package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2018/12/2
 *    desc   : 等待加载对话框
 */
class WaitDialog {

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context) {

//          private val messageView: TextView? by lazy { findViewById(R.id.tv_wait_message) }

        init {
            setContentView(R.layout.wait_dialog)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setCanceledOnTouchOutside(false)
            setBackgroundDimEnabled(true)
            setCancelable(false)
        }

        fun setMessage(text: CharSequence?): Builder = apply {
//            messageView?.text = text
//            messageView?.visibility = if (text == null) View.GONE else View.VISIBLE
        }
    }
}