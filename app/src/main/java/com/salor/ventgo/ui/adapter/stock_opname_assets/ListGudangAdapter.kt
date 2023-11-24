package com.salor.ventgo.ui.adapter.stock_opname_assets

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
import android.widget.ImageView
import android.widget.TextView

import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.obj.warehouse_list.WarehouseList
import com.salor.ventgo.ui.activity.stock_opname_assets.ListGudangActivity
import com.salor.ventgo.ui.activity.stock_opname_assets.riwayat.StockOpnameHistoryListActivity
import com.google.gson.Gson

class ListGudangAdapter(private val context: Activity, private val item_homes: List<WarehouseList>,var listGudangActivity : ListGudangActivity) : RecyclerView.Adapter<ListGudangAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_gudang_adapter, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]


        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.warehouseName
        holder.tvDesc1.text = item.warehouseName


        holder.cvParent.setOnClickListener {

            val intent = Intent(context, StockOpnameHistoryListActivity::class.java)
            intent.putExtra(Cons.JSON,Gson().toJson(item))
            context.startActivity(intent)
            listGudangActivity.setOveridePendingTransisi(listGudangActivity)
        }

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvDesc1: TextView
        internal var tvDesc2: TextView
        internal var ivImage: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDesc1 = itemView.findViewById(R.id.tvDesc1)
            tvDesc2 = itemView.findViewById(R.id.tvDesc2)
            ivImage = itemView.findViewById(R.id.ivImage)
            cvParent = itemView.findViewById(R.id.cvParent)

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
