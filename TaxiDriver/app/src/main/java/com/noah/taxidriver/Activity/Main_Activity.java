package com.noah.taxidriver.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.Call_driver_dialog;
import com.noah.taxidriver.Dialog.Dialog_call;
import com.noah.taxidriver.MyFirebaseMessagingService;
import com.noah.taxidriver.R;
import com.noah.taxidriver.item_matching;

/**
 * Created by YH on 2017-09-12.
 */

public class Main_Activity extends Activity implements Dialog_call.CallOkClickListener{

    Button btn_empty;
    Button btn_driving;

    TextView status;
    TextToSpeech tts;

    ImageButton btn_record;
    SharedPreferences local;
    SharedPreferences.Editor editor;
    public static boolean isDriving=false;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_empty= (Button) findViewById(R.id.main_btn_empty);
        btn_driving = (Button) findViewById(R.id.main_btn_driving);
        btn_record = (ImageButton) findViewById(R.id.main_guestlist);

        Button end = (Button)findViewById(R.id.main_out);
        end.findViewById(R.id.main_out);
        local= getSharedPreferences("Driver",MODE_PRIVATE);
        //무조건 빈차로 해버린다.
        editor = local.edit();
        editor.putBoolean("driving_status",false);
        editor.commit();
        status = (TextView) findViewById(R.id.status);
        if(local.getBoolean("driving_status",false)==false) { //없거나 false라면
        status.setText("빈차");
            btn_driving.setBackgroundColor(Color.parseColor("#2d2d2d"));
            btn_empty.setBackgroundColor(Color.parseColor("#f7be16"));
            btn_driving.setTextColor(Color.WHITE);
            btn_empty.setTextColor(Color.parseColor("#2d2d2d"));
        }else{
            status.setText("운전중");
            btn_empty.setBackgroundColor(Color.parseColor("#2d2d2d"));
            btn_driving.setBackgroundColor(Color.parseColor("#f7be16"));
            btn_empty.setTextColor(Color.WHITE);
            btn_driving.setTextColor(Color.parseColor("#2d2d2d"));
        }






        if(isDriving){
            Intent intent = getIntent();
            if(intent!=null){
                String arrive = intent.getStringExtra("des");

                final int lang= intent.getIntExtra("lang",0);

                status.setText(arrive);
                Toast.makeText(this, ""+lang, Toast.LENGTH_SHORT).show();
                tts = new TextToSpeech(Main_Activity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        switch (lang){
                            case 0:
                                tts.speak("어서오세요",TextToSpeech.QUEUE_FLUSH,null);
                                break;

                            case 1:
                                tts.speak("welcome",TextToSpeech.QUEUE_FLUSH,null);
                                break;

                            case 2:
                                tts.speak("欢迎光临",TextToSpeech.QUEUE_FLUSH,null);
                                break;

                            case 3:
                                tts.speak("いらっしゃいませ",TextToSpeech.QUEUE_FLUSH,null);
                                break;

                            case 4:
                                tts.speak("Bienveue",TextToSpeech.QUEUE_FLUSH,null);
                                break;
                        }

                    }
                });

                btn_empty.setBackgroundColor(Color.parseColor("#2d2d2d"));
                btn_driving.setBackgroundColor(Color.parseColor("#f7be16"));
                btn_empty.setTextColor(Color.WHITE);
                btn_driving.setTextColor(Color.parseColor("#2d2d2d"));

            }

        }


        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_empty.setBackgroundColor(Color.parseColor("#2d2d2d"));
                btn_driving.setBackgroundColor(Color.parseColor("#f7be16"));
                btn_empty.setTextColor(Color.WHITE);
                btn_driving.setTextColor(Color.parseColor("#2d2d2d"));
                editor.putBoolean("driving_status",true);
                editor.commit();
                status.setText("운행중");
            }
        });

        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isDriving=false;

                if(local.getBoolean("driving_status",false)==false){ //없거나 false라면
                    //운행중이아니라는 토스트를 띄워준다.
                    Toast.makeText(Main_Activity.this, "현재 빈차 상태입니다.", Toast.LENGTH_SHORT).show();
                }else{ //있으면 false로 바꿔준다.
                    //운행중 버튼을 다시 원상복귀한다.
                    editor.putBoolean("driving_status",false);
                    editor.commit();
                }


                btn_driving.setBackgroundColor(Color.parseColor("#2d2d2d"));
                btn_empty.setBackgroundColor(Color.parseColor("#f7be16"));
                btn_driving.setTextColor(Color.WHITE);
                btn_empty.setTextColor(Color.parseColor("#2d2d2d"));

                status.setText("빈차");


//                //테스트를 위한코드
////                Log.i("클릭","클릭");
//                Call_driver_dialog a = new Call_driver_dialog(Main_Activity.this,"x","x","x","x",null,Main_Activity.this,"name",null);
//                a.show();
            }
        });

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

    public Button getBtn_empty() {
        return btn_empty;
    }
    //
    //승객의 요청이 fcm을 통해 온다면 MyFirebaseMessagingService에 등록한 브로드 캐스트를 사용하여 밑의 코드를 실행한다.
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


//승객의 요청이 fcm을 통해 온다면 브로스캐스트로 밑의 코드를 실행한다.
            if(local.getBoolean("driving_status",false)==false){ //없거나 false라면
              //운행중이 아닌 상태에서만 다이얼로그를 띄워준다.
                //FCM으로 사용자 이름, 전화번호 , 출발지,도착지,사용자 지정언어,토큰값 저장

               String get_json= intent.getStringExtra("msg");
                Gson gson = new Gson();
                //item_matching 부분임으로 객체로 변경.
                item_matching item_response = gson.fromJson(get_json, item_matching.class);

              Call_driver_dialog dialog = new Call_driver_dialog(Main_Activity.this,item_response.getStart_address(),item_response.getDestination(),item_response.getX(),
                      item_response.getY(), item_response.getToken(),Main_Activity.this,item_response.getName(),item_response.getLang());
                    Log.i("언어값 : ",item_response.getLang());
                dialog.show();

            }


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        if(tts!=null) {
            tts.stop();
            tts.shutdown();
        }
    }

    //승객 요청 수락했을 때 처리
    @Override
    public void callOkResult() {

    }
}
