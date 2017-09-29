package com.noah.taxidriver.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.R;

public class intro extends AppCompatActivity {


    //퍼미션 변수
    final int permissionRequestCodeForMap = 1000;
    Gson gson;
    boolean isGPSOn=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        gson= new Gson();

    }



    /*#####################################
     *      권한 체크 메소드
     * #####################################*/
    private void checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //API level 구분

            //마시멜로 이상인 경우

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //위치정보 권한
                ActivityCompat.requestPermissions(intro.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, permissionRequestCodeForMap);

            } else {
                //권한 모두 획득시
                if (checkGPSService()) {
                    splashEnd(); //액티비티 이동
                } else {
                    checkGPSDialog();
                }


            }

        } else {
            //마시멜로 미만 버전 ( 권한 요청 따로 구분 x)

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPermissions();
    }

    //권한 요청 결과 받는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){

            case permissionRequestCodeForMap :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //폰 상태권한 허용
                    checkPermissions();

                } else {
                    //폰상태 권한 거절시
                    finish();
                    Toast.makeText(this, "권한을 허용하셔야 서비스 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
                }

                break;

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

                                isGPSOn=true;

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
                        isGPSOn=false;
                        dialog.dismiss();
                        intro.this.finish();

                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void splashEnd(){

        Handler handler = new Handler();

        int interval = 3000; //스플레쉬 화면 시간

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(intro.this,Register_Activity.class);
                //인텐트로 이동

                startActivity(intent);
                intro.this.finish();
            }

        },interval);
    }

}
