package com.salor.ventgo.ui.activity.stock_masuk.list.detail

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.view.View
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.stock_list_barang_masuk.StockListBarangMasuk
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_stock_masuk_detail_barang.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class StockMasukDetailBarangActivity : BaseActivity() {

    lateinit var stockBarangMasuk : StockListBarangMasuk

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_masuk_detail_barang)

        setStatusBarGradiantLogin(this)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        val str_json = intent.getStringExtra(Cons.JSON)
        stockBarangMasuk = Gson().fromJson(str_json,StockListBarangMasuk::class.java)

        tvTitle.text = stockBarangMasuk.itemName
        tvSku.text = stockBarangMasuk.sku
        tvNameWarehouse.text = stockBarangMasuk.warehouseName
        tvTypeQuantity.text = stockBarangMasuk.qtyType
        tvQuantity.text = stockBarangMasuk.qty
        tvDescription.text = stockBarangMasuk.description
        tvTanggal.text = DateTimeMasker.changeToDate(stockBarangMasuk.createdAt)
        tvJam.text = DateTimeMasker.changeToHour(stockBarangMasuk.createdAt)

        try {
            tvPno.text = stockBarangMasuk.noPo
        }catch (e : Exception){
            e.printStackTrace()
            tvPno.text = " - "
        }

        ivBarcode.setBarcodeText(stockBarangMasuk.sku)

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
        setOveridePendingTransisi(this@StockMasukDetailBarangActivity)
    }
}
