package com.salor.ventgo.obj.login;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.salor.ventgo.db.dao_user.Database;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = Database.DB_NAME)
public class DataUser {

    @PrimaryKey
    int idLokal;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id_cms_privileges")
    @Expose
    private String idCmsPrivileges;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("Address")
    @Expose
    private String address;

    public DataUser() {

    }

    public DataUser(int idLokal, String id, String name, String photo, String email, String idCmsPrivileges, String status, String password, String phoneNumber, String address) {
        this.idLokal = idLokal;
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.idCmsPrivileges = idCmsPrivileges;
        this.status = status;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getIdLokal() {
        return idLokal;
    }

    public void setIdLokal(int idLokal) {
        this.idLokal = idLokal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCmsPrivileges() {
        return idCmsPrivileges;
    }

    public void setIdCmsPrivileges(String idCmsPrivileges) {
        this.idCmsPrivileges = idCmsPrivileges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}