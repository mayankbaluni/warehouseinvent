package com.salor.ventgo.ui.activity.stock_opname_assets.list_scanned_product.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
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
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.riwayat_stock_opname_asset.list_asset.StockOpnameListAsset
import com.salor.ventgo.obj.riwayat_stock_opname_asset.list_asset.detail_asset_scanned.DetailItemScanned
import com.salor.ventgo.obj.riwayat_stock_opname_asset.list_scanned.ListScanned
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.adapter.stock_opname_assets.scanned_product.detail.ListDetailScannedProductStockOpnameAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_stock_opname_detail_scanned_product.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class StockOpnameListDetailScannedProductActivity : BaseActivity() {

    var isDelete : Boolean = false
    var status_barang : String = ""
    var id_stock_opname : String = ""
    lateinit var pbLoading : Loading
    lateinit var dataAsset : StockOpnameListAsset
    var listData : ArrayList<ListScanned> = ArrayList()
    lateinit var listScannedProductStockOpnameAdapter: ListDetailScannedProductStockOpnameAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_opname_detail_scanned_product)

        setStatusBarGradiantLogin(this)

        pbLoading = Loading(this)

        status_barang = intent.getStringExtra(Cons.STATUS_STOCK)
        id_stock_opname = intent.getStringExtra(Cons.ID_STOCK_OPNAME)
        val str_json = intent.getStringExtra(Cons.JSON)
        dataAsset = Gson().fromJson(str_json,StockOpnameListAsset::class.java)



        // TODO set data detail list assset
        tvTitle.text = dataAsset.itemName
        tvGudang.text = dataAsset.warehouse
        tvQty.text = dataAsset.qty.toString()
        tvDate.text = DateTimeMasker.changeToNameMonthCustom(dataAsset.lastInsert)

        // TODO set adapter
        setData()

        rBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        getDataListScanned(lLoading)

    }

    private fun getDataListScanned(pbLoading: LinearLayout) {
        pbLoading.visibility = View.VISIBLE
        rvListGudang.visibility = View.GONE
        val service = ApiClient.getClient()

        val call = service.stockOpnameAssetItemScannedList(id_stock_opname.toInt(),dataAsset.idItem.toInt())

        See.logE("id_item", "" + dataAsset.idItem)
        See.logE("id_stock_opname_asset", "" + id_stock_opname)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                pbLoading.visibility = View.GONE
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_asset_list", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            listData.clear()

                            rvListGudang.visibility = View.VISIBLE

                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)

                            val listAsset = Gson().fromJson(jsonDataArray.toString(), Array<ListScanned>::class.java).toList()

                            listData.addAll(listAsset)

                            listScannedProductStockOpnameAdapter.notifyDataSetChanged()

                            setAnimHeader()

                            val jsonDetail = json.getJSONObject("detail_item")

                            val detailItem = Gson().fromJson(jsonDetail.toString(),DetailItemScanned::class.java)

                            // TODO set data detail list assset
//                            tvTitle.text = detailItem.itemName
//                            tvGudang.text = detailItem.warehouse

                            try{
                                tvQty.text = detailItem.qty.toString()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }


                            try{
                                tvDate.text = DateTimeMasker.changeDetailMonthCustom(detailItem.lastInsert)
                            }catch (e : Exception){
                                e.printStackTrace()
                                tvDate.text = DateTimeMasker.changeToNameMonthCustom(dataAsset.lastInsert)

                            }

                            //  lAddStock.visibility = View.VISIBLE

                        } else {
                            pbLoading.visibility = View.GONE
                            See.toast(this@StockOpnameListDetailScannedProductActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    pbLoading.visibility = View.GONE
                    dialogFailure(resources.getString(R.string.label_failure_content_server_title),resources.getString(R.string.label_failure_content_server_content))

                    //            See.toast(this@StockOpnameListDetailScannedProductActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {

                pbLoading.visibility = View.GONE
                dialogFailure(resources.getString(R.string.label_failure_content1),resources.getString(R.string.label_failure_content2))

            }
        })

    }


    fun setData(){

        listScannedProductStockOpnameAdapter = ListDetailScannedProductStockOpnameAdapter(this, listData)
        rvListGudang.setAdapter(listScannedProductStockOpnameAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvListGudang.setLayoutManager(layoutManager)
        rvListGudang.isNestedScrollingEnabled = false

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
                getDataListScanned(lLoading)
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

    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvDetailScan.visibility = View.VISIBLE
                tvGudang.visibility = View.VISIBLE
                tvQty.visibility = View.VISIBLE
                tvDate.visibility = View.VISIBLE


            }, 500)
        } catch (e: Exception) {
            e.printStackTrace()
            tvDetailScan.visibility = View.VISIBLE
            tvGudang.visibility = View.VISIBLE
            tvQty.visibility = View.VISIBLE
            tvDate.visibility = View.VISIBLE

        }

    }

    fun deleteDataScanned(id : String) {
        pbLoading.showLoading(resources.getString(R.string.label_loading_title_dialog),false)
        val service = ApiClient.getClient()

        val call = service.stockOpnameAssetItemScannedDelete(id_stock_opname,id)

        See.logE("id_stock_opname_asset", "" + dataAsset.idItem)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                pbLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_asset_list", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            See.toast(this@StockOpnameListDetailScannedProductActivity,api_message)

                            isDelete = true

                            getDataListScanned(lLoading)
                            //  lAddStock.visibility = View.VISIBLE

                        } else {
                            pbLoading.dismissDialog()
                            See.toast(this@StockOpnameListDetailScannedProductActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    pbLoading.dismissDialog()
                    See.toast(this@StockOpnameListDetailScannedProductActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {

                pbLoading.dismissDialog()
                See.toast(this@StockOpnameListDetailScannedProductActivity,resources.getString(R.string.label_koneksi_error))

            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {

        if(isDelete){

            val intent = Intent()
            setResult(Cons.RES_DETAIL_STOCK,intent)
            finish()
            setOveridePendingTransisi(this@StockOpnameListDetailScannedProductActivity)

        }else{
            super.onBackPressed()
            setOveridePendingTransisi(this@StockOpnameListDetailScannedProductActivity)
        }
    }
}
