package com.sushinski.examplelayoutapp.fragment;

import com.google.android.gms.maps.SupportMapFragment;
import com.sushinski.examplelayoutapp.interfaces.IMapTouchListener;
import com.sushinski.examplelayoutapp.util.TouchableWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClickableSupportMapFragment extends SupportMapFragment {
    public View mOriginalContentView;
    public TouchableWrapper mTouchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mOriginalContentView = super.onCreateView(inflater, parent, savedInstanceState);
        Activity activity = getActivity();
        mTouchView = new TouchableWrapper(activity);
        if( activity instanceof IMapTouchListener ){
            mTouchView.setTouchListener((IMapTouchListener)activity );
        }
        mTouchView.addView(mOriginalContentView);
        return mTouchView;
    }

    @Override
    public View getView() {
        return mOriginalContentView;
    }
}
