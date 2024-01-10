package vn.xdeuhug.luckyMoney.other

import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.SearchView
import java.util.*

/**
 * Add an action which will be invoked when the text is changing.
 *
 * @return the [SearchView.OnQueryTextListener] added to the [SearchView]
 */
inline fun SearchView.doAfterTextChanged(
    delay: Long = 500,
    crossinline onTextChangedDelayed: (text: String) -> Unit
) = doOnQueryTextListener(delay, onTextChangedDelayed)


/**
 * Add an action which will be invoked after the text changed.
 *
 * @return the [SearchView.OnQueryTextListener] added to the [SearchView]
 */
inline fun SearchView.doOnQueryTextListener(
    delay: Long,
    crossinline onTextChangedDelayed: (text: String) -> Unit
): SearchView.OnQueryTextListener {
    val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = true
        override fun onQueryTextChange(newText: String?): Boolean {
            handlerPostDelayed(delay) { onTextChangedDelayed.invoke(newText ?: "") }
            return true
        }
    }
    this.setOnQueryTextListener(queryListener)
    return queryListener
}

var handlerDelayTimer: Timer = Timer()

inline fun handlerPostDelayed(delay: Long, crossinline onSuccess: () -> Unit) {
    handlerDelayTimer.cancel()
    handlerDelayTimer = Timer()
    handlerDelayTimer.schedule(object : TimerTask() {
        override fun run() {
            Handler(Looper.getMainLooper()).post {
                onSuccess.invoke()
            }
        }
    }, delay)
}