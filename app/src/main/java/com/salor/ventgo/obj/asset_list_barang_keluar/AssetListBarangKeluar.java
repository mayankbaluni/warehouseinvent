package com.salor.ventgo.obj.asset_list_barang_keluar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetListBarangKeluar {

    @SerializedName("id_item_in_asset")
    @Expose
    private String idItemInAsset;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item_sku")
    @Expose
    private String itemSku;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("warehouse_name")
    @Expose
    private String warehouseName;

    public String getIdItemInAsset() {
        return idItemInAsset;
    }

    public void setIdItemInAsset(String idItemInAsset) {
        this.idItemInAsset = idItemInAsset;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

}