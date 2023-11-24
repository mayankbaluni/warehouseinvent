package com.salor.ventgo.obj.assets_barang_list.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailItem {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("id_warehouse")
    @Expose
    private String idWarehouse;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("id_item_out_asset")
    @Expose
    private Object idItemOutAsset;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("id_stock_opname_asset")
    @Expose
    private Object idStockOpnameAsset;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(String idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public Object getIdItemOutAsset() {
        return idItemOutAsset;
    }

    public void setIdItemOutAsset(Object idItemOutAsset) {
        this.idItemOutAsset = idItemOutAsset;
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

    public Object getIdStockOpnameAsset() {
        return idStockOpnameAsset;
    }

    public void setIdStockOpnameAsset(Object idStockOpnameAsset) {
        this.idStockOpnameAsset = idStockOpnameAsset;
    }

}