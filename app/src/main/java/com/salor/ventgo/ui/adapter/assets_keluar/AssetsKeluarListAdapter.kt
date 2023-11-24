package com.salor.ventgo.ui.adapter.assets_keluar

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bottlerocketstudios.barcode.generation.ui.BarcodeView

import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.asset_list_barang_keluar.AssetListBarangKeluar
import com.salor.ventgo.ui.activity.assets_keluar.list.AssetsKeluarListActivity
import com.salor.ventgo.ui.activity.assets_keluar.list.detail_scan.AssetsKeluarDetailScanActivity
import com.google.gson.Gson

class AssetsKeluarListAdapter(private val context: Activity, private val item_homes: List<AssetListBarangKeluar>,var assetKeluarActivity : AssetsKeluarListActivity) : RecyclerView.Adapter<AssetsKeluarListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assets_keluar_list, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]

        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.itemName
        holder.tvSku.text = item.code
        holder.tvKode.text = item.idItemInAsset
        holder.tvGudang.text = item.warehouseName
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)

        holder.ivBarcode.setBarcodeText(item.code)

        holder.cvParent.setOnClickListener {

            val intent = Intent(context, AssetsKeluarDetailScanActivity::class.java)
            intent.putExtra(Cons.JSON,Gson().toJson(item))
            context.startActivity(intent)
            assetKeluarActivity.setOveridePendingTransisi(assetKeluarActivity)

        }

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvSku: TextView
        internal var tvKode: TextView
        internal var tvGudang: TextView
        internal var tvDate: TextView
        var ivBarcode : BarcodeView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvSku = itemView.findViewById(R.id.tvSku)
            tvKode = itemView.findViewById(R.id.tvKode)
            tvGudang = itemView.findViewById(R.id.tvGudang)
            tvDate = itemView.findViewById(R.id.tvDate)
            ivBarcode = itemView.findViewById(R.id.ivBarcode)
        }
    }



}
