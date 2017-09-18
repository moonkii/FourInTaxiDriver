package com.noah.taxidriver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by YH on 2017-09-12.
 */

public class Main_Activity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(myReceiver, new IntentFilter(MyFirebaseMessagingService.CALL_DRIVER));
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String get_msg =  intent.getStringExtra("msg");
            Gson gson = new Gson();
           item_call_driver item =  gson.fromJson(get_msg,item_call_driver.class);
            if(item.getFlag().trim().equals("call_driver")) { //운전자가 고객을 불렀을 경우. flag에 call_driver를 사용한다.
                String x = item.getX(); //위도
                String y = item.getY();// 경도
                String destination = item.getDestination(); //목적지
                String start_address = item.getStart_address(); //출발지
                String get_token = item.getToken(); //토큰

                //get_msg를 파싱해야한다.
                Log.i("Main_Activity", "받은데이터 : " + get_msg);
                Call_driver_dialog a = new Call_driver_dialog(getApplicationContext(), start_address, destination,x,y,get_token);
                a.show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
