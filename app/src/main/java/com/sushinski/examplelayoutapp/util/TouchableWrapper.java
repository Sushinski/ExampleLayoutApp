package com.sushinski.examplelayoutapp.util;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.sushinski.examplelayoutapp.interfaces.IMapTouchListener;

public class TouchableWrapper extends FrameLayout {
    private IMapTouchListener mListener;

    public TouchableWrapper(Context context) {
        super(context);
    }

    public void setTouchListener(IMapTouchListener listener){
        mListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                if(mListener != null) {
                    mListener.onMapTouchEvent(new PointF(event.getX(), event.getY()));
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
