package com.salor.ventgo.ui.activity.stock_opname_assets.list_scanned_product

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.riwayat_stock_opname_asset.RiwayatStockOpnameList
import com.salor.ventgo.obj.riwayat_stock_opname_asset.list_asset.StockOpnameListAsset
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.activity.stock_opname_assets.list_scanned_product.scan.ScannerActivity
import com.salor.ventgo.ui.adapter.stock_opname_assets.scanned_product.ListScannedProductStockOpnameAdapter
import com.google.gson.Gson
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_stock_opname_list_scanned_product.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import kotlinx.android.synthetic.main.item_dialog_stock_opname_upload_data_success.*
import kotlinx.android.synthetic.main.item_empty_data.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class StockOpnameListScannedProductActivity : BaseActivity() {

    lateinit var itemList : StockOpnameListAsset
    var isPublish: Boolean = false
    lateinit var pbLoading: Loading
    lateinit var listRiwayat: RiwayatStockOpnameList
    var listData: ArrayList<StockOpnameListAsset> = ArrayList()
    lateinit var listScannedProductStockOpnameAdapter: ListScannedProductStockOpnameAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_opname_list_scanned_product)

        setStatusBarGradiantLogin(this)

        pbLoading = Loading(this)

        setData()

        val str_json = intent.getStringExtra(Cons.JSON)
        listRiwayat = Gson().fromJson(str_json, RiwayatStockOpnameList::class.java)

        rBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        // TODO jika status draft tidak bisa publish

        if (listRiwayat.status == Cons.PUBLISH_STATUS){

            ivPublish.setImageResource(R.drawable.ic_publish_unactive)

        }else if (listRiwayat.status == Cons.STATUS_PENDING){

            ivPublish.setImageResource(R.drawable.ic_publish_unactive)

        }else{

            ivPublish.setImageResource(R.drawable.ic_publish_active)
            rUpload.setOnClickListener(View.OnClickListener { publishBarang() })

        }

        btnScan.setOnClickListener({

            if (Build.VERSION.SDK_INT >= 23) {
                val permissionlistener = object : PermissionListener {
                    override fun onPermissionGranted() {

                        try {
                            val intent = Intent(this@StockOpnameListScannedProductActivity, ScannerActivity::class.java)
                            intent.putExtra(Cons.JSON, str_json)
                            startActivityForResult(intent,Cons.REQ_SCAN)
                            setOveridePendingTransisi(this@StockOpnameListScannedProductActivity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }

                    override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                        Toast.makeText(this@StockOpnameListScannedProductActivity, resources.getString(R.string.label_permission_diperlukan), Toast.LENGTH_SHORT).show()
                    }
                }

                TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setRationaleMessage(resources.getString(R.string.label_rational_message))
                        .setDeniedMessage(resources.getString(R.string.label_denied_message))
                        .setPermissions(Manifest.permission.CAMERA)

                        .check()


            } else {

                try {
                    val intent = Intent(this@StockOpnameListScannedProductActivity, ScannerActivity::class.java)
                    intent.putExtra(Cons.JSON, str_json)
                    startActivityForResult(intent,Cons.REQ_SCAN)
                    setOveridePendingTransisi(this@StockOpnameListScannedProductActivity)
                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }


        })


        getDataListAsset(lLoading)

    }

    private fun getDataListAsset(pbLoading: LinearLayout) {
        pbLoading.visibility = View.VISIBLE
        val service = ApiClient.getClient()

        val call = service.stockOpnameAssetItemList(listRiwayat.id.toInt())

        See.logE("id_stock_opname_asset", "" + listRiwayat.id)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pbLoading.visibility = View.GONE
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_asset_list", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            rvListScan.visibility = View.VISIBLE

                            listData.clear()

                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)

                            val listAsset = Gson().fromJson(jsonDataArray.toString(), Array<StockOpnameListAsset>::class.java).toList()

                            listData.addAll(listAsset)

                            listScannedProductStockOpnameAdapter.notifyDataSetChanged()

                            if(listAsset.isEmpty()){

                                setVisibleEmptyData()

                            }else{
                                setVisibleParent()
                            }

                            if (listRiwayat.status == Cons.DRAFT_STATUS){

                                setAnimHeader()

                            }

                            //  lAddStock.visibility = View.VISIBLE

                        } else {
                            pbLoading.visibility = View.GONE
                            See.toast(this@StockOpnameListScannedProductActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    pbLoading.visibility = View.GONE
                    dialogFailure(resources.getString(R.string.label_failure_content_server_title),resources.getString(R.string.label_failure_content_server_content))

                    //      See.toast(this@StockOpnameListScannedProductActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                pbLoading.visibility = View.GONE
                dialogFailure(resources.getString(R.string.label_failure_content1),resources.getString(R.string.label_failure_content2))

            }
        })

    }

    fun setVisibleParent() {

        setAnimViewVisible(lParentContent, rvListScan, 0)
        setAnimViewGone(lParentContent, lParentEmptyData, 0)
    }

    fun setVisibleEmptyData() {

        rvListScan.visibility = View.GONE
        setAnimViewVisible(lParentContent, lParentEmptyData, 0)

    }


    fun setData() {

        listScannedProductStockOpnameAdapter = ListScannedProductStockOpnameAdapter(this, listData)
        rvListScan.setAdapter(listScannedProductStockOpnameAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvListScan.setLayoutManager(layoutManager)
        rvListScan.isNestedScrollingEnabled = false

    }

    private fun publishBarang() {
        pbLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()

        val idUser = DBS.with(this).idUser.toInt()

        val call = service.stockOpnameAssetPublish(listRiwayat.id.toInt(), idUser)

        See.logE("id_stock_opname_asset", "" + listRiwayat.id)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pbLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_publish_barang", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            dialogUploadSuccess()

                            isPublish = true

                        } else {
                            pbLoading.dismissDialog()
                            See.toast(this@StockOpnameListScannedProductActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pbLoading.dismissDialog()
                    See.toast(this@StockOpnameListScannedProductActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pbLoading.dismissDialog()

                See.toast(this@StockOpnameListScannedProductActivity, resources.getString(R.string.label_koneksi_error))

            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Cons.REQ_SCAN && resultCode == Cons.RES_SCAN){

            isPublish = true
            rvListScan.visibility = View.GONE
            getDataListAsset(lLoading)

        }else if(requestCode == Cons.REQ_DETAIL_STOCK && resultCode == Cons.RES_DETAIL_STOCK){

            isPublish = true
            rvListScan.visibility = View.GONE
            getDataListAsset(lLoading)


        }
    }

    fun dialogUploadSuccess() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_stock_opname_upload_data_success)
            pDialog!!.setCancelable(false)

            val btnOke = pDialog.btnOke
            val tvContentDialog = pDialog.tvContentDialog

            val str_publish_data = "Submit data Stock Opname " +DateTimeMasker.changeToDatePublishStockOpname(listRiwayat.createdAt) + " berhasil"
            tvContentDialog.text = str_publish_data

            btnOke.setOnClickListener(View.OnClickListener {
                pDialog.dismiss()
                val intent = Intent()
                setResult(Cons.RES_LIST_SCAN,intent)
                finish()
                setOveridePendingTransisi(this@StockOpnameListScannedProductActivity)
            })


            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = pDialog!!.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            pDialog!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                lButtonScan.visibility = View.VISIBLE


            }, 400)
        } catch (e: Exception) {
            e.printStackTrace()
            lButtonScan.visibility = View.VISIBLE
        }


    }


    fun dialogFailure(title : String,subTitle : String) {
        try {

            var dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_failure_custom)
            dialog.setCancelable(false)

            val btnBack: Button = dialog.btnBack
            val btnRefresh: Button = dialog.btnRefresh

            val tv_Content: TextView = dialog.tv_Content
            val tvContent2: TextView = dialog.tvContent2

            tv_Content.text = title
            tvContent2.text = subTitle


            btnBack.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                onBackPressed()
            })

            btnRefresh.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                getDataListAsset(lLoading)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (isPublish) {

            val intent = Intent()
            setResult(Cons.RES_LIST_SCAN,intent)
            finish()
            setOveridePendingTransisi(this@StockOpnameListScannedProductActivity)

        } else {
            super.onBackPressed()
            setOveridePendingTransisi(this@StockOpnameListScannedProductActivity)
        }
    }
}
