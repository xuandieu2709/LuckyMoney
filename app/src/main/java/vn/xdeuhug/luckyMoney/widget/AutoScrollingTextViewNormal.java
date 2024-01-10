package vn.xdeuhug.luckyMoney.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * @Author: Bùi Hữu Thắng
 * @Date: 30/08/2022
 */

public class AutoScrollingTextViewNormal extends AppTextView {
    public AutoScrollingTextViewNormal(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoScrollingTextViewNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollingTextViewNormal(Context context) {
        super(context);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
