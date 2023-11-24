package com.salor.ventgo.obj.riwayat_stock_opname_stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiwayatStockOpnameList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id_warehouse")
    @Expose
    private String idWarehouse;
    @SerializedName("publish_by")
    @Expose
    private String publishBy;
    @SerializedName("total_stock_opname")
    @Expose
    private Long totalStockOpname;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(String idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public Long getTotalStockOpname() {
        return totalStockOpname;
    }

    public void setTotalStockOpname(Long totalStockOpname) {
        this.totalStockOpname = totalStockOpname;
    }
}