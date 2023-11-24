package com.salor.ventgo.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.salor.ventgo.helper.Cons;


/**
 * Created by yudho utomo on 3/29/16.
 */
public class DBS {

    private static DBS instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public DBS(Context context) {
        pref = context.getSharedPreferences(Cons.PREF_NAME, Cons.PRIVATE_MODE);
    }

    public static DBS with(Fragment fragment) {
        if (instance == null) {
            instance = new DBS(fragment.getContext());
        }
        return instance;
    }

    public static DBS with(Context context) {
        if (instance == null) {
            instance = new DBS(context);
        }
        return instance;
    }

    public void saveFcmId(String fcmId) {
        editor = pref.edit();
        editor.putString(Cons.PREF_FCMID, fcmId);
        editor.apply();
    }

    public String getFcmId() {
        return pref.getString(Cons.PREF_FCMID, "null");
    }


    public void saveIdUser(String value) {
        editor = pref.edit();
        editor.putString(Cons.ID_USER, value);
        editor.apply();
    }

    public String getIdUser() {
        return pref.getString(Cons.ID_USER, "null");
    }


    public void saveDataImageProfile(String value) {
        editor = pref.edit();
        editor.putString(Cons.IMAGE_USER_PROFILE, value);
        editor.apply();
    }

    public String getDataImageProfile() {
        return pref.getString(Cons.IMAGE_USER_PROFILE, "null");
    }



    public void savePasswordUser(String value) {
        editor = pref.edit();
        editor.putString(Cons.PASSWORD_USER, value);
        editor.apply();
    }

    public String getPasswordUser() {
        return pref.getString(Cons.PASSWORD_USER, "null");
    }



    public void saveRoomSiang(String value) {
        editor = pref.edit();
        editor.putString(Cons.ROOM_SIANG, value);
        editor.apply();
    }

    public String getRoomSiang() {
        return pref.getString(Cons.ROOM_SIANG, " - ");
    }


    public Boolean isLogin() {
        return pref.getBoolean(Cons.PREF_LOGIN, false);
    }

    public void setLogin(Boolean b) {
        editor = pref.edit();
        editor.putBoolean(Cons.PREF_LOGIN, b);
        editor.apply();
    }

    // TODO: 03/10/18 save data name user

    public void saveName(String value) {
        editor = pref.edit();
        editor.putString(Cons.NAME_USER, value);
        editor.apply();
    }

    public String getName() {
        return pref.getString(Cons.NAME_USER, " - ");
    }

    // TODO: 03/10/18 save data email user

    public void saveEmail(String value) {
        editor = pref.edit();
        editor.putString(Cons.EMAIL_USER, value);
        editor.apply();
    }

    public String getEmail() {
        return pref.getString(Cons.EMAIL_USER, " - ");
    }

    // TODO: 03/10/18 save job title
    public void saveJobTitle(String value) {
        editor = pref.edit();
        editor.putString(Cons.JOB_TITLE_USER, value);
        editor.apply();
    }

    public String getJobTitle() {
        return pref.getString(Cons.JOB_TITLE_USER, " - ");
    }


    // TODO: 03/10/18 save organitation
    public void saveOrganitation(String value) {
        editor = pref.edit();
        editor.putString(Cons.ORGANITATION_USER, value);
        editor.apply();
    }

    public String getOrganitation() {
        return pref.getString(Cons.ORGANITATION_USER, " - ");
    }


}

