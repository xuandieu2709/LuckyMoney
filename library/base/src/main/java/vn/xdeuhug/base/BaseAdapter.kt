package vn.xdeuhug.base

import android.content.*
import android.util.SparseArray
import android.view.*
import android.view.View.OnLongClickListener
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.xdeuhug.base.action.ResourcesAction

/**
 *    author : Bùi Hửu Thắng
 *    time   : 2022/09/17
 *    desc   : RecyclerView cơ sở dữ liệu
 */
@Suppress("LeakingThis")
abstract class BaseAdapter<VH : BaseAdapter<VH>.BaseViewHolder>(private val context: Context) :
    RecyclerView.Adapter<VH>(), ResourcesAction {

    private var recyclerView: RecyclerView? = null

    /** sự kiện click ngắn */
    private var itemClickListener: OnItemClickListener? = null

    /** sự kiện click dài */
    private var itemLongClickListener: OnItemLongClickListener? = null

    /** sự kiện click ở View */
    private val childClickListeners: SparseArray<OnChildClickListener?> by lazy { SparseArray() }

    /** sự kiện click dài View */
    private val childLongClickListeners: SparseArray<OnChildLongClickListener?> by lazy { SparseArray() }

    /** ViewHolder */
    private var positionOffset: Int = 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        // So sánh vị trí bị ràng buộc bởi ViewHolder và vị trí đến
        // Nói chung, hai giá trị vị trí bằng nhau, nhưng có một trường hợp đặc biệt
        // Trong trường hợp thêm Head View vào lớp ngoài, hai giá trị vị trí không bằng nhau
        positionOffset = position - holder.bindingAdapterPosition
        holder.onBindView(position)
    }

    open fun getRecyclerView(): RecyclerView? {
        return recyclerView
    }

    override fun getContext(): Context {
        return context
    }


    abstract inner class BaseViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, OnLongClickListener {

        constructor(@LayoutRes id: Int) : this(
            LayoutInflater.from(getContext()).inflate(id, recyclerView, false)
        )

        init {
            // Đặt các sự kiện bấm và nhấn lâu cho một mục nhập
            if (itemClickListener != null) {
                itemView.setOnClickListener(this)
            }

            if (itemLongClickListener != null) {
                itemView.setOnLongClickListener(this)
            }

            // Đặt mục sự kiện nhấp vào lượt xem con
            for (i in 0 until childClickListeners.size()) {
                findViewById<View>(childClickListeners.keyAt(i))?.setOnClickListener(this)
            }

            // Đặt mục sự kiện báo chí dài lần xem phụ
            for (i in 0 until childLongClickListeners.size()) {
                findViewById<View>(childLongClickListeners.keyAt(i))?.setOnLongClickListener(this)
            }
            // Ký event Bus
            EventBusManager.getInstance().register(this)
        }

        /**
         * gọi lại liên kết dữ liệu
         */
        abstract fun onBindView(position: Int)

        /**
         * 获取 ViewHolder 位置
         */
        protected open fun getViewHolderPosition(): Int {
            // Đây là giải thích tại sao getLayoutPosition được sử dụng thay vì getAdapterPosition
            // Nếu bạn sử dụng getAdapterPosition, nó sẽ gây ra sự cố, đó là -1 sẽ xuất hiện khi bạn nhanh chóng nhấp để xóa mục nhập, vì ViewHolder đã không được liên kết
            // Sử dụng getLayoutPosition sẽ không làm cho vị trí là -1, vì nó sẽ không biến mất ngay lập tức trong bố cục sau khi hủy liên kết, vì vậy đừng lo lắng về việc vị trí bất thường trong quá trình thực thi hoạt ảnh
            return layoutPosition + positionOffset
        }

        /**
         * [View.OnClickListener]
         */
        override fun onClick(view: View) {
            val position: Int = getViewHolderPosition()
            if (position < 0 || position >= itemCount) {
                return
            }
            if (view === getItemView()) {
                itemClickListener?.onItemClick(recyclerView, view, position)
                return
            }
            childClickListeners.get(view.id)?.onChildClick(recyclerView, view, position)
        }

        /**
         * [View.OnLongClickListener]
         */
        override fun onLongClick(view: View): Boolean {
            val position: Int = getViewHolderPosition()
            if (position < 0 || position >= itemCount) {
                return false
            }
            if (view === getItemView()) {
                if (itemLongClickListener != null) {
                    return itemLongClickListener!!.onItemLongClick(recyclerView, view, position)
                }
                return false
            }
            val listener: OnChildLongClickListener? = childLongClickListeners.get(view.id)
            if (listener != null) {
                return listener.onChildLongClick(recyclerView, view, position)
            }
            return false
        }

        open fun getItemView(): View {
            return itemView
        }

        open fun <V : View?> findViewById(@IdRes id: Int): V? {
            return getItemView().findViewById(id)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        // Xác định xem trình quản lý bố cục hiện tại có trống không, nếu nó trống, hãy đặt trình quản lý bố cục mặc định
        if (this.recyclerView?.layoutManager == null) {
            this.recyclerView?.layoutManager = generateDefaultLayoutManager(context)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    /**
     * Tạo biểu tượng bố cục mặc định
     */
    protected open fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context)
    }

    /**
     * cài đặt RecyclerView mục nghe nhấp chuột
     */
    open fun setOnItemClickListener(listener: OnItemClickListener?) {
        checkRecyclerViewState()
        itemClickListener = listener
    }


    open fun setOnChildClickListener(@IdRes id: Int, listener: OnChildClickListener?) {
        checkRecyclerViewState()
        childClickListeners.put(id, listener)
    }


    open fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        checkRecyclerViewState()
        itemLongClickListener = listener
    }

    open fun setOnChildLongClickListener(@IdRes id: Int, listener: OnChildLongClickListener?) {
        checkRecyclerViewState()
        childLongClickListeners.put(id, listener)
    }


    private fun checkRecyclerViewState() {
        if (recyclerView != null) {
            throw IllegalStateException("are you ok?")
        }
    }

    interface OnItemClickListener {
        fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(recyclerView: RecyclerView?, itemView: View?, position: Int): Boolean
    }

    interface OnChildClickListener {
        fun onChildClick(recyclerView: RecyclerView?, childView: View?, position: Int)
    }

    interface OnChildLongClickListener {
        fun onChildLongClick(recyclerView: RecyclerView?, childView: View?, position: Int): Boolean
    }
}