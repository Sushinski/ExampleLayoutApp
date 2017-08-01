package com.sushinski.examplelayoutapp.mvp.views;

import android.graphics.PointF;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    // sets map center point
    void setMapCoordiantes(double x, double y);
    //sets marker (call it once stategy used)
    @StateStrategyType(OneExecutionStateStrategy.class)
    void setMarker(double x, double y);
    // handle map clicks
    void clickMap(PointF click_point);
}
