package com.salor.ventgo.obj.scan_stock_masuk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScanStockMasuk {
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
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("qty_type")
    @Expose
    private String qtyType;
    @SerializedName("qty_type_count")
    @Expose
    private String qtyTypeCount;
    @SerializedName("select_qty_type")
    @Expose
    private List<SelectQtyType> selectQtyType = null;


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

    public String getDescription() {
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

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getQtyType() {
        return qtyType;
    }

    public void setQtyType(String qtyType) {
        this.qtyType = qtyType;
    }

    public String getQtyTypeCount() {
        return qtyTypeCount;
    }

    public void setQtyTypeCount(String qtyTypeCount) {
        this.qtyTypeCount = qtyTypeCount;
    }

    public List<SelectQtyType> getSelectQtyType() {
        return selectQtyType;
    }

    public void setSelectQtyType(List<SelectQtyType> selectQtyType) {
        this.selectQtyType = selectQtyType;
    }
}