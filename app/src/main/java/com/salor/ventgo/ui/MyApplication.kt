package com.salor.ventgo.ui


import android.os.StrictMode
import com.salor.ventgo.R
import com.facebook.stetho.Stetho
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MyApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        // TODO: 09/03/18 set CalligraphyConfig Text Font
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/open_sans_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )


        Stetho.initializeWithDefaults(this)


    }

}
