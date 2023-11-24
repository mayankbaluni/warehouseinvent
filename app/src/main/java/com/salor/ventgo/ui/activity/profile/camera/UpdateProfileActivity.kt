package com.salor.ventgo.ui.activity.profile.camera

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.LoadImage
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity

import com.github.chrisbanes.photoview.OnMatrixChangedListener
import com.github.chrisbanes.photoview.OnPhotoTapListener
import com.github.chrisbanes.photoview.OnSingleFlingListener
import com.github.chrisbanes.photoview.PhotoView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_dialog_change_password_success.*

import org.json.JSONException
import org.json.JSONObject

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.ArrayList
import java.util.Calendar

import me.shaohui.advancedluban.Luban
import me.shaohui.advancedluban.OnCompressListener
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : BaseActivity() {

    var str_message_dialog : String = ""
    lateinit var mPhotoView: PhotoView
    lateinit var loadImage: LoadImage
    lateinit var pLoading: Loading
    lateinit var lCancel: LinearLayout
    lateinit var lKirim: LinearLayout
    internal var c = Calendar.getInstance()
    lateinit var str_photo: String
    internal var id_order: Int = 0
    var stringList: ArrayList<String>? = null
    lateinit var cancel: TextView
    lateinit var simpan: TextView
    internal var check = true
    internal var str_encode: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profil)

        initToolbar(resources.getString(R.string.label_update_profile))

        setStatusBarGradiantLogin(this)

        loadImage = LoadImage(this)
        pLoading = Loading(this)
        str_photo = intent.getStringExtra("str_photo")

        Log.e("str_photo", str_photo)

        initUI()

    }

    fun initUI() {
        id_order = intent.getIntExtra("id_order", 0)
        lCancel = findViewById(R.id.lCancel) as LinearLayout
        lKirim = findViewById(R.id.lKirim) as LinearLayout
        cancel = findViewById(R.id.cancel) as TextView
        simpan = findViewById(R.id.simpan) as TextView
        mPhotoView = findViewById(R.id.iv_photo) as PhotoView

        mPhotoView!!.setOnMatrixChangeListener(MatrixChangeListener())
        mPhotoView!!.setOnPhotoTapListener(PhotoTapListener())
        mPhotoView!!.setOnSingleFlingListener(SingleFlingListener())

        cancel.visibility = View.GONE

        val file_photo = File(str_photo)

        Log.e("str_encode", "" + str_encode!!)
        lKirim.setOnClickListener {
            Luban.compress(this@UpdateProfileActivity, file_photo)
                    .setMaxSize(200).launch(object : OnCompressListener {
                        override fun onStart() {

                        }

                        override fun onSuccess(file: File) {
                            try {
                                pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
                                str_encode = encodeImageWithOutDirectory(file.absolutePath)

                                UpdatePhoto()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                        override fun onError(e: Throwable) {

                        }
                    })
        }

        loadImage.LoadImagePicassoWithOutProgressbar(Uri.fromFile(File(str_photo)).toString(), mPhotoView)

    }


    private inner class PhotoTapListener : OnPhotoTapListener {

        override fun onPhotoTap(view: ImageView, x: Float, y: Float) {

        }
    }

    private inner class MatrixChangeListener : OnMatrixChangedListener {

        override fun onMatrixChanged(rect: RectF) {}
    }

    private inner class SingleFlingListener : OnSingleFlingListener {

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return true
        }
    }


    private fun UpdatePhoto() {
        val service = ApiClient.getClient()
        val gson = Gson()
        val id_member = DBS.with(this).idUser.toInt()
        See.logE("id_member", "" + id_member)
        See.logE("str_encode server", "" + str_encode!!)

        val call = service.updatePhotoProfile(id_member, str_encode)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_update_photo", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        str_message_dialog = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

//                            photoImage = json.getString("image")

                            Log.e("response_photo", "" + Uri.fromFile(File(str_photo)).toString())

                            DBS.with(this@UpdateProfileActivity).saveDataImageProfile(Uri.fromFile(File(str_photo)).toString())

                            dialogSuccessProfile()

                        } else {
                            Toast.makeText(this@UpdateProfileActivity, str_message_dialog, Toast.LENGTH_SHORT).show()
                            Log.e("toast edit", str_message_dialog)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pLoading.dismissDialog()
                    Toast.makeText(this@UpdateProfileActivity, resources.getString(R.string.label_something_wrong), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pLoading.dismissDialog()
                Toast.makeText(this@UpdateProfileActivity, resources.getString(R.string.label_koneksi_error), Toast.LENGTH_SHORT).show()
            }
        })

    }

