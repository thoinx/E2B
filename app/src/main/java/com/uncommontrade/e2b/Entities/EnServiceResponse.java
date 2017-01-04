package com.uncommontrade.e2b.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thoin_000 on 4/20/2016.
 */
public class EnServiceResponse {
    private int code;
    @Expose
    @SerializedName("error_message")
    private String errorMessage;
    private List<EnCollectionsList> collections;
    private List<EnProducts> products;
    private List<EnProducts> data;
    private String access_token;
    private EnProfile profile;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public EnProfile getProfile() {
        return profile;
    }

    public void setProfile(EnProfile profile) {
        this.profile = profile;
    }

    public List<EnProducts> getProducts() {
        return products;
    }

    public void setProducts(List<EnProducts> products) {
        this.products = products;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<EnCollectionsList> getCollections() {
        return collections;
    }

    public void setCollections(List<EnCollectionsList> collections) {
        this.collections = collections;
    }

    public String getError_message() {
        return errorMessage;
    }

    public void setError_message(String error_message) {
        this.errorMessage = error_message;
    }

    public List<EnProducts> getData() {
        return data;
    }

    public void setData(List<EnProducts> data) {
        this.data = data;
    }
}
