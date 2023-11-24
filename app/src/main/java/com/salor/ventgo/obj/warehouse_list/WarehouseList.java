package com.salor.ventgo.obj.warehouse_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarehouseList {

    @SerializedName("id_warehouse")
    @Expose
    private String idWarehouse;
    @SerializedName("warehouse_name")
    @Expose
    private String warehouseName;
    @SerializedName("warehouse_address")
    @Expose
    private String warehouseAddress;
    @SerializedName("warehouse_created_at")
    @Expose
    private String warehouseCreatedAt;

    public WarehouseList() {
    }

    public WarehouseList(String idWarehouse, String warehouseName, String warehouseAddress, String warehouseCreatedAt) {
        this.idWarehouse = idWarehouse;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
        this.warehouseCreatedAt = warehouseCreatedAt;
    }

    public String getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(String idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getWarehouseCreatedAt() {
        return warehouseCreatedAt;
    }

    public void setWarehouseCreatedAt(String warehouseCreatedAt) {
        this.warehouseCreatedAt = warehouseCreatedAt;
    }

    @Override
    public String toString() {
        return warehouseName;
    }
}