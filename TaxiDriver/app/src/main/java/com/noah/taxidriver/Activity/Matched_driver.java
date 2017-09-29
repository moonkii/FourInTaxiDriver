package com.noah.taxidriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noah.taxidriver.Network;
import com.noah.taxidriver.R;
import com.noah.taxidriver.data.MyCourseData;
import com.noah.taxidriver.item_matching;

import java.util.Date;

import io.realm.Realm;

import static java.security.AccessController.getContext;

/**
 * Created by YH on 2017-09-30.
 */

public class Matched_driver extends AppCompatActivity {
    Button go;
    Handler handler;
    EditText where,destination;
    String token;
    String x;
    String y;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matched_driver);
        handler = new send_message_handler();
        go = (Button)findViewById(R.id.go);

        where = (EditText)findViewById(R.id.where);
        destination = (EditText)findViewById(R.id.destination);
        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String end = intent.getStringExtra("end");
        token = intent.getStringExtra("token");
        x = intent.getStringExtra("x");
        y = intent.getStringExtra("y");
        where.setText(start);
        destination.setText(end);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_matching item_matching = new item_matching("get_client",token);

                Gson gson = new Gson();
                String send = gson.toJson(item_matching);
                Log.i("뱐ㅇ러ㅏ",send);
                Network.push(send,Matched_driver.this,handler);
            }
        });


    }
    class send_message_handler extends Handler {//매칭의 모든 것을 Network하나로 하기 때문에,
        //응답후 결과는 모두 핸들 메시지를 사용해야함.
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0 :
                    Toast.makeText(getApplicationContext(), "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();

                case 1 :
                    startActivity(new Intent(Matched_driver.this,Main_Activity.class));



            }
        }
    }
}
