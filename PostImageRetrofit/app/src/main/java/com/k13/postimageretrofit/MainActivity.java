package com.k13.postimageretrofit;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private SweetAlertDialog dialog;
    public static final String TAG="MainActivity";
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog=new SweetAlertDialog(MainActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle("Posting ImageFile");
        dialog.setTitleText("Please Wait Some Time...");
        dialog.setCancelable(true);
        dialog.show();
        Cursor mCursor = getContentResolver().query
        (
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER
        );

        list=new ArrayList<String>();
        mCursor.moveToFirst();
        int i=0;
        while(!mCursor.isAfterLast())
        {
            list.add(i++,mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            Log.e(":-",list.get(--i));

            uploadFile();
            Log.e(TAG, " - Data : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            Log.e(TAG, " - _ID : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID)));
            Log.e(TAG, " - File Name : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            Log.e(TAG, " - File Path : " + mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            mCursor.moveToNext();
        }
        mCursor.close();
        /*dialog.dismiss();*/
    }


    /**/
    private void uploadFile()
    {
        Log.e("UploadFile","Calling....");
        // create upload service client
        FileUloadService service =ServiceGenerator.createService(FileUloadService.class);

        // use the FileUtils to get the actual file by uri
        // File file = FileUtils.getFile(this, fileUri);
        File file=new File(list.get(0));
        Log.e("File",file.getName());
        // create RequestBody instance from file

//        ResponseBody imagefiles=ResponseBody.create(MediaType.parse("image/jpg"), String.valueOf(file)+".jpg");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        // finally, execute the request
        Call<ResponseBody> call = service.upload(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

    }
}

