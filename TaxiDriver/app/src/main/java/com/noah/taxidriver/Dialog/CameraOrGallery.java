package com.noah.taxidriver.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.noah.taxidriver.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Noah on 2017-11-19.
 */

public class CameraOrGallery extends DialogFragment {

    View view;
    TextView camera;
    TextView gallery;
    setResultListener resultListener;
    String picturePath;
//
//    public static CameraOrGallery newInstance() {
//        CameraOrGallery frag = new CameraOrGallery();
//        Bundle args= new Bundle();
//        frag.setArguments(args);
//        return frag;
//
//
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //============ 다이얼로그 생성 시작 ===============
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //다이얼로그 빌더 생성
        LayoutInflater layoutInflater = getActivity().getLayoutInflater(); //인플레이터 생성
        view = (layoutInflater.inflate(R.layout.camera_or_gallery, null)); //인플레이터로 레이아웃 뷰 연결
        builder.setView(view); //다이얼로그 빌더에 인플레이터 연결
        Dialog dialog = builder.create(); //다이얼로그 생성
        //============ 다이얼로그 생성 끝 ===============

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        camera = (TextView) view.findViewById(R.id.camera);
        gallery = (TextView) view.findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //갤러리 호출
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //인텐트로 갤러리앱 호출
                startActivityForResult(intent, 0); //인텐트 호출 시작 ( request 값 0)

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {//프로필 사진찍기 호출 메소드 시작
            @Override
            public void onClick(View v) {//프로필 사진찍기 클릭 시작


                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 1);


            }//프로필 사진찍기 클릭 끝
        });//프로필 사진찍기 호출 메소드 끝

        return dialog;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //앨범에서 결과값 받기
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) { //인텐트 결과로 이미지값 가져오기 성공했는지 여부


            /*============ 앨범에서 이미지 가져오기 ===============*/
            Uri selectedimage = data.getData(); // Uri 변수에 불러온 데이터 저장(인텐트로 가져온 데이터 저장)
            String[] filepathcolumn = {MediaStore.Images.Media.DATA}; //String 배열에 이미지 정보 담기

            Cursor cursor = getActivity().getContentResolver().query(selectedimage, filepathcolumn, null, null, null); //커서로 가져올 내용 지정 (uri 주소와 ,불러올 파일 주소 열
            cursor.moveToFirst(); //데이터베이스에 커서 이동

            int columnindex = cursor.getColumnIndex(filepathcolumn[0]); //커서로 가져올 열 번호 int변수에 저장
            picturePath = cursor.getString(columnindex); //커서로 가져올 열 번호 내용을 String 변수에 저장
            cursor.close(); //커서 끝


            dismiss();

        }


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {//카메라 결과값 받기 if 시작

             /*============ 카메라에서 이미지 가져오기 ===============*/
            Uri selectedimage = data.getData(); // Uri 변수에 불러온 데이터 저장(인텐트로 가져온 데이터 저장)
            String[] filepathcolumn = {MediaStore.Images.Media.DATA}; //String 배열에 이미지 정보 담기

            Cursor cursor = getActivity().getContentResolver().query(selectedimage, filepathcolumn, null, null, null); //커서로 가져올 내용 지정 (uri 주소와 ,불러올 파일 주소 열
            cursor.moveToFirst(); //데이터베이스에 커서 이동

            int columnindex = cursor.getColumnIndex(filepathcolumn[0]); //커서로 가져올 열 번호 int변수에 저장
            picturePath = cursor.getString(columnindex); //커서로 가져올 열 번호 내용을 String 변수에 저장
            cursor.close(); //커서 끝

            dismiss();
        }

    }


    public interface setResultListener{
        void setResult(String path);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        resultListener.setResult(picturePath);
    }

    public void setResultListener(setResultListener resultListener) {
        this.resultListener = resultListener;
    }
}