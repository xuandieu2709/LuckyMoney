package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.databinding.DialogLuckyMoneyBinding

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 11 / 01 / 2024
 */
class LuckyMoneyDialog {
    class Builder(context: Context,var typeMoney: Int) :
        BaseDialog.Builder<LuckyBoxDialog.Builder>(context) {
        private var binding: DialogLuckyMoneyBinding =
            DialogLuckyMoneyBinding.inflate(LayoutInflater.from(context))

        lateinit var onCompleted: OnCompleted

        fun onCompleted(onCompleted: OnCompleted): Builder = apply {
            this.onCompleted = onCompleted
        }

        companion object {
            const val D_1000 = 1000
            const val D_2000 = 2000
            const val D_5000 = 5000
            const val D_10000 = 10000
            const val D_20000 = 20000
            const val D_50000 = 50000
            const val D_100000 = 100000
            const val D_200000 = 200000
            const val D_500000 = 500000
        }

        init {
            setContentView(binding.root)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setCanceledOnTouchOutside(false)
            setBackgroundDimEnabled(true)
            setCancelable(false)
            setImageView()
            binding.btnReceiver.setOnClickListener {
                dismiss()
                onCompleted.onOpen()
            }
        }

        private fun setImageView() {
            when(typeMoney)
            {
                D_1000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_1000_dong)
                }
                D_2000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_2000_dong)
                }
                D_5000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_5000_dong)
                }
                D_10000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_10000_dong)
                }
                D_20000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_20000_dong)
                }
                D_50000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_50000_dong)
                }
                D_100000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_100000_dong)
                }
                D_200000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_200000_dong)
                }
                D_500000 ->{
                    binding.imvMoney.setImageResource(R.drawable.ic_500000_dong)
                }
            }
        }

        interface OnCompleted {
            fun onOpen()
        }
    }
}