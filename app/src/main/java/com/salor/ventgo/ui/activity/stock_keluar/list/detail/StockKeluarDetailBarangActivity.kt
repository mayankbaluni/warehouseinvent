package com.salor.ventgo.ui.activity.stock_keluar.list.detail

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.view.View
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.stock_list_barang_keluar.StockListBarangKeluar
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_stock_keluar_detail_barang.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class StockKeluarDetailBarangActivity : BaseActivity() {

    lateinit var stockBarangKeluar : StockListBarangKeluar

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_keluar_detail_barang)

        setStatusBarGradiantLogin(this)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        val str_json = intent.getStringExtra(Cons.JSON)
        stockBarangKeluar = Gson().fromJson(str_json,StockListBarangKeluar::class.java)

        tvTitle.text = stockBarangKeluar.itemName
        tvSku.text = stockBarangKeluar.sku
        tvNameWarehouse.text = stockBarangKeluar.warehouseName
        typeQuantity.text = stockBarangKeluar.qtyType
        tvQuantity.text = stockBarangKeluar.qty
        tvDescription.text = stockBarangKeluar.description
        tvTanggal.text = DateTimeMasker.changeToDate(stockBarangKeluar.createdAt)
        tvJam.text = DateTimeMasker.changeToHour(stockBarangKeluar.createdAt)

        ivBarcode.setBarcodeText(stockBarangKeluar.sku)

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
        setOveridePendingTransisi(this@StockKeluarDetailBarangActivity)
    }
}
