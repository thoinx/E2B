package com.uncommontrade.e2b.utilities;

import com.uncommontrade.e2b.E2BApplication;

/**
 * Created by thoin_000 on 4/14/2016.
 */
public class HttpNetService {
    public static final HttpNetService instance = new HttpNetService();

    public String getProfile() {
        return HttpUtil.httpPost("user/getProfile", "{\"uid\":181}", getAuthenParam());
    }

    public String getCollectionList() {
        return HttpUtil.httpPost("collection/list", Const.BLANK_STRING, getAuthenParam());
    }

    public String getProductsList(String dataJson) {
        return HttpUtil.httpPost("collection/products", dataJson, getAuthenParam());
    }

    public String getProductsRelateList(String dataJson) {
        return HttpUtil.httpPost("product/relateds", dataJson, getAuthenParam());
    }

    public String postRatingReview(String dataJson) {
        return HttpUtil.httpPost("product/review", dataJson, getAuthenParam());
    }

    public String getProductDetail(String dataJson) {
        return HttpUtil.httpPost("product/detail", dataJson, getAuthenParam());
    }

    public String getListReview(String dataJson) {
        return HttpUtil.httpPost("product/listReviews", dataJson, getAuthenParam());
    }

    private String getAuthenParam() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(E2BApplication.getAppContext());
        return sharedPreferenceManager.getString(Keys.KEY_PREF_UID, Const.BLANK_STRING);
    }

    public String getListCategories() {
        return HttpUtil.httpPost("data/getCategories", "", "");
    }

    public String getCategoriProduct(String param) {
        return HttpUtil.httpPost("product/categoryProducts", param, "");
    }
    public String getCategoriProductAll(String param) {
        return HttpUtil.httpPost("collection/products", param, "");
    }
    public String postLogin(String param) {
        return HttpUtil.httpPost("user/login", param, null);
    }

    public String addToCart(String param) {
        return HttpUtil.httpPost("cart/add", param, getAuthenParam());
    }

    public String postSignUpEmail(String param) {
        return HttpUtil.httpPost("user/register", param, null);
    }
}
