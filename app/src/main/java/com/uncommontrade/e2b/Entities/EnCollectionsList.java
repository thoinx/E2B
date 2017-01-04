package com.uncommontrade.e2b.Entities;

/**
 * Created by thoin_000 on 4/15/2016.
 */
public class EnCollectionsList {
    private String image;
    private String name;
    private String description;
    private String collection_id;

    public EnCollectionsList() {
        super();
    }

    public EnCollectionsList(String image, String name, String description, String collection_id) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.collection_id = collection_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }
}
