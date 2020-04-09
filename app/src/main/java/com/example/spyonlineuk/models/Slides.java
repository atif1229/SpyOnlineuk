package com.example.spyonlineuk.models;

import com.android.volley.toolbox.StringRequest;

public class Slides {
    private int slideId;
    private String slideImage;
    private String slideCaption;

    public Slides() {
    }

    public Slides(int slideId, String slideImage, String slideCaption) {
        this.slideId = slideId;
        this.slideImage = slideImage;
        this.slideCaption = slideCaption;
    }

    public int getSlideId() {
        return slideId;
    }

    public void setSlideId(int slideId) {
        this.slideId = slideId;
    }

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

    public String getSlideCaption() {
        return slideCaption;
    }

    public void setSlideCaption(String slideCaption) {
        this.slideCaption = slideCaption;
    }
}
