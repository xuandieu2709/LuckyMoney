package vn.xdeuhug.luckyMoney.other

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class PreCachingLayoutManager : LinearLayoutManager {
    private var extraLayoutSpace = -1
    private var context: Context

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        this.context = context
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Timber.d("TAG %s", "meet a IOOBE in RecyclerView")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return if (extraLayoutSpace > 0) {
            extraLayoutSpace
        } else DEFAULT_EXTRA_LAYOUT_SPACE
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        try {
            val smoothScroller: RecyclerView.SmoothScroller =
                CenterSmoothScroller(recyclerView.context)
            smoothScroller.targetPosition = position
            startSmoothScroll(smoothScroller)
        } catch (e: Exception) {
            Timber.d("Error : %s", e.message)
        }
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }

    companion object {
        private const val DEFAULT_EXTRA_LAYOUT_SPACE = 600
    }
}