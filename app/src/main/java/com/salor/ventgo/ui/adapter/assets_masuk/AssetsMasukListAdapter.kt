package com.salor.ventgo.ui.adapter.assets_masuk

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bottlerocketstudios.barcode.generation.ui.BarcodeView

import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.asset_list_barang_masuk.AssetListBarangMasuk
import com.salor.ventgo.ui.activity.assets_masuk.list.AssetsMasukListActivity
import com.salor.ventgo.ui.activity.assets_masuk.list.detail_scan.AssetsMasukDetailScanActivity
import com.google.gson.Gson

class AssetsMasukListAdapter(private val context: Activity, private val item_homes: List<AssetListBarangMasuk>,var assetListActivity : AssetsMasukListActivity) : RecyclerView.Adapter<AssetsMasukListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assets_masuk_list, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]

        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.itemName
        holder.tvSku.text = item.code
        holder.tvKode.text = item.id
        holder.tvGudang.text = item.warehouseName
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)



        holder.ivBarcode.setBarcodeText(item.code)

//        try {
//            Handler().postDelayed({
//
//                TransitionManager.beginDelayedTransition(holder.lParentContent)
//                holder.tvSku.visibility = View.VISIBLE
//
//
//            }, 700)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            holder.tvSku.visibility = View.VISIBLE
//        }


        holder.cvParent.setOnClickListener {

            val intent = Intent(context, AssetsMasukDetailScanActivity::class.java)
            intent.putExtra(Cons.JSON, Gson().toJson(item))
            context.startActivity(intent)
            assetListActivity.setOveridePendingTransisi(assetListActivity)

        }

    }

    private val mUpdateBarcodeRunnable = Runnable {
//        mBarcodeImage.setBarcodeText(mBarcodeText.getText().toString())
    }

//    fun setBarcodeText(str_code : String,ivBarcodeView: BarcodeView){
//
//
//        class OneShotTask : Runnable {
//            override fun run() {
//                ivBarcodeView.setBarcodeText(str_code)
//            }
//        }
//
//        val t = Thread(OneShotTask())
//        t.start()
//
//    }
//    private val mUpdateBarcodeRunnable = Runnable { }


    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivBarcode: BarcodeView
        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvSku: TextView
        internal var tvKode: TextView
        internal var tvGudang: TextView
        internal var tvDate: TextView
        internal var lParentContent: LinearLayout

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvSku = itemView.findViewById(R.id.tvSku)
            tvKode = itemView.findViewById(R.id.tvKode)
            tvGudang = itemView.findViewById(R.id.tvGudang)
            tvDate = itemView.findViewById(R.id.tvDate)
            ivBarcode = itemView.findViewById(R.id.ivBarcode)
            lParentContent = itemView.findViewById(R.id.lParentContent)

        }
    }


}
