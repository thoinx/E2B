package com.uncommontrade.e2b.Entities;

import java.util.List;

/**
 * Created by thoin_000 on 5/5/2016.
 */
public class EnListReviewsResponse {
    private int code;
    private String review_rate;
    private String error_message;
    private List<EnReviewList> data;

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

    public List<EnReviewList> getData() {
        return data;
    }

    public void setData(List<EnReviewList> data) {
        this.data = data;
    }

    public String getReview_rate() {
        return review_rate;
    }

    public void setReview_rate(String review_rate) {
        this.review_rate = review_rate;
    }
}
