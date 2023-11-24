package com.salor.ventgo.obj.assets_barang_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarangAssets {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("minimal_stock")
    @Expose
    private String minimalStock;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinimalStock() {
        return minimalStock;
    }

    public void setMinimalStock(String minimalStock) {
        this.minimalStock = minimalStock;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

}