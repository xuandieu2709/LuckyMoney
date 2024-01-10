package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2018/12/2
 *    desc   : 提示对话框
 */
class TipsDialog {

    companion object {
        const val ICON_WARNING: Int = R.drawable.tips_warning_ic
    }

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context),
        Runnable, BaseDialog.OnShowListener {

        private val messageView: TextView? by lazy { findViewById(R.id.tv_tips_message) }
        private val iconView: ImageView? by lazy { findViewById(R.id.iv_tips_icon) }

        private var duration = 2000

        init {
            setContentView(R.layout.tips_dialog)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setBackgroundDimEnabled(false)
            setCancelable(false)
            addOnShowListener(this)
        }

        fun setIcon(@DrawableRes id: Int): Builder = apply {
            iconView?.setImageResource(id)
        }

        fun setMessage(text: CharSequence?): Builder = apply {
            messageView?.text = text
        }

        override fun create(): BaseDialog {
            // 如果显示的图标为空就抛出异常
            requireNotNull(iconView?.drawable) { "The display type must be specified" }
            // 如果内容为空就抛出异常
            require(!TextUtils.isEmpty(messageView?.text.toString())) { "Dialog message not null" }
            return super.create()
        }

        override fun onShow(dialog: BaseDialog?) {
            // 延迟自动关闭
            postDelayed(this, duration.toLong())
        }

        override fun run() {
            if (!isShowing()) {
                return
            }
            dismiss()
        }
    }
}