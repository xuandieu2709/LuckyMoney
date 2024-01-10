package vn.xdeuhug.base.action

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

/**
 *    author : Bùi Hửu Thắng
 *    time   : 2022/09/17
 *    desc   : ActivityAction
 */
interface ActivityAction {

    fun getContext(): Context

    fun getActivity(): Activity? {
        var context: Context? = getContext()
        do {
            when (context) {
                is Activity -> {
                    return context
                }

                is ContextWrapper -> {
                    context = context.baseContext
                }

                else -> {
                    return null
                }
            }
        } while (context != null)
        return null
    }

    fun startActivity(clazz: Class<out Activity>) {
        startActivity(Intent(getContext(), clazz))
    }

    fun startActivity(intent: Intent) {
        if (getContext() !is Activity) {
            // Nếu ngữ cảnh hiện tại không phải là Activity, việc gọi startActivity phải thêm thẻ ngăn xếp tác vụ mới, nếu không sẽ báo lỗi: android.util.AndroidRuntimeException
            // Việc gọi startActivity () từ bên ngoài ngữ cảnh Hoạt động yêu cầu cờ FLAG_ACTIVITY_NEW_TASK. Đây có thực sự là điều bạn muốn?
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        getContext().startActivity(intent)
    }
}