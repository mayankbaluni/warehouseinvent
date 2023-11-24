package com.salor.ventgo.obj;

public class DataHome {

    int id;
    String title;
    String description1;
    String description2;
    int image;

    public DataHome() {
    }

    public DataHome(int id, String title, String description1, String description2, int image) {
        this.id = id;
        this.title = title;
        this.description1 = description1;
        this.description2 = description2;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
