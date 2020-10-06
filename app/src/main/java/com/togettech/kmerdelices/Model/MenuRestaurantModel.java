package com.togettech.kmerdelices.Model;

import java.util.List;

public class MenuRestaurantModel {

    private String key;
    private String restaurant_id, name, image, description, ville;
    private String menu_restaurant_id, menu_restaurant_image, menu_restaurant_name, menu_restaurant_prix;
    List<MenuRestaurantModel> menus;
    private Double ratingValue;
    private Long ratingCount;

    public MenuRestaurantModel () {

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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMenu_restaurant_id() {
        return menu_restaurant_id;
    }

    public void setMenu_restaurant_id(String menu_restaurant_id) {
        this.menu_restaurant_id = menu_restaurant_id;
    }

    public String getMenu_restaurant_image() {
        return menu_restaurant_image;
    }

    public void setMenu_restaurant_image(String menu_restaurant_image) {
        this.menu_restaurant_image = menu_restaurant_image;
    }

    public String getMenu_restaurant_name() {
        return menu_restaurant_name;
    }

    public void setMenu_restaurant_name(String menu_restaurant_name) {
        this.menu_restaurant_name = menu_restaurant_name;
    }

    public String getMenu_restaurant_prix() {
        return menu_restaurant_prix;
    }

    public void setMenu_restaurant_prix(String menu_restaurant_prix) {
        this.menu_restaurant_prix = menu_restaurant_prix;
    }

    public List<MenuRestaurantModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuRestaurantModel> menus) {
        this.menus = menus;
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
}
