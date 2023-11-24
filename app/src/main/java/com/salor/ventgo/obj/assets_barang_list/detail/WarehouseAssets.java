package com.salor.ventgo.obj.assets_barang_list.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WarehouseAssets {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("id_warehouse")
    @Expose
    private String idWarehouse;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("warehouse_name")
    @Expose
    private String warehouseName;
    @SerializedName("detail_item")
    @Expose
    private List<DetailItem> detailItem = null;

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

    public String getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(String idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<DetailItem> getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(List<DetailItem> detailItem) {
        this.detailItem = detailItem;
    }

}