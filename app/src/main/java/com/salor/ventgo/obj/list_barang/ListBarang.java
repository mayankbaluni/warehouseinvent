package com.salor.ventgo.obj.list_barang;

public class ListBarang {

    int id;
    String title;
    String quantity;
    String date;

    public ListBarang() {
    }

    public ListBarang(int id, String title, String quantity, String date) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.date = date;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
