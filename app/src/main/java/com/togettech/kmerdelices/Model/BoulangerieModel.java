package com.togettech.kmerdelices.Model;

public class BoulangerieModel {

    private String boulangerie_id, name, image, description, ville;
    private String menu_boul_id, menu_boul_name, menu_boul_prix, menu_boul_image;

    public BoulangerieModel () {

    }

    public BoulangerieModel(String boulangerie_id, String name, String image, String description, String ville, String menu_boul_id, String menu_boul_name, String menu_boul_prix, String menu_boul_image) {
        this.boulangerie_id = boulangerie_id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.ville = ville;
        this.menu_boul_id = menu_boul_id;
        this.menu_boul_name = menu_boul_name;
        this.menu_boul_prix = menu_boul_prix;
        this.menu_boul_image = menu_boul_image;
    }

    public String getBoulangerie_id() {
        return boulangerie_id;
    }

    public void setBoulangerie_id(String boulangerie_id) {
        this.boulangerie_id = boulangerie_id;
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

    public String getMenu_boul_id() {
        return menu_boul_id;
    }

    public void setMenu_boul_id(String menu_boul_id) {
        this.menu_boul_id = menu_boul_id;
    }

    public String getMenu_boul_name() {
        return menu_boul_name;
    }

    public void setMenu_boul_name(String menu_boul_name) {
        this.menu_boul_name = menu_boul_name;
    }

    public String getMenu_boul_prix() {
        return menu_boul_prix;
    }

    public void setMenu_boul_prix(String menu_boul_prix) {
        this.menu_boul_prix = menu_boul_prix;
    }

    public String getMenu_boul_image() {
        return menu_boul_image;
    }

    public void setMenu_boul_image(String menu_boul_image) {
        this.menu_boul_image = menu_boul_image;
    }
}
