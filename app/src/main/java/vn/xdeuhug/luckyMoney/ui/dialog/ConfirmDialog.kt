package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.text.HtmlCompat
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.databinding.DialogConfirmBinding

/**
 * @Author: Trần Tuấn Vũ
 * @Date: 11/01/2023
 */
class ConfirmDialog {
    class Builder constructor(
        context: Context,
        headerText: String,
        messageText: String
    ) :
        BaseDialog.Builder<Builder>(context) {
        private var binding: DialogConfirmBinding =
            DialogConfirmBinding.inflate(LayoutInflater.from(context))
        lateinit var onActionDone: OnActionDone

        fun onActionDone(onActionDone: OnActionDone): Builder = apply {
            this.onActionDone = onActionDone
        }

        init {
            setCancelable(false)
            setAnimStyle(AnimAction.ANIM_SCALE)
            setGravity(Gravity.CENTER)
            setContentView(binding.root)
            setWidth(Resources.getSystem().displayMetrics.widthPixels* 5 / 6)
            binding.tvHeader.text = headerText
            binding.tvContent.text =   HtmlCompat.fromHtml(
                messageText ,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            binding.btnCancel.setOnClickListener {
                binding.btnCancel.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.btnCancel.isEnabled = true
                }, 1000)
                dismiss()
                onActionDone.onActionDone(false)
            }
            binding.btnConfirm.setOnClickListener {
                binding.btnConfirm.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.btnConfirm.isEnabled = true
                }, 1000)
                dismiss()
                onActionDone.onActionDone(true)
            }
        }

        interface OnActionDone {
            fun onActionDone(isConfirm: Boolean)
        }
    }
}