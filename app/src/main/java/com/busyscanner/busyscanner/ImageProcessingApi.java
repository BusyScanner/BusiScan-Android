package com.busyscanner.busyscanner;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface ImageProcessingApi {

    @Multipart
    @POST("/receiveImg")
    void uploadCardImage(@Part("image") TypedFile imageFile,
                         @Part("desc") String description,
                         Callback<List<BizCardResponse>> callback);

    @POST("/receiveImg")
    void uploadImageString(@Body BizCardRequest body,
                           Callback<List<BizCardResponse>> callback);
}
