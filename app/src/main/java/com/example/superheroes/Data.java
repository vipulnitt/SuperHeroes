package com.example.superheroes;

public class Data {
    int id;
    String name;
    Images images;
    Appearance appearance;
    String slug;
    Powerstats powerstats;

    public Powerstats getPowerstats() {
        return powerstats;
    }

    public void setPowerstats(Powerstats powerstats) {
        this.powerstats = powerstats;
    }

    public Appearance getApperance() {
        return appearance;
    }

    public void setApperance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Data(){}
    public Data(int id,String name,Images images,Appearance appearance){
        this.appearance=appearance;
        this.id=id;
        this.name=name;
        this.images=images;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
