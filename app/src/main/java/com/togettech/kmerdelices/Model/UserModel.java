package com.togettech.kmerdelices.Model;

public class UserModel {

    private String uid, image, name, address, phone, password;

    public UserModel() {
    }

    public UserModel(String uid, String image, String name, String address, String phone, String password) {
        this.uid = uid;
        this.image = image;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
