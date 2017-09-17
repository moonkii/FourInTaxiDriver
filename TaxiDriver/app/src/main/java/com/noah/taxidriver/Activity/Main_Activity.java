package com.noah.taxidriver.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxidriver.Dialog.Dialog_call;
import com.noah.taxidriver.MyFirebaseMessagingService;
import com.noah.taxidriver.R;

/**
 * Created by YH on 2017-09-12.
 */

public class Main_Activity extends Activity implements Dialog_call.CallOkClickListener{

    Button btn_empty;
    Button btn_driving;
    TextView status;

    Button btn_record;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_empty= (Button) findViewById(R.id.main_btn_empty);
        btn_driving = (Button) findViewById(R.id.main_btn_driving);
        status = (TextView) findViewById(R.id.status);
        btn_record = (Button) findViewById(R.id.main_myinfo);

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //여기 내정보 리스트(기사이름 ,번호 등등) 보여주고 탑승기록버튼 누르면 띄워야함 (일단 임시로 바로 탑승기록 띄움)
                // 즉 메뉴를 만들어야한다는 말

                Intent intent = new Intent(Main_Activity.this,Record_Activity.class);
                startActivity(intent);

            }
        });

        registerReceiver(myReceiver, new IntentFilter(MyFirebaseMessagingService.CALL_DRIVER));
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "왓어", Toast.LENGTH_SHORT).show();


            //FCM으로 사용자 이름, 전화번호 , 출발지,도착지,사용자 지정언어,토큰값 저장
            Dialog_call dialog_call = new Dialog_call(
                    Main_Activity.this,
                    "사용자 이름",
                    "사용자 번호",
                    "사용자 출발지",
                    "사용자 도착지",
                    "사용자 언어",
                    "사용자 토큰값",
                    Main_Activity.this

            );
            dialog_call.show();


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    //승객 요청 수락했을 때 처리
    @Override
    public void callOkResult() {

    }
}
