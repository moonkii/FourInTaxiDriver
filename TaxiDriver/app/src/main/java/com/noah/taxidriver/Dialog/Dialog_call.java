package com.noah.taxidriver.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.noah.taxidriver.R;

/**
 * Created by Noah on 2017-09-18.
 */

public class Dialog_call extends Dialog {

/* FCM으로 받은 사용자 정보 로컬 DB에 넣어야함
*  sqlite에 저장 하기바람
*
* */

    TextView start;
    TextView end;
    TextView lang;
    TextView ok;
    TextView cancel;

    //손님 정보
    String user_start;
    String user_end;
    String user_lang;
    String user_token;
    String user_name;
    String user_number;

    CallOkClickListener callOkClickListener;

    public Dialog_call(@NonNull Context context,String name,String number, String user_start, String user_end, String user_lang, String user_token,CallOkClickListener mCallOkClickListener) {
        super(context);
        this.user_start = user_start;
        this.user_end = user_end;
        this.user_lang = user_lang;
        this.user_token = user_token;
        this.user_name=name;
        this.user_number=number;
        this.callOkClickListener = mCallOkClickListener;

        //여기다가 사용자 정보들 로컬DB에 저장하기
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_dialog);

        start = (TextView) findViewById(R.id.call_start);
        end = (TextView) findViewById(R.id.call_end);
        lang= (TextView) findViewById(R.id.call_lang);
        ok = (TextView) findViewById(R.id.call_ok);
        cancel = (TextView) findViewById(R.id.call_cancel);

        start.setText(user_start);
        end.setText(user_end);
        lang.setText(user_lang);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_call.this.dismiss();
                callOkClickListener.callOkResult();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_call.this.dismiss();
            }
        });


    }

    //수락 시 결과 처리 메소드
    public interface CallOkClickListener{
        void callOkResult();
    }
}
