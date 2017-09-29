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

import java.util.ArrayList;

/**
 * Created by Noah on 2017-09-18.
 */

public class Record_Activity extends Activity {

    /* 승객 탑승기록 액티비티 */

    ListView recordListView;
//    RecordAdapter adapter;
    ArrayList<Record_item> dataArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dataArray = new ArrayList<>();


        //테스트 데이터
        dataArray.add(new Record_item("2017. 09. 17","출발 13:09 경기도 감자밭","도착 15:10 경기도 호텔","테스트 기사님","01012345678"));


        recordListView = (ListView) findViewById(R.id.record_list);
//        adapter = new RecordAdapter(this,R.layout.activity_record_item,dataArray);
//        recordListView.setAdapter(adapter);

    }
}


class Record_item{

    String date;
    String start;
    String end;
    String name;
    String callnum;

    public Record_item(String date, String start, String end, String pName, String callnum) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.name = pName;
        this.callnum = callnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallnum() {
        return callnum;
    }

    public void setCallnum(String callnum) {
        this.callnum = callnum;
    }
}



//
//class RecordAdapter extends ArrayAdapter<Record_item> {
//
//
//    ArrayList<Record_item> arraylist;
//    Context c;
//
//
//    public RecordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Record_item> objects) {
//        super(context, resource, objects);
//        this.c = context;
//        this.arraylist = objects;
//    }
//
//    @Override
//    public int getCount() {
//        return arraylist.size();
//    }
//
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        if (convertView == null) {
//            //인플레이터 생성
//            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            //인플레이터로 리스트뷰를 실제 객체로 만들어줌.
//            convertView = inflater.inflate(R.layout.activity_record_item, parent, false);
//        }
//
//        TextView date = (TextView) convertView.findViewById(R.id.record_item_date);
//        TextView start = (TextView) convertView.findViewById(R.id.record_item_start);
//        TextView end = (TextView) convertView.findViewById(R.id.record_item_end);
//        TextView pName = (TextView) convertView.findViewById(R.id.record_pname);
//        ImageView call = (ImageView) convertView.findViewById(R.id.record_call);
//
//
//        date.setText(arraylist.get(position).getDate());
//        start.setText(arraylist.get(position).getStart());
//        end.setText(arraylist.get(position).getEnd());
//        pName.setText(arraylist.get(position).getName());
//
//
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(c, position + "번째전화", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        return convertView;
//    }
//
//}


