package com.example.doan_sale.model;
import java.io.Serializable;

public class Product implements Serializable {
    int proID;
    String proName;
    int proPrice;
    String proImage;
    String proDes;
    int categoryID;
    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public  int getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Product(int proID, String proImage,String proName,  int proPrice,String proDes, int categoryID) {
        this.proID = proID;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proImage = proImage;
        this.proDes = proDes;
        this.categoryID = categoryID;
    }
    public Product( String proImage,String proName,  int proPrice,String proDes, int categoryID) {
        this.proName = proName;
        this.proPrice = proPrice;
        this.proImage = proImage;
        this.proDes = proDes;
        this.categoryID = categoryID;
    }
    public Product(int proID, String proImage,String proName, int proPrice,String proDes) {
        this.proID = proID;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proImage = proImage;
        this.proDes = proDes;

    }
    public Product(int proID, String proImage, String proName, int proPrice) {
        this.proID = proID;
        this.proImage = proImage;
        this.proName = proName;
        this.proPrice = proPrice;
    }
    public Product() {}

}
