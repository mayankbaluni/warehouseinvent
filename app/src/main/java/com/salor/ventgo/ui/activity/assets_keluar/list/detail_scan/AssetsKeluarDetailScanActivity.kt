package com.salor.ventgo.ui.activity.assets_keluar.list.detail_scan

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.view.View
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.asset_list_barang_keluar.AssetListBarangKeluar
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_assets_keluar_detail_scan.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AssetsKeluarDetailScanActivity : BaseActivity() {

    lateinit var dataBarang : AssetListBarangKeluar

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_keluar_detail_scan)

        setStatusBarGradiantLogin(this)

        val str_json = intent.getStringExtra(Cons.JSON)

        dataBarang = Gson().fromJson(str_json,AssetListBarangKeluar::class.java)

        tvTitle.text = dataBarang.itemName
        tvSku.text = dataBarang.itemSku
        tvGudang.text = dataBarang.warehouseName
        tvCode.text = dataBarang.code

        ivBarcode.setBarcodeText(dataBarang.code)

        try {

            tvDescription.text = dataBarang.description
        }catch (e : Exception){
            e.printStackTrace()
            tvDescription.text = " - "
        }

        tvTanggal.text = DateTimeMasker.changeToDate(dataBarang.createdAt)
        tvHour.text = DateTimeMasker.changeToHour(dataBarang.createdAt)


        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@AssetsKeluarDetailScanActivity)
    }
}
