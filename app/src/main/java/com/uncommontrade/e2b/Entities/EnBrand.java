package com.uncommontrade.e2b.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thoin_000 on 4/21/2016.
 */
public class EnBrand implements Parcelable{
    private String brand_id;
    private String brand_name;
    private String cover;

    public EnBrand() {
    }

    public EnBrand(String brand_id, String brand_name, String cover) {
        this.brand_id = brand_id;
        this.brand_name = brand_name;
        this.cover = cover;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(brand_id);
        parcel.writeString(brand_name);
        parcel.writeString(cover);

    }

    public static final Creator<EnBrand> CREATOR = new Creator<EnBrand>() {
        @Override
        public EnBrand createFromParcel(Parcel source) {
            EnBrand enBrand = new EnBrand();
            enBrand.brand_id = source.readString();
            enBrand.brand_name = source.readString();
            enBrand.cover = source.readString();
            return enBrand;
        }

        @Override
        public EnBrand[] newArray(int size) {
            return new EnBrand[size];
        }
    };

}
