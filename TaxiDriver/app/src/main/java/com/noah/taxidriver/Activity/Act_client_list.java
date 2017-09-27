package com.noah.taxidriver.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noah.taxidriver.R;
import com.noah.taxidriver.data.MyCourseData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by YH on 2017-09-27.
 */

public class Act_client_list extends Activity {
    ListView lv;
    ArrayList<Act_recorditem> data;
    recordAdapter adapter;
    Realm realm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
    lv = (ListView)findViewById(R.id.lv);
        data = new ArrayList<>();
        adapter = new recordAdapter(Act_client_list.this, R.layout.act_record_item,data);
        lv.setAdapter(adapter);

        //임시로 넣는 렘 DB에 데이터 넣는부분

realm.executeTransaction(new Realm.Transaction() {
    @Override
    public void execute(Realm realm) {
        Number seq = realm.where(MyCourseData.class).max("seq");
        int nextId = (seq == null) ? 1 : seq.intValue() + 1;
        MyCourseData data = realm.createObject(MyCourseData.class,nextId);
        data.setLng(0.1);
        data.setLat(0.2);
        data.setDestination("너");
        data.setStart_address("sl");
        data.setToken("ss");
        data.setDatetime(new Date());
    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MyCourseData> realmList = realm.where(MyCourseData.class).findAll();
                for(int i=0;i<realmList.size();i++){
                    data.add(new Act_recorditem(realmList.get(i).getDatetime(),realmList.get(i).getStart_address(),realmList.get(i).getDestination(),realmList.get(i).getName()
                    ,realmList.get(i).getToken()));
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
}

class Act_recorditem{

    Date date;
    String start;
    String destination;
    String name;
    String token;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Act_recorditem(Date date, String start, String destination, String name, String token) {
        this.date = date;
        this.start = start;
        this.destination = destination;
        this.name = name;
        this.token = token;
    }
}

class recordAdapter extends ArrayAdapter<Act_recorditem> {


    ArrayList<Act_recorditem> data;
    Context c;


    public recordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Act_recorditem> objects) {
        super(context, resource, objects);
        this.c=context;
        this.data=objects;
    }

    @Override
    public int getCount() {
        return data.size() ;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            //인플레이터 생성
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //인플레이터로 리스트뷰를 실제 객체로 만들어줌.
            convertView = inflater.inflate(R.layout.act_record_item,parent,false);
        }

        TextView date= (TextView) convertView.findViewById(R.id.record_item_date);
        TextView start= (TextView) convertView.findViewById(R.id.record_item_start);
        TextView end = (TextView) convertView.findViewById(R.id.record_item_end);
        TextView driverName = (TextView) convertView.findViewById(R.id.record_driverName);


        SimpleDateFormat formatter02 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date_string =  formatter02.format(data.get(position).getDate());

        date.setText(date_string);
        start.setText(data.get(position).getStart());
        end.setText(data.get(position).destination);
        driverName.setText(data.get(position).getName());



        return convertView;
    }


}