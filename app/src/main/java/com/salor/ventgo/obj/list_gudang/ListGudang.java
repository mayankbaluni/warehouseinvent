package com.salor.ventgo.obj.list_gudang;

public class ListGudang {

    int id;
    String title;
    String desc1;
    String desc2;
    int image;


    public ListGudang() {
    }

    public ListGudang(int id, String title, String desc1, String desc2, int image) {
        this.id = id;
        this.title = title;
        this.desc1 = desc1;
        this.desc2 = desc2;
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

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
