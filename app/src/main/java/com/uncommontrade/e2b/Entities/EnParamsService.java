package com.uncommontrade.e2b.Entities;

/**
 * Created by thoin_000 on 4/22/2016.
 */
public class EnParamsService {
    private int collection_id;
    private int page;
    private float product_id;
    private String account;
    private String password;
    private String device_id;
    private int cat_id;
    private String key;
    private String email;
    private String username;
    private String fbid;
    private String qty;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public EnParamsService() {
        super();
    }

    public EnParamsService(int collection_id, int page) {
        this.collection_id = collection_id;
        this.page = page;
    }


    public float getProduct_id() {
        return product_id;
    }

    public void setProduct_id(float product_id) {
        this.product_id = product_id;
    }

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getKey() {
        return key;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }
}

