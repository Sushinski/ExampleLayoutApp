package com.sushinski.examplelayoutapp;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sushinski.examplelayoutapp.interfaces.IMapTouchListener;
import com.sushinski.examplelayoutapp.mvp.presenters.MainPresenter;
import com.sushinski.examplelayoutapp.mvp.views.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity
        implements MainView,
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        IMapTouchListener{

    // Google map instance
    private GoogleMap mMap;
    SupportMapFragment mMapFragment;
    // click point coords
    private PointF mCurClickPoint;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigation;

    @OnClick(R.id.messages_fab)
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    // injects retained presenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MainPreneter")
    MainPresenter mMainPresenter;

    // map popup dialog offsets
    public static final int HOR_OFFSET = 100;
    public static final int VER_OFFSET = 130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        // apply custom action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.appbar_layout, null);
        getSupportActionBar().setCustomView(viewActionBar, params);
        // set title
        TextView title = (TextView) viewActionBar.findViewById(R.id.mytext);
        title.setText(R.string.registration);
        // init navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigation.setNavigationItemSelectedListener(this);
        // init google maps fragment
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // close navigation drawer on back first
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // calls presenter on map ready for map initialization
        mMainPresenter.onBindMap();
    }


    /**
     * Shows popup dialog on specified coords with offsets
     * @param p
     */
    public void showPopupWindow(PointF p){
        final PopupWindow mpopup;
        // inflate popup view
        View popUpView = getLayoutInflater().inflate(R.layout.map_marker, null);
        // compose popup window
        mpopup = new PopupWindow(popUpView, DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        // covert ancor coord to extract point coords
        Rect rc = new Rect();
        popUpView.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        popUpView.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // shop popup
        mpopup.showAtLocation(popUpView,
                Gravity.START|Gravity.TOP,
                rc.left+(int)p.x - HOR_OFFSET,
                rc.top + (int)p.y - VER_OFFSET);
        // sets action for popup button
        Button more = (Button) popUpView.findViewById(R.id.more_button);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with additional action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //mpopup.dismiss();
            }
        });
    }


    /**
     * Handles marker click
     * @param marker Marker clicked
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if(mCurClickPoint != null){
            mMainPresenter.onMapMarkerClick(mCurClickPoint);
        }
        return true;
    }

    /**
     * Map intermediate view click callback
     * @param p Coord of point clicked
     */
    @Override
    public void onMapTouchEvent(PointF p) {
        mCurClickPoint = p;
        if(mMap != null){
            LatLng ll = mMap.getCameraPosition().target;
            mMainPresenter.onMoveMap(ll.latitude, ll.longitude);
        }
    }

    /**
     * Sets map center coordinates
     * @param x lattitude
     * @param y longtitude
     */
    @Override
    public void setMapCoordiantes(double x, double y) {
        if(mMap != null) {
            LatLng msk = new LatLng(x, y);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(msk));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        }
    }

    /**
     * Sets marker at point specified
     * @param x lattitude
     * @param y longtitude
     */
    @Override
    public void setMarker(double x, double y) {
        if(mMap != null){
            mMap.addMarker(new MarkerOptions().position(new LatLng(x, y))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.f)));
        }
    }

    /**
     * Handles map click events
     * @param click_point point clicked
     */
    @Override
    public void clickMap(PointF click_point) {
        if(mMap != null) {
            showPopupWindow(mCurClickPoint);
        }
    }
}
