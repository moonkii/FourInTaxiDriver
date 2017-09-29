package com.noah.taxidriver.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.MyFirebaseMessagingService;
import com.noah.taxidriver.R;
import com.noah.taxidriver.item_matching;

/**
 * Created by YH on 2017-09-18.
 */

public class Act_loding_matching extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_matching);
        registerReceiver(myReceiver, new IntentFilter(MyFirebaseMessagingService.CALL_DRIVER));
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String get_msg =  intent.getStringExtra("msg");
            Gson gson = new Gson();
            item_matching item =  gson.fromJson(get_msg,item_matching.class);
            if(item.getFlag().trim().equals("")) { //고객의 응답일 경우의 flag로 바꿔준다.
                String x = item.getX(); //위도
                String y = item.getY();// 경도
                String destination = item.getDestination(); //목적지
                String start_address = item.getStart_address(); //출발지
                String get_token = item.getToken(); //토큰

                //매칭 완료로 넘어가 줄 수 있도록 한다.
                //경로 화면으로 넘어간다.


            }
        }
    };
}
