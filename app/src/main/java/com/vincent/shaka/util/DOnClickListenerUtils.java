package com.vincent.shaka.util;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by mowen on 5/24/16.
 * 避免500毫秒以内的连续误点
 */
public class DOnClickListenerUtils implements View.OnClickListener {

    private long mLastClickTime = 0;
    View.OnClickListener onClickListener;

    public DOnClickListenerUtils(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        // Preventing multiple clicks, using threshold of 0.5 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        ///REAL ONCLICK
        onClickListener.onClick(v);
    }
}
