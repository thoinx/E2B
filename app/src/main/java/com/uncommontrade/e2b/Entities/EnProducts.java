package com.uncommontrade.e2b.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thoin_000 on 4/21/2016.
 */
public class EnProducts implements Parcelable {
    private float product_id;
    private String title;
    private String description;
    private String image;
    private float selling_price;
    private float original_price;
    private float qty;
    private String parent_id;
    private String attr_id;
    private boolean has_favorited;
    private EnBrand brand;

    public EnProducts() {
    }


    public float getProduct_id() {
        return product_id;
    }

    public void setProduct_id(float product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public float getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(float original_price) {
        this.original_price = original_price;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public boolean isHas_favorited() {
        return has_favorited;
    }

    public void setHas_favorited(boolean has_favorited) {
        this.has_favorited = has_favorited;
    }


    public EnBrand getBrand() {
        return brand;
    }

    public void setBrand(EnBrand brand) {
        this.brand = brand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeFloat(product_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeFloat(selling_price);
        parcel.writeFloat(original_price);
        parcel.writeFloat(qty);
        parcel.writeString(parent_id);
        parcel.writeString(attr_id);
        parcel.writeByte((byte) (has_favorited ? 1 : 0));
        parcel.writeParcelable(brand, flags);
    }

    public static final Creator<EnProducts> CREATOR = new Creator<EnProducts>() {
        @Override
        public EnProducts createFromParcel(Parcel source) {
            EnProducts enProducts = new EnProducts();
            enProducts.product_id = source.readFloat();
            enProducts.title = source.readString();
            enProducts.description = source.readString();
            enProducts.image = source.readString();
            enProducts.selling_price = source.readFloat();
            enProducts.original_price = source.readFloat();
            enProducts.qty = source.readFloat();
            enProducts.parent_id = source.readString();
            enProducts.attr_id = source.readString();
            enProducts.has_favorited = source.readByte() != 0;
            enProducts.brand = source.readParcelable(EnBrand.class.getClassLoader());
            return enProducts;
        }

        @Override
        public EnProducts[] newArray(int size) {
            return new EnProducts[size];
        }
    };
}
