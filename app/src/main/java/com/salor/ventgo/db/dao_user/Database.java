package com.salor.ventgo.db.dao_user;

import android.arch.persistence.room.RoomDatabase;

import com.salor.ventgo.helper.Cons;
import com.salor.ventgo.obj.login.DataUser;


@android.arch.persistence.room.Database(entities = {DataUser.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static final String DB_NAME = Cons.DB_NAME_LOGIN_USER;

    public abstract DaoAccess daoAccess();

}