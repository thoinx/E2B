package com.uncommontrade.e2b.Entities;

import java.util.List;

/**
 * Created by thoin_000 on 5/4/2016.
 */
public class EnProductResponse {
    private int code;
    private String error_message;
    private EnProductDetail product;
    private List<EnImageList> images_list;
    private List<EnProducts> relateds;
    private List<EnReviewList> reviews;
    private EnAttribute attribute;
    private List<EnOptions> options;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public EnProductDetail getProduct() {
        return product;
    }

    public void setProduct(EnProductDetail product) {
        this.product = product;
    }

    public List<EnImageList> getImages_list() {
        return images_list;
    }

    public void setImages_list(List<EnImageList> images_list) {
        this.images_list = images_list;
    }

    public List<EnProducts> getRelateds() {
        return relateds;
    }

    public void setRelateds(List<EnProducts> relateds) {
        this.relateds = relateds;
    }

    public List<EnReviewList> getReviews() {
        return reviews;
    }

    public void setReviews(List<EnReviewList> reviews) {
        this.reviews = reviews;
    }

    public EnAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(EnAttribute attribute) {
        this.attribute = attribute;
    }

    public List<EnOptions> getOptions() {
        return options;
    }

    public void setOptions(List<EnOptions> options) {
        this.options = options;
    }
}
