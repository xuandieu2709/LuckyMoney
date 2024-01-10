package vn.xdeuhug.luckyMoney.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import vn.xdeuhug.base.BaseAdapter
import vn.xdeuhug.base.action.ToastAction
import vn.xdeuhug.luckyMoney.utils.AppUtils

/**
 * @Author: Bui Huu Thang
 * @Date: 28/09/2022
 */
abstract class AppAdapter<T> constructor(context: Context) :
    BaseAdapter<AppAdapter<T>.AppViewHolder>(context), ToastAction {

    /** Liệt kê dữ liệu */
    private var dataSet: MutableList<T> = ArrayList()

    /** Số trang của danh sách hiện tại, mặc định là trang đầu tiên, được sử dụng cho chức năng tải phân trang */
    private var pageNumber = 1

    /** Cho dù đó là trang cuối cùng, mặc định là false, được sử dụng cho chức năng tải phân trang */
    private var lastPage = false

    /** Thẻ đối tượng */
    private var tag: Any? = null

    override fun getItemCount(): Int {
        return getCount()
    }

    /**
     * Nhận tổng số dữ liệu
     */
    open fun getCount(): Int {
        return dataSet.size
    }

    /**
     * Thiết lập dữ liệu mới
     */
    @SuppressLint("NotifyDataSetChanged")
    open fun setData(data: MutableList<T>?) {
        if (data == null) {
            dataSet.clear()
        } else {
            dataSet = data
        }
        notifyDataSetChanged()
    }

    /**
     * Lấy dữ liệu hiện tại
     */
    open fun getData(): MutableList<T> {
        return dataSet
    }

    /**
     * nối một số dữ liệu
     */
    open fun addData(data: MutableList<T>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        dataSet.addAll(data)
        notifyItemRangeInserted(dataSet.size - data.size, data.size)
    }

    /**
     * Xóa dữ liệu hiện tại
     */
    @SuppressLint("NotifyDataSetChanged")
    open fun clearData() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    /**
     * Có bao gồm dữ liệu nhập tại một vị trí hay không
     */
    open fun containsItem(@IntRange(from = 0) position: Int): Boolean {
        return containsItem(getItem(position))
    }

    /**
     * Có bao gồm dữ liệu mục hay không
     */
    open fun containsItem(item: T?): Boolean {
        return if (item == null) {
            false
        } else dataSet.contains(item)
    }

    /**
     * Nhận dữ liệu tại một vị trí
     */
    open fun getItem(@IntRange(from = 0) position: Int): T {
        return dataSet[position]
    }

    open fun removeItem(@IntRange(from = 0) position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Lấy số trang hiện tại
     */
    open fun getPageNumber(): Int {
        return pageNumber
    }

    /**
     * Đặt số trang hiện tại
     */
    open fun setPageNumber(@IntRange(from = 0) number: Int) {
        pageNumber = number
    }

    /**
     * Hiện tại có phải là trang cuối cùng không
     */
    open fun isLastPage(): Boolean {
        return lastPage
    }

    /**
     * Đặt xem đó có phải là trang cuối cùng hay không
     */
    open fun setLastPage(last: Boolean) {
        lastPage = last
    }

    /**
     * Lấy điểm đánh dấu
     */
    open fun getTag(): Any? {
        return tag
    }

    /**
     * Đặt điểm đánh dấu
     */
    open fun setTag(tag: Any) {
        this.tag = tag
    }

    abstract inner class AppViewHolder : BaseViewHolder {

        constructor(@LayoutRes id: Int) : super(id)

        constructor(itemView: View) : super(itemView)

    }

    /**
     * Khởi tạo instance AppUtils
     */
    fun AppUtilsIAppUtilsInstancenstance(): AppUtils {
        return AppUtils
    }

    fun View.clickWithDebounce(debounceTime: Long = 1000L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }
}