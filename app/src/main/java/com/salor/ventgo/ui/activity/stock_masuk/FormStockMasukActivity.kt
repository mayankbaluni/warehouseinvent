package com.salor.ventgo.ui.activity.stock_masuk

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.scan_stock_masuk.ScanStockMasuk
import com.salor.ventgo.obj.warehouse_list.WarehouseList
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.activity.stock_masuk.list.StockMasukListActivity
import kotlinx.android.synthetic.main.activity_form_stock_masuk.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class FormStockMasukActivity : BaseActivity() {

    lateinit var pLoading: Loading
    var str_description: String = ""
    var str_total_quantity: String = ""
    var selected_id_warehouse: Int = 0
    lateinit var scanStockMasuk: ScanStockMasuk
    lateinit var spinnerAdapterGudang: AdapterSpinnerGudang
    var listWarehouse: ArrayList<WarehouseList> = ArrayList()
    var selected_tipe_quantity: String = ""
    var str_pno: String = ""
    var listSpinnerTipeQuantity: ArrayList<String> = ArrayList()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_stock_masuk)

        setStatusBarGradiantLogin(this)

        pLoading = Loading(this)

        rBack.setOnClickListener { onBackPressed() }

        val str_json = intent.getStringExtra(Cons.JSON)
        scanStockMasuk = Gson().fromJson(str_json, ScanStockMasuk::class.java)

        tvTitle.text = scanStockMasuk.name
        tvSku.text = scanStockMasuk.sku

        ivBarcode.setBarcodeText(scanStockMasuk.sku)

        // TODO set spinner warehouse adapter

        listWarehouse.add(WarehouseList("1", resources.getString(R.string.label_please_wait_spinner_gudang), "", ""))

        spinnerAdapterGudang = AdapterSpinnerGudang(this@FormStockMasukActivity, listWarehouse)
        spinnerAdapterGudang.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spGudang.adapter = spinnerAdapterGudang


        try {
            // TODO set list spinner tipe quantity

            for (i in scanStockMasuk.selectQtyType.indices) {

                listSpinnerTipeQuantity.add(scanStockMasuk.selectQtyType[i].qtyType)

            }

            val spinnerAdapterQuantity = ArrayAdapter(this, R.layout.spinner_item_default, listSpinnerTipeQuantity)
            spinnerAdapterQuantity.setDropDownViewResource(R.layout.spinner_dropdown_item_default)
            spTipeQuantity.adapter = spinnerAdapterQuantity


            spTipeQuantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                    selected_tipe_quantity = listSpinnerTipeQuantity.get(i)

                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            // TODO set list spinner tipe quantity

            listSpinnerTipeQuantity.add(scanStockMasuk.qtyType)

            val spinnerAdapterQuantity = ArrayAdapter(this, R.layout.spinner_item_default, listSpinnerTipeQuantity)
            spinnerAdapterQuantity.setDropDownViewResource(R.layout.spinner_dropdown_item_default)
            spTipeQuantity.adapter = spinnerAdapterQuantity

            selected_tipe_quantity = scanStockMasuk.qtyType

            spTipeQuantity.isEnabled = false


        }



        setAnimHeader()

        getDataWarehouse()


        btnSubmit.setOnClickListener {

            str_description = etDescription.text.toString()
            str_total_quantity = etQuantity.text.toString()
            str_pno = etPno.text.toString()

            if (selected_id_warehouse == -1 || selected_id_warehouse == 0) {

                See.toast(this@FormStockMasukActivity, resources.getString(R.string.label_selected_wirehouse_first))

            } else if (selected_tipe_quantity == "") {

                See.toast(this@FormStockMasukActivity, resources.getString(R.string.label_selected_quantity_type_first))

            } else if (str_total_quantity == "") {

                etQuantity.requestFocus()
                etQuantity.error = resources.getString(R.string.label_form_wajib_diisi)

            } else {

                addStockMasuk()

            }

        }

    }

    private fun getDataWarehouse() {
        pbLoadingSpinner.visibility = View.VISIBLE
        val service = ApiClient.getClient()

        val idUser = DBS.with(this).idUser.toInt()

        val call = service.warehouseList(idUser)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_warehouse", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)

                            val listData = Gson().fromJson(jsonDataArray.toString(), Array<WarehouseList>::class.java).toList()

                            listWarehouse.clear()

                            listWarehouse.add(WarehouseList("-1", resources.getString(R.string.labe_hint_pilih_gudang), "", ""))

                            listWarehouse.addAll(listData)

                            spinnerAdapterGudang = AdapterSpinnerGudang(this@FormStockMasukActivity, listWarehouse)
                            spinnerAdapterGudang.setDropDownViewResource(R.layout.spinner_dropdown_item)
                            spGudang.adapter = spinnerAdapterGudang

                            setAnimLoadingGudang()

                            spGudang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                                    selected_id_warehouse = listWarehouse.get(i).idWarehouse.toInt()

                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>) {

                                }
                            }


                        } else {
                            pbLoadingSpinner.visibility = View.GONE
                            See.toast(this@FormStockMasukActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pbLoadingSpinner.visibility = View.GONE
                    dialogFailure("warehouse", resources.getString(R.string.label_failure_content_server_title), resources.getString(R.string.label_failure_content_server_content))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                pbLoadingSpinner.visibility = View.GONE
                dialogFailure("warehouse", resources.getString(R.string.label_failure_content1), resources.getString(R.string.label_failure_content2))
            }
        })

    }

    private fun addStockMasuk() {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()
        val call = service.stockBarangMasukAdd(scanStockMasuk.sku, scanStockMasuk.id.toInt(), selected_id_warehouse, selected_tipe_quantity, str_total_quantity, str_description, scanStockMasuk.idCmsUsers.toInt(), str_pno)

        See.logE("idUser", "" + scanStockMasuk.idCmsUsers)
        See.logE("sku", "" + scanStockMasuk.sku)
        See.logE("id_item", "" + scanStockMasuk.id.toInt())
        See.logE("selected_id_warehouse", "" + selected_id_warehouse)
        See.logE("str_description", "" + str_description)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_add_asset", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            dialogTerkirim(scanStockMasuk.sku)

                        } else {
                            Toast.makeText(this@FormStockMasukActivity, api_message, Toast.LENGTH_SHORT).show()
                            Log.e("toastlogin", api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pLoading.dismissDialog()
                    Toast.makeText(this@FormStockMasukActivity, resources.getString(R.string.label_something_wrong), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pLoading.dismissDialog()
                Toast.makeText(this@FormStockMasukActivity, resources.getString(R.string.label_koneksi_error), Toast.LENGTH_SHORT).show()
            }
        })

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

    fun setAnimLoadingGudang() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                pbLoadingSpinner.visibility = View.GONE


            }, 700)
        } catch (e: Exception) {
            e.printStackTrace()
            pbLoadingSpinner.visibility = View.GONE
        }


    }

    fun dialogFailure(type: String, title: String, subTitle: String) {
        try {

            var dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_failure_custom)
            dialog.setCancelable(false)

            val btnBack: Button = dialog.btnBack
            val btnRefresh: Button = dialog.btnRefresh

            val tv_Content: TextView = dialog.tv_Content
            val tvContent2: TextView = dialog.tvContent2

            tv_Content.text = title
            tvContent2.text = subTitle

            btnBack.setOnClickListener {
                dialog.dismiss()
                onBackPressed()
            }

            btnRefresh.setOnClickListener {
                dialog.dismiss()

                if (type == "warehouse") {
                    getDataWarehouse()
                }
            }

            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = dialog.window
            val wlp = window!!.attributes

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun dialogTerkirim(codeScan: String) {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog.setContentView(R.layout.item_dialog_stock_opname_upload_data_success)
            pDialog.setCancelable(false)

            val tvTitle: TextView = pDialog.findViewById(R.id.tvTitle)
            val tvContent: TextView = pDialog.findViewById(R.id.tvContentDialog)
            val btnOke: Button = pDialog.findViewById(R.id.btnOke)

            tvTitle.text = resources.getString(R.string.label_data_terkirim)
            tvContent.text = "Data produk code " + codeScan + " berhasil ditambahkan ke sistem "

            btnOke.setOnClickListener {
                pDialog.dismiss()
                val intent = Intent(this, StockMasukListActivity::class.java)
                startActivity(intent)
                finish()
                setOveridePendingTransisi(this@FormStockMasukActivity)

            }


            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = pDialog.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            pDialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class AdapterSpinnerGudang(internal var context: Context, internal var stringList: ArrayList<WarehouseList>) : ArrayAdapter<WarehouseList>(context, R.layout.spinner_item, stringList) {

        internal var inflater: LayoutInflater? = null
        internal var resource: Int = 0
        internal var searchText = ""
        internal var selectedPosisi = 0

        init {
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }


        fun setFilter(dataSpinnerExhibitors: List<WarehouseList>, search: String) {
            stringList = java.util.ArrayList()
            stringList.addAll(dataSpinnerExhibitors)
            this.searchText = search
            notifyDataSetChanged()
        }


        fun setSelected(posisi: Int) {
            selectedPosisi = posisi
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            var vi = convertView
            val holder: ViewHolder

            if (convertView == null)
                vi = inflater!!.inflate(R.layout.spinner_item_default, null)
            holder = ViewHolder(vi!!)
            vi.tag = holder

            val item = stringList[position]

            holder.text1.text = item.warehouseName


            return vi
        }

        private inner class ViewHolder internal constructor(view: View) {
            internal var text1: TextView = view.findViewById(R.id.text1)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this@FormStockMasukActivity, ScannerActivity::class.java)
        startActivity(intent)
        finish()
        setOveridePendingTransisi(this@FormStockMasukActivity)
    }
}
