package com.noah.taxidriver.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.noah.taxidriver.R;

/**
 * Created by YH on 2017-09-30.
 */

public class Matched_driver extends AppCompatActivity {
    Button go,cancle;
    EditText where,destination;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matched_driver);
        go = (Button)findViewById(R.id.go);
        cancle = (Button)findViewById(R.id.cancle);
        where = (EditText)findViewById(R.id.where);
        destination = (EditText)findViewById(R.id.destination);

    }
}
