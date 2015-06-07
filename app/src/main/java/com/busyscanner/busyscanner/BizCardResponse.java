package com.busyscanner.busyscanner;

import retrofit.Callback;
import retrofit.mime.TypedFile;

/**
 * Base response received from {@link ImageProcessingApi#uploadCardImage(TypedFile, String, Callback)}
 */
public class BizCardResponse {

    private String name;
    private String role;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String twitter;

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getWebsite() {
        return website;
    }
}
