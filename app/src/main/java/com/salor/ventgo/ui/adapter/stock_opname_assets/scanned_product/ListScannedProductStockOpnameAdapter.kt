package com.salor.ventgo.ui.adapter.stock_opname_assets.scanned_product

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView

import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.riwayat_stock_opname_asset.list_asset.StockOpnameListAsset
import com.salor.ventgo.ui.activity.stock_opname_assets.list_scanned_product.StockOpnameListScannedProductActivity
import com.salor.ventgo.ui.activity.stock_opname_assets.list_scanned_product.detail.StockOpnameListDetailScannedProductActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_dialog_stock_opname_update_quantity.*

class ListScannedProductStockOpnameAdapter(private val context: StockOpnameListScannedProductActivity, private val item_homes: List<StockOpnameListAsset>) : RecyclerView.Adapter<ListScannedProductStockOpnameAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_opname_list_scanned_product, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]

        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.itemName
        holder.tvQty.text = item.qty.toString()
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.lastInsert)

        holder.cvParent.setOnClickListener {

            val intent = Intent(context, StockOpnameListDetailScannedProductActivity::class.java)
            intent.putExtra(Cons.JSON, Gson().toJson(item))
            intent.putExtra(Cons.ID_STOCK_OPNAME, context.listRiwayat.id)
            intent.putExtra(Cons.STATUS_STOCK, context.listRiwayat.status)
            context.startActivityForResult(intent,Cons.REQ_DETAIL_STOCK)
            context.setOveridePendingTransisi(context)
        }

        holder.tvQty.setOnClickListener(View.OnClickListener {
         //   dialogUpdateStock()
        })

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvQty: TextView
        internal var tvDate: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvQty = itemView.findViewById(R.id.tvQty)
            cvParent = itemView.findViewById(R.id.cvParent)
            tvDate = itemView.findViewById(R.id.tvDate)

        }
    }

    fun dialogUpdateStock() {

        try {

            val pDialog = Dialog(context, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_stock_opname_update_quantity)
            pDialog!!.setCancelable(false)

            val etQty = pDialog.etQty
            val btnUpdate = pDialog.btnUpdate
            val btnCancel = pDialog.btnCancel


            btnUpdate.setOnClickListener(View.OnClickListener {

                val str_stock = etQty.text.toString()
                if (str_stock == "") {

                    etQty.requestFocus()
                    etQty.error = context.resources.getString(R.string.label_form_wajib_diisi)

                } else {

                    pDialog.dismiss()

                }
            })

            btnCancel.setOnClickListener(View.OnClickListener { pDialog.dismiss() })

            val size = Point()
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = pDialog!!.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            pDialog!!.show()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
