package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.*
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/09/21
 *    desc   : 项目通用 Dialog 布局封装
 */
class CommonDialog {
    @Suppress("UNCHECKED_CAST", "LeakingThis")
    open class Builder<B : Builder<B>>(context: Context) : BaseDialog.Builder<B>(context) {

        private var autoDismiss = true

        private val containerLayout: ViewGroup? by lazy { findViewById(R.id.ll_ui_container) }
        private val titleView: TextView? by lazy { findViewById(R.id.tv_ui_title) }
        private val cancelView: TextView? by lazy { findViewById(R.id.tv_ui_cancel) }
        private val lineView: View? by lazy { findViewById(R.id.v_ui_line) }
        private val confirmView: TextView? by lazy { findViewById(R.id.tv_ui_confirm) }
        private val actionView: LinearLayout? by lazy { findViewById(R.id.lnAction) }
        private val viewTitle: View? by lazy { findViewById(R.id.view_title) }

        init {
            setContentView(R.layout.ui_dialog)
            setAnimStyle(AnimAction.ANIM_IOS)
            setGravity(Gravity.CENTER)
            setOnClickListener(cancelView, confirmView)
            setActionView(true)
            setTitleView(true)
        }

        fun setCustomView(@LayoutRes id: Int): B {
            return setCustomView(
                LayoutInflater.from(getContext()).inflate(id, containerLayout, false)
            )
        }

        fun setCustomView(view: View?): B {
            containerLayout?.addView(view, 1)
            return this as B
        }

        fun setActionView(boolean: Boolean): B {
            if (boolean) {
                actionView?.visibility = View.VISIBLE
            } else {
                actionView?.visibility = View.GONE

            }
            return this as B
        }

        fun setTitleView(boolean: Boolean): B {
            if (boolean) {

                viewTitle?.visibility = View.VISIBLE
                viewTitle?.visibility = View.VISIBLE
                containerLayout?.setPadding(0, 20, 0, 0)
            } else {
                viewTitle?.visibility = View.GONE
                containerLayout?.setPadding(0, 0, 0, 0)
            }
            return this as B
        }

        fun setButtonView(boolean: Boolean): B {
            if (boolean) {
                viewTitle?.visibility = View.VISIBLE
                actionView?.visibility = View.VISIBLE
                containerLayout?.setPadding(0, 20, 0, 0)
            } else {
                viewTitle?.visibility = View.GONE
                actionView?.visibility = View.GONE
                containerLayout?.setPadding(0, 0, 0, 0)
            }
            return this as B
        }

        fun setTitle(@StringRes id: Int): B {
            return setTitle(getString(id))
        }

        fun setTitle(text: CharSequence?): B {
            titleView?.text = text
            return this as B
        }

        fun setCancel(@StringRes id: Int): B {
            return setCancel(getString(id))
        }

        fun setCancel(text: CharSequence?): B {
            cancelView?.text = text
            lineView?.visibility =
                if (text == null || "" == text.toString()) View.GONE else View.VISIBLE
            return this as B
        }

        fun setConfirm(@StringRes id: Int): B {
            return setConfirm(getString(id))
        }

        fun setConfirm(text: CharSequence?): B {
            confirmView?.text = text
            return this as B
        }

        fun setAutoDismiss(dismiss: Boolean): B {
            autoDismiss = dismiss
            return this as B
        }

        fun autoDismiss() {
            if (autoDismiss) {
                dismiss()
            }
        }
    }
}