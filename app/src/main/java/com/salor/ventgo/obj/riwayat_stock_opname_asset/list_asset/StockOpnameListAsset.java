package com.salor.ventgo.obj.riwayat_stock_opname_asset.list_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockOpnameListAsset {

    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_sku")
    @Expose
    private String itemSku;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("qty")
    @Expose
    private Long qty;
    @SerializedName("last_insert")
    @Expose
    private String lastInsert;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getLastInsert() {
        return lastInsert;
    }

    public void setLastInsert(String lastInsert) {
        this.lastInsert = lastInsert;
    }

}