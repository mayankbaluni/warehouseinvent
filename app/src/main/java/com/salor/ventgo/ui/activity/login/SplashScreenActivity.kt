package com.salor.ventgo.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.See
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.activity.HomeActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SplashScreenActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setFlagFullScreen()

        See.logE("isLogin",""+DBS.with(this@SplashScreenActivity).isLogin)

        val handler = Handler()
        handler.postDelayed({
            if (DBS.with(this@SplashScreenActivity).isLogin == false) {

                val intent = Intent(this@SplashScreenActivity,LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                setOveridePendingTransisi(this@SplashScreenActivity)


            } else {
                val intent = Intent(this@SplashScreenActivity,HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                setOveridePendingTransisi(this@SplashScreenActivity)
//
            }
        }, 3000L)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
