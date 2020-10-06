package com.togettech.kmerdelices.Model;

import java.util.List;

public class RestaurantModel {

    private String key;
    private String restaurant_id, name, image, description, ville, wb_link;
    private String menu;
    List<RestaurantModel> menus;
    private Double ratingValue;
    private Long ratingCount;

    public RestaurantModel () {

    }

    public RestaurantModel(String wb_link, String key, String restaurant_id, String name, String image, String description, String ville, String menu, Double ratingValue, Long ratingCount) {
        this.key = key;
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.menu = menu;
        this.ville = ville;
        this.ratingValue = ratingValue;
        this.ratingCount = ratingCount;

        this.wb_link = wb_link;
    }

    public String getWb_link() {
        return wb_link;
    }

    public void setWb_link(String wb_link) {
        this.wb_link = wb_link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<RestaurantModel> getMenus() {
        return menus;
    }

    public void setMenus(List<RestaurantModel> menus) {
        this.menus = menus;
    }
}
