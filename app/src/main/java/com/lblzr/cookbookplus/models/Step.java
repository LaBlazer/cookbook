package com.lblzr.cookbookplus.models;

import java.io.Serializable;

public class Step implements Serializable {
    private String name;
    private String image;
    private String description;

    public Step(String name, String description, String image) {
        this.description = description;
        this.image = image;
        this.name = name;
    }

    public Step(String name, String description) {
        this.description = description;
        this.image = null;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean hasImage() {
        return image != null;
    }
}