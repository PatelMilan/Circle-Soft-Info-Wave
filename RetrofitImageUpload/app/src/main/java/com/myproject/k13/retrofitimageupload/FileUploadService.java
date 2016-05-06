package com.myproject.k13.retrofitimageupload;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService
{
    @Multipart
    @POST("/vz_upload_img.php")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);
}