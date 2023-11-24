package com.salor.ventgo.ui.adapter.stock_masuk

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
import com.salor.ventgo.obj.stock_list_barang_masuk.StockListBarangMasuk
import com.salor.ventgo.ui.activity.stock_masuk.list.StockMasukListActivity
import com.salor.ventgo.ui.activity.stock_masuk.list.detail.StockMasukDetailBarangActivity
import com.google.gson.Gson

class StockMasukListAdapter(private val context: Activity, private val item_homes: List<StockListBarangMasuk>,var listActivity : StockMasukListActivity) : RecyclerView.Adapter<StockMasukListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_masuk_list, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]

        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.itemName

        holder.tvSku.text = item.sku

        holder.tvGudang.text = item.warehouseName

        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)

        holder.ivBarcode.setBarcodeText(item.sku)

        holder.cvParent.setOnClickListener {

            val intent = Intent(context, StockMasukDetailBarangActivity::class.java)
            intent.putExtra(Cons.JSON, Gson().toJson(item))
            context.startActivity(intent)
            listActivity.setOveridePendingTransisi(listActivity)
        }

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cvParent: CardView
        internal var tvTitle: TextView
        var tvSku: TextView
        var tvGudang: TextView
        var tvDate: TextView
        var ivBarcode : BarcodeView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvSku = itemView.findViewById(R.id.tvSku)
            tvGudang = itemView.findViewById(R.id.tvGudang)
            tvDate = itemView.findViewById(R.id.tvDate)
            ivBarcode = itemView.findViewById(R.id.ivBarcode)

        }
    }



}
