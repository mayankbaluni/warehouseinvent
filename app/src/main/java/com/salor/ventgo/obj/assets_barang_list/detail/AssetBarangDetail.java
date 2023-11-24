package com.salor.ventgo.obj.assets_barang_list.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssetBarangDetail {
    @SerializedName("api_status")
    @Expose
    private Long apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_response_fields")
    @Expose
    private List<String> apiResponseFields = null;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("minimal_stock")
    @Expose
    private String minimalStock;
    @SerializedName("id_cms_users")
    @Expose
    private String idCmsUsers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("warehouse")
    @Expose
    private List<WarehouseAssets> warehouse = null;

    public Long getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Long apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public List<String> getApiResponseFields() {
        return apiResponseFields;
    }

    public void setApiResponseFields(List<String> apiResponseFields) {
        this.apiResponseFields = apiResponseFields;
    }

    public String getApiAuthorization() {
        return apiAuthorization;
    }

    public void setApiAuthorization(String apiAuthorization) {
        this.apiAuthorization = apiAuthorization;
    }

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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<WarehouseAssets> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<WarehouseAssets> warehouse) {
        this.warehouse = warehouse;
    }

}
