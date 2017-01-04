package com.uncommontrade.e2b.Entities;

/**
 * Created by thoin_000 on 5/3/2016.
 */
public class EnReview {
    private float product_id;
    private float rate;
    private String name;
    private String review_title;
    private String review_content;

    public EnReview() {
    }

    public EnReview(float product_id, float rate, String name, String review_title, String review_content) {
        this.product_id = product_id;
        this.rate = rate;
        this.name = name;
        this.review_title = review_title;
        this.review_content = review_content;
    }

    public float getProduct_id() {
        return product_id;
    }

    public void setProduct_id(float product_id) {
        this.product_id = product_id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(String review_title) {
        this.review_title = review_title;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }
}
