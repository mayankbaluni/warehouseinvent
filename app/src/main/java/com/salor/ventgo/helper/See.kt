package com.salor.ventgo.helper

import android.content.Context
import android.util.Log
import android.widget.Toast

public class See {

    companion object {

        var TAG: String = "log_lippo_gudang"

        public fun log(message: String) {

            try {
                Log.d(TAG, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        public fun log(key: String, message: String) {
            try {
                Log.d(TAG, key + " -> " + message);
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }

        public fun logE(message: String) {
            try {
                Log.e(TAG, message);
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }


        public fun logE(key : String, message : String){
            try {
                Log.e(TAG, key + " -> " + message);
            } catch ( e : NullPointerException) {
                e.printStackTrace()
            }
        }


        public fun toast(context : Context, message : String){
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch ( e : IllegalStateException) {
                e.printStackTrace()
            }
        }
    }


}