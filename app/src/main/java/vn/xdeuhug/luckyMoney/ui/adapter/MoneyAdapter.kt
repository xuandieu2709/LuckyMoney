package vn.xdeuhug.luckyMoney.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.xdeuhug.base.action.KeyboardAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppAdapter
import vn.xdeuhug.luckyMoney.databinding.ItemMoneyBinding
import vn.xdeuhug.luckyMoney.model.eventbus.Money
import vn.xdeuhug.luckyMoney.utils.AppUtils

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 16 / 01 / 2024
 */
class MoneyAdapter(context: Context) : AppAdapter<Money>(context) {
    var onClickItem: OnClickItem? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding =
            ItemMoneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemMoneyBinding) : AppViewHolder(binding.root) {
        init {
            binding.btnMinus.setOnClickListener {
                binding.edtNumber.clearFocus()
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClickItem!!.onMinus(position, getItem(position))
                }
            }

            binding.btnPlus.setOnClickListener {
                binding.edtNumber.clearFocus()
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClickItem!!.onPlus(position, getItem(position))
                }
            }
            binding.edtNumber.addTextChangedListener(object : TextWatcher {
                private var isFormatting = false
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.edtNumber.length() > 2) {
                        binding.edtNumber.setText(
                            binding.edtNumber.text.toString().substring(0, 2)
                        )
                        binding.edtNumber.setSelection(binding.edtNumber.text!!.length)
                        toast(getString(R.string.max_number_99))
                    } else {
                        if (!isFormatting) {
                            if (binding.edtNumber.text.toString().isNotEmpty()) {
                                isFormatting = true
                                binding.edtNumber.removeTextChangedListener(this)
                                binding.edtNumber.setText("${binding.edtNumber.text.toString().toInt()}")
                                binding.edtNumber.addTextChangedListener(this)
                                isFormatting = false
                            } else {
                                binding.edtNumber.setText("0")
                            }
                            val position = bindingAdapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                val item = getItem(position)
                                item.number = binding.edtNumber.text.toString().toInt()
                            }

                        }
                        binding.edtNumber.setSelection(binding.edtNumber.text!!.length)
                    }
                }

                override fun afterTextChanged(arg0: Editable?) {
                    //
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //
                }
            })
        }

        override fun onBindView(position: Int) {
            val item = getItem(position)
            binding.edtNumber.setText("${item.number}")
            val imageRes = getContext().resources.getIdentifier(
                "ic_${item.money}_dong",
                "drawable",
                getContext().resources.getResourcePackageName(R.drawable.ic_launcher_foreground)
            )
            binding.imvMoney.setImageResource(imageRes)
        }

    }

    interface OnClickItem {
        fun onMinus(position: Int, item: Money)
        fun onPlus(position: Int, item: Money)
    }
}