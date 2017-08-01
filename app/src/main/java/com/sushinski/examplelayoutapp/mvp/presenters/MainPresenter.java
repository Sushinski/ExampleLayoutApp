package com.sushinski.examplelayoutapp.mvp.presenters;

import android.graphics.PointF;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sushinski.examplelayoutapp.mvp.views.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    // moscow point
    private static final double MSK_X = 55.6875502;
    private static final double MSK_Y = 37.5559557;
    private double mMapX = MSK_X;
    private double mMapY = MSK_Y;
    // click point cache
    private PointF mClickPoint = new PointF(0.f, 0.f);

    // called by map just created
    public void onBindMap() {
        getViewState().setMapCoordiantes(mMapX, mMapY);
        getViewState().setMarker(MSK_X, MSK_Y);
    }

    // called by map just moved
    public void onMoveMap(double x, double y){
        mMapX = x;
        mMapY = y;
        getViewState().setMapCoordiantes(mMapX, mMapY);
    }

    // called by map just clicked on marker point
    public void onMapMarkerClick(PointF click_point){
        mClickPoint = click_point;
        getViewState().clickMap(click_point);
    }
}
