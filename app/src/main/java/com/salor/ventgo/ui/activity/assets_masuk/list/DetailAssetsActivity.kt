package com.salor.ventgo.ui.activity.assets_masuk.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.salor.ventgo.R
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.adapter.assets_masuk.detail_assets.AssetsMasukListScanAdapter
import kotlinx.android.synthetic.main.activity_assets_keluar_detail_assets.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DetailAssetsActivity : BaseActivity() {

    lateinit var assetsKeluarListScanAdapter: AssetsMasukListScanAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_keluar_detail_assets)

        setStatusBarGradiantListSearch(this)

        setData()

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        setAnimHeader()
    }

    fun setData(){

        val listData : ArrayList<String> = ArrayList()

        listData.add("TY507041645R2696B")
        listData.add("TY507041645R2692C")
        listData.add("TY507041645R2666B")
        listData.add("TY507041645R2666B")


        assetsKeluarListScanAdapter = AssetsMasukListScanAdapter(this, listData)
        rvListGudang.setAdapter(assetsKeluarListScanAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvListGudang.setLayoutManager(layoutManager)
        rvListGudang.isNestedScrollingEnabled = false

    }


    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvDetailScan.visibility = View.VISIBLE

            }, 400)
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
    }
}
