package com.salor.ventgo.obj.riwayat_stock_opname_asset.list_scanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListScanned {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(String idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
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

}