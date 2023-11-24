package com.salor.ventgo.obj.riwayat_stock_opname_stock.list_stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockOpnameListStock {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("id_stock_opname")
    @Expose
    private String idStockOpname;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_sku")
    @Expose
    private String itemSku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getIdStockOpname() {
        return idStockOpname;
    }

    public void setIdStockOpname(String idStockOpname) {
        this.idStockOpname = idStockOpname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

}