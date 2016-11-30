package test.avick.com.mapviewplusviewpager.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.maps.android.ui.IconGenerator;

import test.avick.com.mapviewplusviewpager.Fragment.DummyFragment;
import test.avick.com.mapviewplusviewpager.R;
import test.avick.com.mapviewplusviewpager.adapter.DummyAdapter;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ANIMATE_SPEEED_TURN = 1000;
    private static final int BEARING_OFFSET = 20;

    private DummyAdapter mAdapter;
    private static boolean flag = true;
    public ArrayList<Marker> mMarkerList = new ArrayList<>();
    Marker prevMarker;
    String prevVendorName;
    boolean doubleBackToExitPressedOnce = false;
    private Map<Integer, Integer> mDealMap = new HashMap<>();
    double[][] latLongList = new double[][]{
            {37.771883, -122.405224},
            {37.773975, -122.40205},
            {37.772127, -122.404411},
            {37.77572, -122.41354}};
    private GoogleMap mMap;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mViewPager = (ViewPager) findViewById(R.id.item_pager);
        MapsInitializer.initialize(getApplicationContext());

        initializeViewPager();

    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        initMap();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if (flag) {
            flag = false;
            mViewPager.setVisibility(View.VISIBLE);
            String aid = marker.getId().substring(1, marker.getId().length());
            final String temp = Integer.parseInt(aid) + "";
            mViewPager.setCurrentItem(Integer.parseInt(aid));

            if (prevMarker != null && !marker.equals(prevMarker)) {
                IconGenerator iconFactory = new IconGenerator(MainActivity.this);
                iconFactory.setRotation(0);
                iconFactory.setBackground(null);
                View view = View.inflate(MainActivity.this, R.layout.map_marker_text, null);
                TextView tvVendorTitle;

                tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
                tvVendorTitle.setText(prevVendorName);
                tvVendorTitle.setBackground(getResources().getDrawable(R.mipmap.map_pin_white));
                tvVendorTitle.setTextColor(Color.parseColor("#0097a9"));

                iconFactory.setContentView(view);

                prevMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(temp)));

            }

            if (!marker.equals(prevMarker)) {

                IconGenerator iconFactory = new IconGenerator(MainActivity.this);
                iconFactory.setRotation(0);
                iconFactory.setBackground(null);
                View view = View.inflate(MainActivity.this, R.layout.map_marker_text_active, null);
                TextView tvVendorTitle;

                tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
                tvVendorTitle.setText(" " + (Integer.parseInt(marker.getSnippet()) + 1) + " ");
                iconFactory.setContentView(view);

                marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(temp)));
                prevMarker = marker;
                prevVendorName = " " + (Integer.parseInt(marker.getSnippet()) + 1) + " ";

            }
            prevMarker = marker;
            prevVendorName = " " + (Integer.parseInt(marker.getSnippet()) + 1) + " ";

            LatLng beginLatLng = prevMarker.getPosition();
            LatLng endLatLng = marker.getPosition();

            moveFocusAnimation(beginLatLng, endLatLng);

            flag = true;
        }


        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    void initializeViewPager() {
        mAdapter = new DummyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(50);
        mViewPager.setPadding(100, 14, 100, 14);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((DummyFragment) mAdapter.getItem(position)).makeFragmentSelected();
                if (position + 1 < 4) {
                    ((DummyFragment) mAdapter.getItem(position + 1)).removeFragmentSelected();
                }

                if (position - 1 >= 0) {
                    ((DummyFragment) mAdapter.getItem(position - 1)).removeFragmentSelected();
                }

                if (flag) {
                    flag = false;

                    LatLng newLatLng = new LatLng(latLongList[position][0], latLongList[position][1]);
                    if(prevMarker != null) {

                        LatLng beginLatLng = prevMarker.getPosition();// current point
                        LatLng endLatLng = newLatLng;// next point

                        moveFocusAnimation(beginLatLng, endLatLng);
                    }

                    Marker marker = mMarkerList.get(position);


                    if (prevMarker != null && !marker.equals(prevMarker)) {
                        IconGenerator iconFactory = new IconGenerator(MainActivity.this);
                        iconFactory.setRotation(0);
                        iconFactory.setBackground(null);
                        View view = View.inflate(MainActivity.this, R.layout.map_marker_text, null);
                        TextView tvVendorTitle;

                        tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
                        tvVendorTitle.setText(prevVendorName);
                        tvVendorTitle.setBackground(getResources().getDrawable(R.mipmap.map_pin_white));
                        tvVendorTitle.setTextColor(Color.parseColor("#0097a9"));


                        iconFactory.setContentView(view);
                        prevMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon((position + 1) + "")));

                    }

                    if (!marker.equals(prevMarker)) {


                        IconGenerator iconFactory = new IconGenerator(MainActivity.this);
                        iconFactory.setRotation(0);
                        iconFactory.setBackground(null);
                        View view = View.inflate(MainActivity.this, R.layout.map_marker_text_active, null);
                        TextView tvVendorTitle;

                        tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
                        tvVendorTitle.setText(" " + (position + 1) + " ");

                        iconFactory.setContentView(view);
                        //
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon()));
                        prevMarker = marker;
                        prevVendorName = " " + (position + 1) + " ";

                    }
                    prevMarker = marker;
                    prevVendorName = " " + (position + 1) + " ";
                    flag = true;
                } else {
                    Log.i("", "" + mMarkerList);
                    Log.i("", "" + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

    }

    public void initMap() {
        for (int j = 0; j < 4; j++) {
            LatLng newLatLngTemp = new LatLng(latLongList[j][0], latLongList[j][1]);

            MarkerOptions options = new MarkerOptions();
            IconGenerator iconFactory = new IconGenerator(MainActivity.this);
            iconFactory.setRotation(0);
            iconFactory.setBackground(null);

            View view = View.inflate(MainActivity.this, R.layout.map_marker_text, null);
            TextView tvVendorTitle;
            tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
            tvVendorTitle.setText(" " + (j + 1) + " ");
            iconFactory.setContentView(view);

            options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("1")));
            options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
            options.position(newLatLngTemp);
            options.snippet(String.valueOf(j));

            Marker mapMarker = mMap.addMarker(options);
            mMarkerList.add(mapMarker);
            mDealMap.put(j, j);

        }

        if (latLongList.length > 0) {
            LatLng latlngOne = new LatLng(latLongList[0][0], latLongList[0][1]);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngOne, 16));
            IconGenerator iconFactory = new IconGenerator(MainActivity.this);
            iconFactory.setRotation(0);
            iconFactory.setBackground(null);
            View view = View.inflate(MainActivity.this, R.layout.map_marker_text_active, null);
            TextView tvVendorTitle;

            tvVendorTitle = (TextView) view.findViewById(R.id.tv_vendor_title);
            tvVendorTitle.setText(" " + 1 + " ");

            iconFactory.setContentView(view);
            //
            mMarkerList.get(0).setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon()));
            prevMarker = mMarkerList.get(0);
            prevVendorName = " " + 1 + " ";
        }
    }


    void moveFocusAnimation(LatLng beginLatLng, LatLng endLatLng) {


//        float bearingL = (float) SphericalUtil.computeHeading(beginLatLng, endLatLng);
//        //bearingBetweenLatLngs(begin, end);

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(endLatLng)
                        .bearing(0)
                        //.bearing(bearingL + BEARING_OFFSET)
                        //.tilt(90)
                        .zoom(mMap.getCameraPosition().zoom)
                        .build();


        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                ANIMATE_SPEEED_TURN,
                null
        );
    }

}
