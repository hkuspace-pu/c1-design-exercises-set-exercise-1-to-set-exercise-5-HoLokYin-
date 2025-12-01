package com.karzaf.sushihub;

public class DrinkModel {
    private String uuid;
    private String name;
    private String description;
    private String price;
    private String imageName;
    public DrinkModel() {
    }
    public DrinkModel(String uuid, String name, String description, String price, String imageName) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
