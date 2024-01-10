package vn.xdeuhug.luckyMoney.other

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

/**
 * Add an action which will be invoked when the text is changing.
 *
 * @return the [EditText.onTextChangeListener] added to the [EditText]
 */
inline fun EditText.doAfterTextChanged(
    delay: Long = 500,
    crossinline onTextChangedDelayed: (text: String) -> Unit
) = onTextChangeListener(delay, onTextChangedDelayed)


/**
 * Add an action which will be invoked after the text changed.
 *
 * @return the [EditText.onTextChangeListener] added to the [EditText]
 */
inline fun EditText.onTextChangeListener(
    delay: Long,
    crossinline onTextChangedDelayed: (text: String) -> Unit
): TextWatcher {
    val listener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            handlerPostDelayedEdit(delay) { onTextChangedDelayed.invoke(s?.toString() ?: "") }
        }
    }
    this.addTextChangedListener(listener)
    return listener
}

var handlerDelayTimerEdit: Timer = Timer()

inline fun handlerPostDelayedEdit(delay: Long, crossinline onSuccess: () -> Unit) {
    handlerDelayTimerEdit.cancel()
    handlerDelayTimerEdit = Timer()
    handlerDelayTimerEdit.schedule(object : TimerTask() {
        override fun run() {
            Handler(Looper.getMainLooper()).post {
                onSuccess.invoke()
            }
        }
    }, delay)
}