package com.noah.taxidriver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by YH on 2017-08-25.
 */

public class login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//추가한 라인
        Log.i("login","시작");
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //결국 한 기기에선 똑같은 토큰이 나오게 되있음.
               String token =  FirebaseInstanceId.getInstance().getToken();
        String token2 = FirebaseInstanceId.getInstance().getToken();
        Log.i("to",token);
        Log.i("dd",token2);


        //로그인 완료 될 경우에 토큰 값을 받아 올 수 있도록 한다.
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();
    }

}
