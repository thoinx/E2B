package com.uncommontrade.e2b.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HoangDinh on 5/12/2016.
 */
public class EnCategories implements Parcelable {
    private int cat_id;
    private String name;
    private int in_dropdown;

    public EnCategories() {
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getIn_dropdown() {
        return in_dropdown;
    }

    public void setIn_dropdown(int in_dropdown) {
        this.in_dropdown = in_dropdown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cat_id);
        dest.writeString(name);
        dest.writeInt(in_dropdown);
    }

    public static final Creator<EnCategories> CREATOR= new Creator<EnCategories>() {

        @Override
        public EnCategories createFromParcel(Parcel source) {
            EnCategories enCategories= new EnCategories();

            enCategories.cat_id= source.readInt();
            enCategories.name= source.readString();
            enCategories.in_dropdown= source.readInt();

            return enCategories;
        }

        @Override
        public EnCategories[] newArray(int size) {
            return new EnCategories[size];
        }
    };
}

