package vn.xdeuhug.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import vn.xdeuhug.base.action.*
import java.util.*
import kotlin.math.pow

/**
 *    author : Bùi Hửu Thắng
 *    time   : 2022/09/17
 *    desc   : BaseActivity
 */
abstract class BaseActivity : AppCompatActivity(), ActivityAction, ToastAction,
    ClickAction, HandlerAction, BundleAction, KeyboardAction {

    /** Activity Callbacks */
    private val activityCallbacks: SparseArray<OnActivityCallback?> by lazy { SparseArray(1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()

        EventBusManager.getInstance().register(this)
    }

    protected open fun initActivity() {
        initLayout()
        initView()
        initData()
    }

    /**
     * bố cục ID
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
     * Khởi tạo bố cục
     */
    protected open fun initLayout() {
        setContentView(getLayoutView())
        initSoftKeyboard()
    }

    /**
     * Khởi tạo bàn phím mềm
     */
    protected open fun initSoftKeyboard() {
        // Nhấp để ẩn bàn phím mềm bên ngoài để cải thiện trải nghiệm người dùng
        getContentView()?.setOnClickListener {
            // Ẩn phím chức năng để tránh rò rỉ bộ nhớ
            hideKeyboard(currentFocus)
        }
    }

    override fun onDestroy() {
        EventBusManager.getInstance().unregister(this)
        super.onDestroy()
        removeCallbacks()
    }

    override fun finish() {
        super.finish()
        // Ẩn phím chức năng để tránh rò rỉ bộ nhớ
        hideKeyboard(currentFocus)
    }

    /**
     *  Activity（singleTop Chế độ khởi động） Gọi lại khi được sử dụng lại
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // đặt lại Intent，Tránh khởi động lại Intent sau khi Activity bị giết hoặc khởi động lại ban đầu
        setIntent(intent)
    }

    override fun getBundle(): Bundle? {
        return intent.extras
    }

    /**
     *  setContentView
     */
    open fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    override fun getContext(): Context {
        return this
    }

    override fun startActivity(intent: Intent) {
        return super<AppCompatActivity>.startActivity(intent)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val fragments: MutableList<Fragment?> = supportFragmentManager.fragments
        for (fragment: Fragment? in fragments) {
            if (fragment !is BaseFragment<*> || fragment.lifecycle.currentState != Lifecycle.State.RESUMED) {
                continue
            }
            // Gửi các sự kiện quan trọng đến Fragment để xử lý
            if (fragment.dispatchKeyEvent(event)) {
                // Nếu Fragment chặn sự kiện này, nó sẽ không được Activity xử lý
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("deprecation")
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        //Ẩn phím chức năng để tránh rò rỉ bộ nhớ
        hideKeyboard(currentFocus)
        //startActivity cuối cùng sẽ gọi startActivityForResult
        super.startActivityForResult(intent, requestCode, options)
    }

    /**
     * startActivityForResult Tối ưu hóa phương pháp
     */
    open fun startActivityForResult(clazz: Class<out Activity>, callback: OnActivityCallback?) {
        startActivityForResult(Intent(this, clazz), null, callback)
    }

    open fun startActivityForResult(intent: Intent, callback: OnActivityCallback?) {
        startActivityForResult(intent, null, callback)
    }

    @Suppress("deprecation")
    open fun startActivityForResult(
        intent: Intent,
        options: Bundle?,
        callback: OnActivityCallback?
    ) {
        // Mã yêu cầu phải nằm trong khoảng từ 2 đến 16
        val requestCode: Int = Random().nextInt(2.0.pow(16.0).toInt())
        activityCallbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode, options)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("deprecation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var callback: OnActivityCallback?
        if ((activityCallbacks.get(requestCode).also { callback = it }) != null) {
            callback?.onActivityResult(resultCode, data)
            activityCallbacks.remove(requestCode)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface OnActivityCallback {

        /**
         * gọi lại kết quả
         *
         * @param resultCode mã kết quả
         * @param data       dữ liệu truyền đi
         */
        fun onActivityResult(resultCode: Int, data: Intent?)
    }

}