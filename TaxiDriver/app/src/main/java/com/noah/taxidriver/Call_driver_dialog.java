package com.noah.taxidriver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.noah.taxidriver.Activity.Main_Activity;
import com.noah.taxidriver.Activity.Matched_driver;
import com.noah.taxidriver.data.MyCourseData;

import java.util.Date;

import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YH on 2017-09-16.
 */

public class Call_driver_dialog extends Dialog {
    public Call_driver_dialog(@NonNull Context context, String start, String end, String x, String y, String get_token, Main_Activity main_activity, String name, String lang) {
        super(context);
        this.context = context;
        this.start_ = start;
        this.end_ = end;
        this.x = x;
        this.y = y;
        //받은 토큰
        this.get_token = get_token;
        this.main_activity = main_activity;
        this.name = name;
        this.lang = lang;
    }

    String lang;
    Main_Activity main_activity;
    Location userLocation;
    //현재 위치 구하기 위한 변수와 객체선언.
    Geocoder geocoder; //주소 지명 찾는 변수
    String myAdress; //주소값
    private LocationManager locationManager;
    private LocationListener locationListener;
    double latitude;
    double longitude;
    Context context;
    String start_;
    String name; //장소명
    String end_;
    String x;
    String y;
    String get_token;
    String my_token;
    TextView start;
    int requestlocationcode = 1;

    SharedPreferences local;
    SharedPreferences.Editor editor;
    TextView end;
    Button ok;//5
    Button no;//6
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.dialog_call_request);
        end = (TextView) findViewById(R.id.textView2);
        start = (TextView) findViewById(R.id.textView);
        ok = (Button) findViewById(R.id.button5);
        no = (Button) findViewById(R.id.button6);
        start.setText(start_);
        end.setText(end_);
        local = getContext().getSharedPreferences("Driver", MODE_PRIVATE);
        editor = local.edit();
        start.setText(start_);
        end.setText(end_);
        my_token = FirebaseInstanceId.getInstance().getToken();



//        userLocation = getMyLocation();
//        Log.i("ss",userLocation.getLatitude()+"");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.매칭 됬다고 보내준다

                Gson gson = new Gson();
                String send = gson.toJson(new Item_response_driver("강원 77바 1234", "name", get_token, x, y, end_, start_, "driver_ok"));
                Log.i("클라에게 보내는 메시지", send);
                Log.i("클라에게 보내는 메시지", "");
                send_message_handler handler = new send_message_handler();//통신 응답 후 처리할 부분을 핸들러에 정의한다.
                Network.push(send, getContext(), handler);
            }
        });

//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 토큰값을 사용하여 매칭됨을 드라이버에게 전송함.
//               // get_token;
//                //고객의 응답을 기다리는 화면으로 넘어간다.
//                Gson gson = new Gson();
//                //이부분에선 택시 xy 값을 보내줘야함.
////                try {
////                    List<Address> where = geocoder.getFromLocation(latitude,longitude,1);
////                    myAdress=where.get(0).getAddressLine(0);
////
////                    Log.v("##주소값 : ",""+where.get(0).getAddressLine(0));
////
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//                String send = gson.toJson(new item_matching("name",my_token,x,y,end_,start_,"driver_ok"));
//                send_message_handler handler = new send_message_handler();
//                //승객이 사용한 똑같은 API에 flag 값만 바꿔서 보내준다. driver_ok
//                Network.push(send,getContext(),handler);
//
//                //바로 매칭된 화면으로 넘어간다.
//
//            }
//        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    class send_message_handler extends Handler {//매칭의 모든 것을 Network하나로 하기 때문에,

        //응답후 결과는 모두 핸들 메시지를 사용해야함.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(getContext(), "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();

                case 1:
                    //2.쉐어드 프리퍼런스에 true로 저장한후에
                    //3.운행중 버튼색깔은 변경해준다.

                    editor.putBoolean("driving_status", true);
                    editor.commit();
                    //            main_activity.getBtn_empty().setBackgroundColor();
//                    Intent intent = new Intent(getContext(), Main_Activity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    getContext().startActivity(intent);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            Number seq = realm.where(MyCourseData.class).max("seq");
                            int nextId = (seq == null) ? 1 : seq.intValue() + 1;
                            MyCourseData myCourseData = realm.createObject(MyCourseData.class, nextId);
                            myCourseData.setDatetime(new Date());
                            myCourseData.setToken(get_token);
                            myCourseData.setName(name);
                            myCourseData.setStart_address(start_);
                            myCourseData.setDestination(end_);
                            Log.i("썅 x", x);
                            Log.i("썅 y", y);
                            myCourseData.setLat(Double.parseDouble(x));
                            myCourseData.setLng(Double.parseDouble(y));
                            myCourseData.setLang(Integer.valueOf(lang));
                        }
                    });
                    Intent intent1 = new Intent(new Intent(context, Matched_driver.class));
                    intent1.putExtra("start", start_);
                    intent1.putExtra("end", end_);
                    intent1.putExtra("token", get_token);
                    intent1.putExtra("x", x);
                    intent1.putExtra("y", y);
                    intent1.putExtra("lang", lang);

                    context.startActivity(intent1);
                    main_activity.finish();
                    dismiss();


            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
