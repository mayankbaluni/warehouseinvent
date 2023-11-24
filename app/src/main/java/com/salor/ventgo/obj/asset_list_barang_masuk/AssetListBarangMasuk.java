package com.salor.ventgo.obj.asset_list_barang_masuk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetListBarangMasuk {

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
    private String description;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("id_item_out_asset")
    @Expose
    private String idItemOutAsset;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("no_po")
    @Expose
    private String noPo;
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

    public String getIdItemOutAsset() {
        return idItemOutAsset;
    }

    public void setIdItemOutAsset(String idItemOutAsset) {
        this.idItemOutAsset = idItemOutAsset;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNoPo() {
        return noPo;
    }

    public void setNoPo(String noPo) {
        this.noPo = noPo;
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