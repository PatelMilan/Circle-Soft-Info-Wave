package com.myproject.k13.retrofitimageupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button selectImage, uploadImage;
    TextView imageName;
    ImageView imageView;
    Bitmap bitmap;
    ProgressDialog pd;
    String path;
    Uri filePath;

    public static final MediaType MEDIA_TYPE = MediaType.parse("image/jpg");
    private Bitmap images;
    private String imgNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(MainActivity.this);

        selectImage = (Button) findViewById(R.id.bSel);
        uploadImage = (Button) findViewById(R.id.bUp);

        imageName = (TextView) findViewById(R.id.Tname);

        imageView = (ImageView) findViewById(R.id.Images);


        ///** SetOnClick Listener On Button***///
        selectImage.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSel:
                onSelectImageIntoGallery();
                break;
            case R.id.bUp:
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                long codes=Math.round(Math.random() * 1000);
                uploadFile(filePath.getPath());
               break;
        }
    }
    private void onSelectImageIntoGallery()
    {

        //Intent use for Open Gallery and getback result of selected image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    // it will be return the result of Intent Call Back
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            ///Local Uir Class Variable to Store dataResult

            filePath = data.getData();
            String name[]={MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(filePath, name, null, null, null);
            c.moveToFirst();
            int col = c.getColumnIndex(name[0]);
            path = c.getString(col);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            imageName.setText(path);
            Log.e("Image Path is :-",filePath+"");
            Log.e("Path is :-",path);
        }
    }
    private void uploadFile(String filePath)
    {
        Log.e("UploadFile","Calling....");
        // create upload service client
        FileUploadService service =ServiceGenerator.createService(FileUploadService.class);

        // use the FileUtils to get the actual file by uri
        // File file = FileUtils.getFile(this, fileUri);
        File file=new File(path);
        Log.e("File",file.getName());
         // create RequestBody instance from file

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

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}