//    @SuppressLint("StaticFieldLeak")
//    private fun fetchTodoById(todo_id: Int) {
//
//        try {
//            object : AsyncTask<Int, Void, DataLogin>() {
//                override fun doInBackground(vararg p0: Int?): DataLogin? {
//
//                    return userDatabase.daoAccess().fetchUserListById(p0[0]!!)
//
//                }
//
//                override fun onPostExecute(user: DataLogin) {
//                    super.onPostExecute(user)
//
//                    dataProfil = user
//
//                }
//            }.execute(todo_id)
//        }catch (e : Exception){
//            e.printStackTrace()
//        }
//
//
//    }
//

//    @SuppressLint("StaticFieldLeak")
//    private fun updateRow(todo: DataLogin) {
//
//        try {
//            object : AsyncTask<DataLogin, Void, Int>() {
//                override fun doInBackground(vararg params: DataLogin): Int? {
//                    return userDatabase.daoAccess().updateUser(params[0])
//                }
//
//                override fun onPostExecute(number: Int?) {
//                    super.onPostExecute(number)
//
//                    try{
//                        dialogSuccessProfile()
//                    }catch (e : Exception){
//                        e.printStackTrace()
//                    }
//
//                }
//            }.execute(todo)
//
//        }catch (e : Exception){
//            e.printStackTrace()
//        }
//
//    }

    fun dialogSuccessProfile() {
        try {

            var dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.item_dialog_change_password_success)
            dialog.setCancelable(false)

            val tvTitle  = dialog.tvTitle
            val tvContentDialog  = dialog.tvContentDialog
            val btnYa  = dialog.btnYa


            tvTitle.text = resources.getString(R.string.label_header_content_dialog_update_photo1)
            tvContentDialog.text = resources.getString(R.string.label_header_content_dialog_update_photo2)
            btnYa.text = resources.getString(R.string.label_oke)


            btnYa.setOnClickListener(View.OnClickListener {
                dialog.dismiss()

                val intent = Intent()
                intent.putExtra(Cons.FilePhoto, Uri.fromFile(File(str_photo)).toString())
                setResult(Cons.RES_Camera, intent)
                finish()
            })


            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = dialog.window
            val wlp = window!!.attributes

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun dialogSuccessProfile() {
//
//        val dialog = Dialog(this, R.style.DialogLight)
//        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.dialog_quiz_answer)
//        dialog.setCancelable(false)
//
//        val tvTitle : TextView = dialog.findViewById(R.id.tvTitle)
//        val btnOke : Button = dialog.findViewById(R.id.btnOke)
//
//        tvTitle.text = str_message_dialog
//
//        btnOke.setOnClickListener {
//
//            dialog.dismiss()
//
//            val intent = Intent()
//            intent.putExtra(Cons.FilePhoto, photoImage)
//            setResult(Cons.RES_Camera, intent)
//            finish()
//        }
//
//
//        val size = Point()
//        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = wm.defaultDisplay
//        display.getSize(size)
//        val mWidth = size.x
//
//        val window = dialog.window
//        val wlp = window!!.attributes as WindowManager.LayoutParams
//
//        wlp.gravity = Gravity.CENTER
//        wlp.x = 0
//        wlp.y = 0
//        wlp.width = mWidth
//        window.attributes = wlp
//        dialog.show()
//
//    }

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



    private fun encodeImage(path: String): String? {


        val fileName = path.substring(path.lastIndexOf("/") + 1)

        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Cons.DIRECTORY_NAME)
        val file_before_encode = File(mediaStorageDir.path + File.separator + fileName)
        var encodedBase64: String? = null
        var encImage: String? = null
        try {
            val fileInputStreamReader = FileInputStream(file_before_encode)
            val bytes = ByteArray(file_before_encode.length().toInt())
            fileInputStreamReader.read(bytes)
            encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
            encImage = encodedBase64!!.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return encImage

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

