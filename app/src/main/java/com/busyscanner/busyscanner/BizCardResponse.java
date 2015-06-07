package com.busyscanner.busyscanner;

import retrofit.Callback;
import retrofit.mime.TypedFile;

/**
 * Base response received from {@link ImageProcessingApi#uploadCardImage(TypedFile, String, Callback)}
 */
public class BizCardResponse {

    private String phone;
    private String company;
    private String email;
    private String fullname;
    private String _id;

    public String get_id() {
        return _id;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }
}
