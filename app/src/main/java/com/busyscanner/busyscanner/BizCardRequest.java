package com.busyscanner.busyscanner;

public class BizCardRequest {
    String desc;
    String encodedImage;

    public BizCardRequest(String desc, String encodedImage) {
        this.desc = desc;
        this.encodedImage = encodedImage;
    }
}
