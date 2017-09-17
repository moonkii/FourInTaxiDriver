package com.noah.taxidriver.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.R;

public class intro extends AppCompatActivity {


    //퍼미션 변수
    final int permissionRequestCodeForMap = 1000;
    Gson gson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        gson= new Gson();
        checkPermissions();
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

                splashEnd(); //액티비티 이동

            }

        } else {
            //마시멜로 미만 버전 ( 권한 요청 따로 구분 x)

        }

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



    private void splashEnd(){

        Handler handler = new Handler();

        int interval = 500; //스플레쉬 화면 시간

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(intro.this,Register_Activity.class);
                //인텐트로 이동

                startActivity(intent);
            }

        },interval);
    }

}
