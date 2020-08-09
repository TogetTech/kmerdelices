package com.togettech.kmerdelices.Model;

import java.util.List;

public class RestaurantModel {

    private String restaurant_id, name, image, description, ville;
    private String menu_resto_id, menu_resto_name, menu_resto_prix, menu_resto_image;

    public RestaurantModel () {

    }

    public RestaurantModel(String restaurant_id, String name, String image, String description, String ville, String menu_resto_id, String menu_resto_name, String menu_resto_prix, String menu_resto_image) {
        this.restaurant_id = restaurant_id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.ville = ville;
        this.menu_resto_id = menu_resto_id;
        this.menu_resto_name = menu_resto_name;
        this.menu_resto_prix = menu_resto_prix;
        this.menu_resto_image = menu_resto_image;
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

    public String getMenu_resto_id() {
        return menu_resto_id;
    }

    public void setMenu_resto_id(String menu_resto_id) {
        this.menu_resto_id = menu_resto_id;
    }

    public String getMenu_resto_name() {
        return menu_resto_name;
    }

    public void setMenu_resto_name(String menu_resto_name) {
        this.menu_resto_name = menu_resto_name;
    }

    public String getMenu_resto_prix() {
        return menu_resto_prix;
    }

    public void setMenu_resto_prix(String menu_resto_prix) {
        this.menu_resto_prix = menu_resto_prix;
    }

    public String getMenu_resto_image() {
        return menu_resto_image;
    }

    public void setMenu_resto_image(String menu_resto_image) {
        this.menu_resto_image = menu_resto_image;
    }
}
