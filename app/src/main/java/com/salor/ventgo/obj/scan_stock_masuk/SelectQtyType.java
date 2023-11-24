package com.salor.ventgo.obj.scan_stock_masuk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectQtyType {
    @SerializedName("qty_type")
    @Expose
    private String qtyType;

    public String getQtyType() {
        return qtyType;
    }

    public void setQtyType(String qtyType) {
        this.qtyType = qtyType;
    }

}