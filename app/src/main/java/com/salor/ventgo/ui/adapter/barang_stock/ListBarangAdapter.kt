package com.salor.ventgo.ui.adapter.barang_stock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.support.v7.app.AlertDialog
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
import com.salor.ventgo.obj.stock_barang_list.BarangStock
import com.salor.ventgo.ui.activity.barang_stock.ListBarangActivity
import com.salor.ventgo.ui.activity.barang_stock.detail_stock.DetailBarangStockActivity
import com.google.gson.Gson

class ListBarangAdapter(private val context: Activity, private val item_homes: List<BarangStock>,var listActivity : ListBarangActivity) : RecyclerView.Adapter<ListBarangAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_barang_stock_adapter, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]

        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.name
        holder.tvSku.text = item.sku
        holder.tvQty.text = item.stock.toString()
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)

        holder.ivBarcode.setBarcodeText(item.sku)

        holder.cvParent.setOnClickListener {

            val intent = Intent(context, DetailBarangStockActivity::class.java)
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
        internal var tvQty: TextView
        internal var tvDate: TextView
        internal var tvSku: TextView
        var ivBarcode : BarcodeView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvQty = itemView.findViewById(R.id.tvQty)
            tvDate = itemView.findViewById(R.id.tvDate)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvSku = itemView.findViewById(R.id.tvSku)
            ivBarcode = itemView.findViewById(R.id.ivBarcode)

        }
    }

    fun gpsState(): Boolean {
        val isGPSon: Boolean
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val statusgps1 = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val statusgps2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!statusgps1 && !statusgps2) {
            isGPSon = false
        } else {
            isGPSon = true
        }
        return isGPSon
    }

    fun showDialogEnableGPS() {
        val builder = AlertDialog.Builder(
                context)
        builder.setTitle("Notification")
        builder.setMessage("Make sure GPS is active")

        builder.setPositiveButton("OK"
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
            dialog.dismiss()
        }
        builder.show()
    }


}
