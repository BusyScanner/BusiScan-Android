package com.busyscanner.busyscanner;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

public class Access {

    public static final String TAG = Access.class.getSimpleName();
    private static volatile Access instance = null;
    private static final String IMAGE_PROCESSING_ENDPOINT = "http://busiscan.herokuapp.com";
    private static final RestAdapter.LogLevel LOGLEVEL = RestAdapter.LogLevel.BASIC;
    private static final int TIMEOUT = 15; // Seconds
    private OkClient okClient;

    private ImageProcessingApi imageProcessingApi;

    private Access() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setFollowRedirects(false);
        okClient = new OkClient(okHttpClient);
    }

    public static Access getInstance() {
        if (instance == null) {
            synchronized (Access.class) {
                if (instance == null) {
                    instance = new Access();
                }
            }
        }
        return instance;
    }

    public ImageProcessingApi getImageProcessingApi() {
        if (imageProcessingApi == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(IMAGE_PROCESSING_ENDPOINT)
                    .setLogLevel(LOGLEVEL)
                    .setLog(new AndroidLog(TAG))
                    .setClient(okClient)
                    .build();
            imageProcessingApi = restAdapter.create(ImageProcessingApi.class);
        }
        return imageProcessingApi;
    }
}
