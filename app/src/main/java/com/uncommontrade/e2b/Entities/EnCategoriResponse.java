package com.uncommontrade.e2b.Entities;

import java.util.List;

/**
 * Created by HoangDinh on 5/12/2016.
 */
public class EnCategoriResponse {
    private List<EnCategories> categories;
    private int code;

    public List<EnCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<EnCategories> categories) {
        this.categories = categories;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
