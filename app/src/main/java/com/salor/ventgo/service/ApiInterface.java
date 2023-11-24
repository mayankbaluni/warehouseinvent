package com.salor.ventgo.service;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {


    @POST("asset_barang_masuk_scan")
    Call<ResponseBody> scanAssetBarangMasuk(
            @Query("code") String code
    );

    @POST("asset_barang_keluar_scan")
    Call<ResponseBody> scanAssetBarangKeluar(
            @Query("code") String code
    );

    @POST("warehouse_list")
    Call<ResponseBody> warehouseList(
            @Query("id_cms_users") int id_cms_users
    );

    @POST("user_update_password")
    Call<ResponseBody> updatePassword(
            @Query("id") int id,
            @Query("password") String password
    );


    @FormUrlEncoded
    @POST("user_update_photo")
    Call<ResponseBody> updatePhotoProfile(
            @Field("id") int id,
            @Field("photo") String photo
    );


    @POST("user_login")
    Call<ResponseBody> userLogin(
            @Query("email") String username,
            @Query("password") String password
    );


    @POST("stock_opname_stock_item_update_qty")
    Call<ResponseBody> stockOpnameStockUpdateQty(
            @Query("id") String id,
            @Query("qty") String qty
    );


    @POST("user_forgot_password")
    Call<ResponseBody> forgotPassword(
            @Query("email") String email
    );


    @POST("asset_barang_masuk_list")
    Call<ResponseBody> listAssetBarangMasuk(
            @Query("id_warehouse") int id_warehouse,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("search") String search
    );


    @POST("asset_barang_masuk_add")
    Call<ResponseBody> assetBarangMasukAdd(
            @Query("id_cms_users") int id_cms_users,
            @Query("code") String code,
            @Query("id_item") int id_item,
            @Query("id_warehouse") int id_warehouse,
            @Query("description") String description,
            @Query("no_po") String no_po
    );


    @POST("asset_barang_keluar_list")
    Call<ResponseBody> listAssetBarangkeluar(
            @Query("id_warehouse") int id_warehouse,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("search") String search
    );


    @POST("asset_barang_keluar_add")
    Call<ResponseBody> assetBarangkeluarAdd(
            @Query("id_cms_users") int id_cms_users,
            @Query("code") String code,
            @Query("id_item") int id_item,
            @Query("description") String description
    );


    @POST("stock_opname_asset_list")
    Call<ResponseBody> stockOpnameAssetList(
            @Query("id_warehouse") int id_warehouse,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("search") String search,
            @Query("status") String status
    );


    @POST("stock_opname_stock_list")
    Call<ResponseBody> stockOpnameStockList(
            @Query("id_warehouse") int id_warehouse,
            @Query("search") String search,
            @Query("status") String status
    );


    // TODO add data stock opname
    @POST("stock_opname_asset_add")
    Call<ResponseBody> stockOpnameAssetAdd(
            @Query("name") String name,
            @Query("id_cms_users") int id_cms_users,
            @Query("id_warehouse") int id_warehouse
    );


    // TODO add data stock opname
    @POST("stock_opname_stock_add")
    Call<ResponseBody> stockOpnameStockAdd(
            @Query("name") String name,
            @Query("id_cms_users") int id_cms_users,
            @Query("id_warehouse") int id_warehouse
    );


    @POST("stock_opname_asset_item_list")
    Call<ResponseBody> stockOpnameAssetItemList(
            @Query("id_stock_opname_asset") int id_stock_opname_asset
    );


    @POST("stock_opname_stock_item_list")
    Call<ResponseBody> stockOpnameStockItemList(
            @Query("id_stock_opname") int id_stock_opname
    );


    @POST("stock_opname_stock_publish")
    Call<ResponseBody> stockOpnameStockPublish(
            @Query("id") String id,
            @Query("id_cms_users") String id_cms_users
    );


    // TODO load detail scanned -- > scanned list
    @POST("stock_opname_asset_item_scaned_list")
    Call<ResponseBody> stockOpnameAssetItemScannedList(
            @Query("id_stock_opname_asset") int id_stock_opname_asset,
            @Query("id_item") int id_item
    );


    @POST("asset_barang_list")
    Call<ResponseBody> assetBarangList(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("search") String search
    );


    @POST("asset_barang_detail")
    Call<ResponseBody> assetBarangListDetail(
            @Query("id") String id
    );


    @POST("stock_opname_asset_item_scaned_delete")
    Call<ResponseBody> stockOpnameAssetItemScannedDelete(
            @Query("id_stock_opname_asset") String id_stock_opname_asset,
            @Query("id") String id
    );


    // TODO Stockopname add scan asset item
    @POST("stock_opname_asset_item_scaned_add")
    Call<ResponseBody> stockOpnameAssetItemScannedAdd(
            @Query("code") String code,
            @Query("id_stock_opname_asset") int id_stock_opname_asset,
            @Query("id_cms_users") int id_cms_users
    );

    @POST("stock_opname_asset_publish")
    Call<ResponseBody> stockOpnameAssetPublish(
            @Query("id") int id,
            @Query("id_cms_users") int id_cms_users
    );


    @POST("stock_barang_list")
    Call<ResponseBody> stockOpnameStock(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("search") String search
    );


    @POST("stock_barang_detail")
    Call<ResponseBody> stockBarangDetail(
            @Query("id") String id
    );


    @POST("stock_barang_masuk_scan")
    Call<ResponseBody> stockBarangMasukScan(
            @Query("sku") String sku
    );


    @POST("stock_opname_stock_item_scan")
    Call<ResponseBody> stockOpnameStockScan(
            @Query("sku") String sku
    );


    @POST("stock_opname_stock_item_add")
    Call<ResponseBody> stockOpnameStockAddItem(
            @Query("id_item") String id_item,
            @Query("id_cms_users") String id_cms_users,
            @Query("qty_type") String qty_type,
            @Query("qty") String qty,
            @Query("sku") String sku,
            @Query("id_stock_opname") String id_stock_opname
    );


    @POST("stock_barang_masuk_add")
    Call<ResponseBody> stockBarangMasukAdd(
            @Query("sku") String sku,
            @Query("id_item") int id_item,
            @Query("id_warehouse") int id_warehouse,
            @Query("qty_type") String qty_type,
            @Query("qty") String qty,
            @Query("description") String description,
            @Query("id_cms_users") int id_cms_users,
            @Query("no_po") String no_po
    );


    @POST("stock_barang_masuk_list")
    Call<ResponseBody> stockBarangMasukList(
            @Query("id_warehouse") int id_warehouse,
            @Query("search") String search

    );


    @POST("stock_barang_keluar_scan")
    Call<ResponseBody> stockBarangKeluarScan(
            @Query("sku") String sku

    );


    @POST("stock_barang_keluar_add")
    Call<ResponseBody> stockBarangKeluarAdd(
            @Query("sku") String sku,
            @Query("id_item") int id_item,
            @Query("id_warehouse") int id_warehouse,
            @Query("qty_type") String qty_type,
            @Query("qty") String qty,
            @Query("description") String description,
            @Query("id_cms_users") int id_cms_users

    );


    @POST("stock_barang_keluar_list")
    Call<ResponseBody> stockBarangKeluarList(
            @Query("id_warehouse") int id_warehouse,
            @Query("search") String search

    );


}
