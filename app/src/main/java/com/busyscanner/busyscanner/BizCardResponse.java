package com.busyscanner.busyscanner;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import retrofit.Callback;
import retrofit.mime.TypedFile;

/**
 * Base response received from {@link ImageProcessingApi#uploadCardImage(TypedFile, String, Callback)}
 */
public class BizCardResponse extends SugarRecord<BizCardResponse> {

    private String phone;
    private String company;
    private String email;
    private String fullname;
    @Ignore
    private String _id;

    public BizCardResponse() {
    }

    public BizCardResponse(String company, String email, String fullname, String phone) {
        this.company = company;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
    }

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
