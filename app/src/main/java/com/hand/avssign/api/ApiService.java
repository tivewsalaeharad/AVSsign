package com.hand.avssign.api;

import com.hand.avssign.model.AccessToken;
import com.hand.avssign.model.DocumentForSignature;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("token")
    Call<AccessToken> getToken(@Query("code") String code);

    @Headers("Content-Type: application/json")
    @GET("signature")
    Call<DocumentForSignature> getDocument(
            @Header("Authorization") String authorization,
            @Query("department") int department);

    @Multipart
    @Headers({"content-type: multipart/form-data", "cache-control: no-cache"})
    @POST("signature/{id}")
    Call<String> sendSignature(
            @Header("Authorization") String authorization,
            @Path("id") String id,
            @Part("descrtipion") RequestBody description,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2);
}
