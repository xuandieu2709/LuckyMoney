package vn.xdeuhug.luckyMoney.other

import android.content.*
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.xdeuhug.base.BaseAdapter
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.app.AppAdapter
import java.util.*

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/10/18
 *    desc   : 列表弹窗
 */
class ListPopup {

    class Builder(context: Context, positionChoose: Int) :
        BasePopupWindow.Builder<Builder>(context),
        BaseAdapter.OnItemClickListener {

        private var listener: OnListener<Any>? = null
        private var autoDismiss = true
        private val adapter: MenuAdapter

        init {
            val recyclerView = RecyclerView(context)
            recyclerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(recyclerView)
            adapter = MenuAdapter(getContext(), positionChoose)
            adapter.setOnItemClickListener(this)
            val dividerDrawable =
                ContextCompat.getDrawable(context, R.drawable.divider_with_padding)
            val dividerItemDecoration = DividerItemDecoration(
                dividerDrawable!!,
                getContext().resources.getDimension(R.dimen.dp_1).toInt(),
                ContextCompat.getColor(context, R.color.gray_100),
                getContext().resources.getDimension(R.dimen.dp_4).toInt()
            )

            recyclerView.addItemDecoration(dividerItemDecoration)
            recyclerView.adapter = adapter
            ArrowDrawable.Builder(context)
                .setArrowOrientation(Gravity.TOP)
                .setArrowGravity(Gravity.RIGHT)
                .setShadowSize(context.resources.getDimension(R.dimen.dp_10).toInt())
                .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                .apply(recyclerView)
        }

        override fun setGravity(gravity: Int): Builder = apply {
            when (gravity) {
                // 如果这个是在中间显示的
                Gravity.CENTER, Gravity.CENTER_VERTICAL -> {
                    // 重新设置动画
                    setAnimStyle(AnimAction.ANIM_SCALE)
                }
            }
            super.setGravity(gravity)
        }

        fun setList(vararg ids: Int): Builder = apply {
            val data: MutableList<Any> = ArrayList(ids.size)
            for (id in ids) {
                data.add(getContext().getString(id))
            }
            setList(data)
        }

        fun setList(vararg data: String): Builder = apply {
            setList(mutableListOf(*data))
        }

        private fun setList(data: MutableList<Any>): Builder = apply {
            adapter.setData(data)
        }

        fun setAutoDismiss(dismiss: Boolean): Builder = apply {
            autoDismiss = dismiss
        }

        @Suppress("UNCHECKED_CAST")
        fun setListener(listener: OnListener<out Any>?): Builder = apply {
            this.listener = listener as OnListener<Any>?
        }

        /**
         * [BaseAdapter.OnItemClickListener]
         */
        override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
            if (autoDismiss) {
                dismiss()
            }
            listener?.onSelected(getPopupWindow(), position, adapter.getItem(position))
        }
    }

    private class MenuAdapter(context: Context, var positionChoose: Int) :
        AppAdapter<Any>(context) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder()
        }

        private inner class ViewHolder : AppAdapter<Any>.AppViewHolder(R.layout.item_spinner) {

            private val textView: TextView? by lazy { findViewById(R.id.text1) }

            init {
                textView!!.setTextColor(getColor(R.color.black50))
                textView!!.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getContext().resources.getDimension(R.dimen.sp_16)
                )
            }

            override fun onBindView(position: Int) {
                textView!!.text = getItem(position).toString()
                if (position == positionChoose)
                    textView!!.setBackgroundColor(
                        getContext().resources.getColor(
                            R.color.gray_200,
                            null
                        )
                    )
                textView!!.setPaddingRelative(
                    getContext().resources.getDimension(R.dimen.dp_32).toInt(),
                    getContext().resources.getDimension(R.dimen.dp_8).toInt(),
                    getContext().resources.getDimension(R.dimen.dp_32).toInt(),
                    getContext().resources.getDimension(R.dimen.dp_8).toInt()
                )
            }
        }
    }

    class DividerItemDecoration(
        private val divider: Drawable,
        private val dividerHeight: Int,
        dividerColor: Int,
        private val padding: Int
    ) : RecyclerView.ItemDecoration() {

        private val paint = Paint()

        init {
            paint.color = dividerColor
            paint.style = Paint.Style.FILL
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)
            val left = parent.paddingLeft + padding
            val right = parent.width - parent.paddingRight - padding

            for (i in 0 until parent.childCount-1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + dividerHeight
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
                divider.draw(c)

            }
        }
    }

    interface OnListener<T> {

        /**
         * 选择条目时回调
         */
        fun onSelected(popupWindow: BasePopupWindow?, position: Int, data: T)
    }
}