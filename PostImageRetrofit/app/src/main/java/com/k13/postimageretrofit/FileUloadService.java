package com.k13.postimageretrofit;

/**
 * Created by k13 on 11/4/16.
 */
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUloadService
{
    @Multipart
    @POST("/vz_upload_img.php")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);
}
