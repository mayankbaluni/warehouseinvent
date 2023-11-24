package com.salor.ventgo.ui.activity.barang_assets.detail_gudang

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.assets_barang_list.BarangAssets
import com.salor.ventgo.obj.assets_barang_list.detail.DetailItem
import com.salor.ventgo.obj.assets_barang_list.detail.WarehouseAssets
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.adapter.barang_assets.detail_list_barang_scan.ListBarangScanAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_barang_detail_gudang.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DetailGudangActivity : BaseActivity() {

    lateinit var barangAssets : BarangAssets
    var listDetailItem : ArrayList<DetailItem> = ArrayList()
    lateinit var warehouseAssets : WarehouseAssets
    lateinit var listBarangScan : ListBarangScanAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_barang_detail_gudang)

        setStatusBarGradiantListSearch(this)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        val str_json = intent.getStringExtra(Cons.JSON)
        val str_json_barang_assets = intent.getStringExtra(Cons.JSON_BARANG_ASSETS)
        warehouseAssets = Gson().fromJson(str_json,WarehouseAssets::class.java)

        barangAssets = Gson().fromJson(str_json_barang_assets, BarangAssets::class.java)

        tvTitleToolbar.text = warehouseAssets.warehouseName

        tvTitle.text = barangAssets.name
        tvQty.text = barangAssets.stock + " " + resources.getString(R.string.label_pcs)
        tvDate.text = DateTimeMasker.changeToNameMonthCustom(barangAssets.createdAt)


        listDetailItem = warehouseAssets.detailItem as ArrayList<DetailItem>

        setData()

        setAnimViewVisible(lParentContent,tvBarangAssetsDummy,700)

    }

    fun setData(){

        listBarangScan = ListBarangScanAdapter(this, listDetailItem)
        rvListGudang.setAdapter(listBarangScan)
        val layoutManager = LinearLayoutManager(this)
        rvListGudang.setLayoutManager(layoutManager)
        rvListGudang.isNestedScrollingEnabled = false

        setAnimHeader()
    }


    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvDetailScan.visibility = View.VISIBLE

            }, 700)
        } catch (e: Exception) {
            e.printStackTrace()
            tvDetailScan.visibility = View.VISIBLE
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@DetailGudangActivity)

    }
}
