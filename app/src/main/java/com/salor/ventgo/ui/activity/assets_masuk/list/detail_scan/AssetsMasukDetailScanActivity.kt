package com.salor.ventgo.ui.activity.assets_masuk.list.detail_scan

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.view.View
import com.bottlerocketstudios.barcode.generation.ui.BarcodeView
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.asset_list_barang_masuk.AssetListBarangMasuk
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_assets_masuk_detail_scan.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AssetsMasukDetailScanActivity : BaseActivity() {

    lateinit var ivBarcode : BarcodeView
    var mHandler = Handler()
    lateinit var dataBarang : AssetListBarangMasuk

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_masuk_detail_scan)

        setStatusBarGradiantLogin(this)

        val str_json = intent.getStringExtra(Cons.JSON)
        dataBarang = Gson().fromJson(str_json,AssetListBarangMasuk::class.java)

        ivBarcode = findViewById(R.id.ivBarcode)
        tvTitle.text = dataBarang.itemName
        tvSku.text = dataBarang.itemSku
        tvGudang.text = dataBarang.warehouseName
        tvCode.text = dataBarang.code

        try {
            tvPno.text = dataBarang.noPo
        }catch (e : Exception){
            e.printStackTrace()
            tvPno.text = " - "
        }

        try {
            tvDescription.text = dataBarang.description
        }catch (e : Exception){
            e.printStackTrace()
            tvDescription.text = " - "
        }

        tvTanggal.text = DateTimeMasker.changeToDate(dataBarang.createdAt)
        tvWaktu.text = DateTimeMasker.changeToHour(dataBarang.createdAt)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        mHandler.removeCallbacks(mUpdateBarcodeRunnable)
        mHandler.postDelayed(mUpdateBarcodeRunnable, 500)

        setAnimHeader()

    }

    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvInfoDetail.visibility = View.VISIBLE


            }, 700)
        } catch (e: Exception) {
            e.printStackTrace()
            tvInfoDetail.visibility = View.VISIBLE
        }


    }

    private val mUpdateBarcodeRunnable = Runnable {
        ivBarcode.setBarcodeText(dataBarang.code)
        pbLoadingBarcode.visibility = View.GONE
    }

//    private val mUpdateBarcodeRunnable = Runnable {
//        pbLoadingBarcode.visibility = View.GONE
//        ivBarcode.setBarcodeText(dataBarang.code)
//    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@AssetsMasukDetailScanActivity)
    }
}
