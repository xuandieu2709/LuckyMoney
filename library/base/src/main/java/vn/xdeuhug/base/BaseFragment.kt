package vn.xdeuhug.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import vn.xdeuhug.base.BaseActivity.OnActivityCallback
import vn.xdeuhug.base.action.BundleAction
import vn.xdeuhug.base.action.ClickAction
import vn.xdeuhug.base.action.HandlerAction
import vn.xdeuhug.base.action.KeyboardAction

/**
 * @Author: Bui Huu Thang
 * @Date: 28/09/2022
 */
abstract class BaseFragment<A : BaseActivity> : Fragment(), HandlerAction, ClickAction,
    BundleAction, KeyboardAction {

    /** Activity */
    private var activity: A? = null

    /** Bố cục góc */
    private var rootView: View? = null

    /** Hiện nó đã được tải chưa */
    private var loading: Boolean = false

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireActivity() as A
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        loading = false
        rootView = getLayoutView()
        initView()
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBusManager.getInstance().register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!loading) {
            loading = true
            initData()
            onFragmentResume(true)
            return
        }

        if (this.activity?.lifecycle?.currentState == Lifecycle.State.STARTED) {
            onActivityResume()
        } else {
            onFragmentResume(false)
        }
    }

    /**
     * Fragment gọi lại có thể nhìn thấy
     *
     * @param first                 Có nên gọi lần đầu tiên không
     */
    protected open fun onFragmentResume(first: Boolean) {}

    /**
     * Activity gọi lại có thể nhìn thấy
     */
    protected open fun onActivityResume() {}

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
    }

    override fun onDestroy() {
        EventBusManager.getInstance().unregister(this)
        super.onDestroy()
        loading = false
        removeCallbacks()
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    /**
     * 这个 Fragment nó đã được tải chưa
     */
    open fun isLoading(): Boolean {
        return loading
    }

    override fun getView(): View? {
        return rootView
    }

    /**
     * Lấy Activity liên kết để ngăn getActivity bị trống
     */
    open fun getAttachActivity(): A? {
        return activity
    }

    /**
     * Nhận đối tượng Ứng dụng
     */
    open fun getApplication(): Application? {
        activity?.let { return it.application }
        return null
    }

    /**
     * Lấy view bố cục
     */
    protected abstract fun getLayoutView(): View

    /**
     * Khởi tạo điều khiển
     */
    protected abstract fun initView()


    /**
     * Dữ liệu khởi tạo
     */
    protected abstract fun initData()


    /**
     * Nhận một đối tượng View dựa trên id tài nguyên
     */
    override fun <V : View?> findViewById(@IdRes id: Int): V? {
        return rootView?.findViewById(id)
    }

    override fun getBundle(): Bundle? {
        return arguments
    }

    /**
     * Jump Activity Lite
     */
    open fun startActivity(clazz: Class<out Activity>) {
        startActivity(Intent(context, clazz))
    }

    /**
     * Tối ưu hóa phương thức startActivityForResult
     */
    open fun startActivityForResult(clazz: Class<out Activity>, callback: OnActivityCallback?) {
        activity?.startActivityForResult(clazz, callback)
    }

    open fun startActivityForResult(intent: Intent, callback: OnActivityCallback?) {
        activity?.startActivityForResult(intent, null, callback)
    }

    open fun startActivityForResult(
        intent: Intent, options: Bundle?, callback: OnActivityCallback?
    ) {
        activity?.startActivityForResult(intent, options, callback)
    }

    /**
     * Hủy Hoạt động nơi chứa Phân mảnh hiện tại
     */
    open fun finish() {
        this.activity?.let {
            if (it.isFinishing || it.isDestroyed) {
                return
            }
            it.finish()
        }
    }

    /**
     * Điều phối sự kiện nút mảnh
     */
    open fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val fragments: MutableList<Fragment?> = childFragmentManager.fragments
        for (fragment: Fragment? in fragments) {
            // Fragment con này phải là một lớp con của BaseFragment và có thể nhìn thấy được
            if (fragment !is BaseFragment<*> || fragment.lifecycle.currentState != Lifecycle.State.RESUMED) {
                continue
            }
            // Gửi các sự kiện chính đến Fragment con để xử lý
            if (fragment.dispatchKeyEvent(event)) {
                // Nếu Fragment con chặn sự kiện này, nó sẽ không được chuyển giao cho Fragment cha để xử lý
                return true
            }
        }
        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> onKeyDown(event.keyCode, event)
            KeyEvent.ACTION_UP -> onKeyUp(event.keyCode, event)
            else -> false
        }
    }

    /**
     * Nút bấm sự kiện gọi lại
     */
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Theo mặc định, các sự kiện chính không bị chặn
        return false
    }

    /**
     * Nút gọi lại sự kiện
     */
    open fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // Theo mặc định, các sự kiện chính không bị chặn
        return false
    }

    override fun getContext(): Context? {
        return activity
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