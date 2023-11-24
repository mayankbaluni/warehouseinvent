package com.salor.ventgo.ui.adapter.stock_opname_stock.history

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.riwayat_stock_opname_stock.RiwayatStockOpnameList
import com.salor.ventgo.ui.activity.stock_opname_stock.list_scanned_product.StockOpnameListScannedProductActivity
import com.salor.ventgo.ui.activity.stock_opname_stock.riwayat.StockOpnameHistoryListActivity
import com.google.gson.Gson

class ListHistoryStockOpnameAdapter(private val context: Activity, private val item_homes: List<RiwayatStockOpnameList>,var listActivity : StockOpnameHistoryListActivity) : RecyclerView.Adapter<ListHistoryStockOpnameAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_riwayat_stock_opname, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]


        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.name
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)
        holder.tvStatus.text = item.status
        holder.tvQty.text = item.totalStockOpname.toString() + " " + context.resources.getString(R.string.label_scanned)

        // TODO status
        if (item.status == Cons.STATUS_DRAFT){

            holder.lStatus.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_rounded_status_draft))
        }else if (item.status == Cons.STATUS_PENDING){
            holder.lStatus.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_rounded_status_pending))
        }else{
            holder.lStatus.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_rounded_status_publish))
        }

        holder.cvParent.setOnClickListener {

            val intent  = Intent(context, StockOpnameListScannedProductActivity::class.java)
            intent.putExtra(Cons.JSON,Gson().toJson(item))
            context.startActivityForResult(intent,Cons.REQ_PUBLISH)
            listActivity.setOveridePendingTransisi(listActivity)

        }

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvDate: TextView
        internal var tvStatus: TextView
        internal var tvQty: TextView
        internal var lStatus: LinearLayout

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            lStatus = itemView.findViewById(R.id.lStatus)
            tvQty = itemView.findViewById(R.id.tvQty)

        }
    }


}
