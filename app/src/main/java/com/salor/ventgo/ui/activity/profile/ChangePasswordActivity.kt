package com.salor.ventgo.ui.activity.profile

import android.app.Dialog
import android.arch.persistence.room.Room
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.db.dao_user.Database
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.login.DataUser
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.item_dialog_change_password_success.*
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class ChangePasswordActivity : BaseActivity() {

    lateinit var dataUser : DataUser
    lateinit var userDatabase : Database
    lateinit var pLoading : Loading
    var str_password_now : String = ""
    var str_password_new : String = ""
    var str_password_retype : String = ""

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        try {
            userDatabase = Room.databaseBuilder(applicationContext, Database::class.java, Cons.DB_NAME_LOGIN_USER).fallbackToDestructiveMigration().build()
        }catch (e : Exception){
            e.printStackTrace()
        }

        doAsync {

            dataUser = userDatabase.daoAccess().fetchUserListById(Cons.INT_ID)

        }

        setStatusBarGradiantLogin(this)

        pLoading = Loading(this)

        initToolbar(resources.getString(R.string.label_ganti_password_profile))

        //setAnimHeader()

        btnSave.setOnClickListener(View.OnClickListener {

            str_password_now = etPasswordNow.text.toString()
            str_password_new = etPasswordNew.text.toString()
            str_password_retype = etPasswordRetype.text.toString()

            if (str_password_now == ""){

                etPasswordNow.requestFocus()
                etPasswordNow.error = resources.getString(R.string.label_form_wajib_diisi)

            }else if (str_password_new == ""){

                etPasswordNew.requestFocus()
                etPasswordNew.error = resources.getString(R.string.label_form_wajib_diisi)


            }else if (str_password_retype == ""){

                etPasswordRetype.requestFocus()
                etPasswordRetype.error = resources.getString(R.string.label_form_wajib_diisi)

            }else if (str_password_new != str_password_retype){

                See.toast(this@ChangePasswordActivity,resources.getString(R.string.label_retype_wrong))

            }else if (DBS.with(this@ChangePasswordActivity).passwordUser != str_password_now){

                See.toast(this@ChangePasswordActivity,resources.getString(R.string.label_lama_masih_sama))

            }else if (DBS.with(this@ChangePasswordActivity).passwordUser == str_password_new){

                See.toast(this@ChangePasswordActivity,resources.getString(R.string.label_password_tidak_boleh_sama))

            }else{

                updatePassword()

            }

        })

    }

    private fun updatePassword() {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()
        val gson = Gson()

        val idUser = DBS.with(this@ChangePasswordActivity).idUser.toInt()

        val call = service.updatePassword(idUser,str_password_new)
        See.logE("idUser", "" + idUser)
        See.logE("str_password_new", "" + str_password_new)

        See.logE(Cons.CALLRESPONSE,""+call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_update", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            DBS.with(this@ChangePasswordActivity).savePasswordUser(str_password_new)

                            dialogChangeSuccess()

                        } else {

                            See.toast(this@ChangePasswordActivity, api_message)

                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pLoading.dismissDialog()
                    See.toast(this@ChangePasswordActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pLoading.dismissDialog()
                See.toast(this@ChangePasswordActivity, resources.getString(R.string.label_koneksi_error))
            }
        })

    }


    fun dialogChangeSuccess() {
        try {
            val dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.item_dialog_change_password_success)
            dialog.setCancelable(false)

            val btnYa = dialog.btnYa

            btnYa.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                onBackPressed()
            })


            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = dialog.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            dialog.show()
        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    fun setAnimHeader() {


        try {
            Handler().postDelayed({

                try {
                    TransitionManager.beginDelayedTransition(lParentContent)
                    lBottom.visibility = View.VISIBLE
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, 650)
        } catch (e: Exception) {
            e.printStackTrace()
            lBottom.visibility = View.VISIBLE
        }

    }


    fun initToolbar(vTitle: String) {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewActionBar = layoutInflater.inflate(R.layout.toolbar_layout, null)
        val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)

        val tvTitle = viewActionBar.findViewById<View>(R.id.actionbar_textview) as TextView
        tvTitle.text = vTitle
        supportActionBar!!.setCustomView(viewActionBar, params)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@ChangePasswordActivity)
    }
}
