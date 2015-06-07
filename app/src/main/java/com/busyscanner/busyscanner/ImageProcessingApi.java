package com.busyscanner.busyscanner;

import java.util.List;

import retrofit.Callback;
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

    @Multipart
    @POST("/receiveImg")
    void uploadImageString(@Part("base64img") String encodedImage,
                           @Part("desc") String description,
                           Callback<List<BizCardResponse>> callback);
}
