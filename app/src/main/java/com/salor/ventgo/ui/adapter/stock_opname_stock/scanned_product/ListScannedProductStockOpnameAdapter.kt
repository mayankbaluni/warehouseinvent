package com.salor.ventgo.ui.adapter.stock_opname_stock.scanned_product

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.bottlerocketstudios.barcode.generation.ui.BarcodeView

import com.salor.ventgo.R
import com.salor.ventgo.helper.date.DateTimeMasker
import com.salor.ventgo.obj.riwayat_stock_opname_stock.list_stock.StockOpnameListStock
import com.salor.ventgo.ui.activity.stock_opname_stock.list_scanned_product.StockOpnameListScannedProductActivity
import kotlinx.android.synthetic.main.item_dialog_stock_opname_update_quantity.*

class ListScannedProductStockOpnameAdapter(private val context: Activity, private val item_homes: List<StockOpnameListStock>, var stockOpnameActivity: StockOpnameListScannedProductActivity) : RecyclerView.Adapter<ListScannedProductStockOpnameAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_opname_list_scanned_product_stock, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]


        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.itemName
        holder.tvSku.text = item.itemSku
        holder.tvQty.text = item.qty
        holder.tvDate.text = DateTimeMasker.changeToNameMonthCustom(item.createdAt)

        holder.ivBarcode.setBarcodeText(item.itemSku)

        holder.cvParent.setOnClickListener {

            //            val intent = Intent(context, StockOpnameListDetailScannedProductActivity::class.java)
//            context.startActivity(intent)

        }

        holder.btnUpdate.setOnClickListener(View.OnClickListener {
            dialogUpdateStock(item.id)
        })

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var btnUpdate: Button
        internal var cvParent: CardView
        internal var tvTitle: TextView
        internal var tvQty: TextView
        internal var tvSku: TextView
        internal var tvDate: TextView
        var ivBarcode : BarcodeView

        init {

            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvQty = itemView.findViewById(R.id.tvQty)
            cvParent = itemView.findViewById(R.id.cvParent)
            btnUpdate = itemView.findViewById(R.id.btnUpdate)
            tvSku = itemView.findViewById(R.id.tvSku)
            tvDate = itemView.findViewById(R.id.tvDate)
            ivBarcode = itemView.findViewById(R.id.ivBarcode)

        }
    }

    fun dialogUpdateStock(idStock: String) {

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
                    stockOpnameActivity.updateStockOpname(str_stock, idStock)

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
