package com.noah.taxidriver.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.noah.taxidriver.Network;
import com.noah.taxidriver.R;
import com.noah.taxidriver.item_matching;

import java.util.Locale;

/**
 * Created by YH on 2017-09-30.
 */

public class Matched_driver extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        ,LocationListener
//        ,DrawRoute.onDrawRoute
{

    /*#####################################
    *          전역 변수
    * #####################################*/

    GoogleMap map; //구글맵 참조 변수
    GoogleApiClient mGoogleApiClient;
    Location myLocation; //구글맵 위치 변수
    LocationRequest mLocationRequest; //구글맵 위치 정보 요청 변수
    PendingResult<LocationSettingsResult> result;
    Geocoder geocoder; //주소 지명 찾는 변수

    //위도 경도
    double latitude = 0;
    double longitude = 0;
    boolean isFirst=false;

    Button go;
    Handler handler;
    TextView where,destination;
    String token;
    String x;
    String y;
    int lang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matched_driver);
        handler = new send_message_handler();
        go = (Button)findViewById(R.id.go);

        where = (TextView)findViewById(R.id.where);
        destination = (TextView)findViewById(R.id.destination);
        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        final String end = intent.getStringExtra("end");
        token = intent.getStringExtra("token");
        x = intent.getStringExtra("x");
        y = intent.getStringExtra("y");
        where.setText(start);
        destination.setText(end);
        lang=intent.getIntExtra("lang",0);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_matching item_matching = new item_matching("get_client",token);

                Gson gson = new Gson();
                String send = gson.toJson(item_matching);
                Log.i("뱐ㅇ러ㅏ",send);
                Network.push(send,Matched_driver.this,handler);

                Intent i = new Intent(Matched_driver.this,Main_Activity.class);
                i.putExtra("lang",1);
                i.putExtra("des",end);
                startActivity(i);
                Main_Activity.isDriving=true;
                Matched_driver.this.finish();

            }
        });


    }


    private void updateMapForCustomer(){
        LatLng Loc = new LatLng(Double.parseDouble(x), Double.parseDouble(y)); //위도 경도 지정
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Loc, 17)); //이동

            //마커 달기
            MarkerOptions options = new MarkerOptions();
            options.position(Loc);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pindpi)); //BitmapDescriptorFactory.fromResource(R.drawable.station))
            options.title("승객"); // info window의 타이틀
            map.addMarker(options);
            Marker mk1 = map.addMarker(options);
            mk1.showInfoWindow();

        }
    }


    private void updateMapForMyLocation(double myLatitude, double myLongtitude) {


        LatLng Loc = new LatLng(myLatitude, myLongtitude); //위도 경도 지정
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Loc, 17)); //이동

            //마커 달기
            map.clear();
            MarkerOptions options = new MarkerOptions();
            options.position(Loc);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_pindpi)); //BitmapDescriptorFactory.fromResource(R.drawable.station))
            options.title("내위치"); // info window의 타이틀
            map.addMarker(options);
            Marker mk1 = map.addMarker(options);
            mk1.showInfoWindow();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings mapUi = map.getUiSettings();
        mapUi.setZoomControlsEnabled(true); //줌 기능

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("##위치요청", "커넥트됨");
        if (ActivityCompat.checkSelfPermission(Matched_driver.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.v("##위치요청", "정보요청!!!");
            //위치정보 요청
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        latitude = myLocation.getLatitude();
        longitude = myLocation.getLongitude();

        if(!isFirst){
            updateMapForCustomer();
            isFirst=true;
        }



    }

//    @Override
//    public void afterDraw(String result) {
//
//    }

    class send_message_handler extends Handler {//매칭의 모든 것을 Network하나로 하기 때문에,
        //응답후 결과는 모두 핸들 메시지를 사용해야함.
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0 :
                    Toast.makeText(getApplicationContext(), "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();

                case 1 :
                    Intent i = new Intent(Matched_driver.this,Main_Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);



            }
        }
    }


    //구글 맵 생성
    private void initGoogleMap() {

        Log.v("##Frag_first", "initgooglemap");

        //지명 찾기용
        geocoder = new Geocoder(this, Locale.KOREAN);

        MapFragment mapFr = (MapFragment)getFragmentManager() .findFragmentById(R.id.map);
        mapFr.getMapAsync(Matched_driver.this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //위치정보 요청 설정
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000) //업데이트 간격
                .setFastestInterval(5000) //가장 빠른 업데이트 간격
                .setSmallestDisplacement(100); //이동 거리를 기준으로 업데이트 (미터)

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Log.v("##구글맵 설정", "" + locationSettingsResult.getStatus().toString());
            }
        });
//
//
//        DrawRoute.getInstance(this,Matched_driver.this).setFromLatLong(latitude,longitude)
//                .setToLatLong(Double.parseDouble(x),Double.parseDouble(y)).setGmapAndKey("MapandroidKey",map).run();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkGPSService()) {
            initGoogleMap();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        } else {
            checkGPSDialog();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        //구글 플레이서비스에 연결 해제
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

    }

    public boolean checkGPSService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return false;

        } else {
            return true;
        }


    }


    private void checkGPSDialog() {



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("위치 서비스(GPS)를 설정해주세요")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼



                                // GPS설정 화면으로 이동
                                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                startActivity(intent);
                                dialog.dismiss();

                            }
                        })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Matched_driver.this.finish();
                        Toast.makeText(Matched_driver.this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();


                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
