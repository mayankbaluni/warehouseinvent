package com.salor.ventgo.ui.activity.stock_opname_stock.riwayat

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.riwayat_stock_opname_stock.RiwayatStockOpnameList
import com.salor.ventgo.obj.warehouse_list.WarehouseList
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.adapter.stock_opname_stock.history.ListHistoryStockOpnameAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat_list_stock_opname.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import kotlinx.android.synthetic.main.item_dialog_tambah_stock_opname.*
import kotlinx.android.synthetic.main.item_empty_data.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class StockOpnameHistoryListActivity : BaseActivity() {

    var str_keyword_search: String = ""
    var isNotLoad: Boolean = false
    var str_name_add_stockopname: String = ""
    lateinit var pLoading: Loading
    var id_warehouse: Int = 0
    lateinit var dataWarehouse: WarehouseList
    var listHistoryOpnameList: ArrayList<RiwayatStockOpnameList> = ArrayList()
    var str_selected_status: String = ""
    var listSpinnerStatus: ArrayList<String> = ArrayList()
    lateinit var spinnerAdapter: AdapterSpinnerStatus
    lateinit var listHistoryStockOpnameAdapter: ListHistoryStockOpnameAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_list_stock_opname)

        setStatusBarGradiantListSearch(this)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        pLoading = Loading(this)

        val str_json = intent.getStringExtra(Cons.JSON)
        dataWarehouse = Gson().fromJson(str_json, WarehouseList::class.java)

        id_warehouse = dataWarehouse.idWarehouse.toInt()

        listSpinnerStatus.add(resources.getString(R.string.label_semua_status))
        listSpinnerStatus.add(resources.getString(R.string.label_draft_status))
        listSpinnerStatus.add(resources.getString(R.string.label_pending_status))
        listSpinnerStatus.add(resources.getString(R.string.label_publish_status))

        spinnerAdapter = AdapterSpinnerStatus(this, listSpinnerStatus)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spStatus.setAdapter(spinnerAdapter)


        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                str_selected_status = listSpinnerStatus.get(i)

                str_keyword_search = etSearch.getText().toString()

                isNotLoad = false

                hideKeyboardwithoutPopulate(this@StockOpnameHistoryListActivity)

                rvListBarang.visibility = View.GONE

                getDataList(lLoading)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        // TODO set ime click Search
        etSearch.setOnEditorActionListener() { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                str_keyword_search = etSearch.getText().toString()

                isNotLoad = false

                hideKeyboardwithoutPopulate(this@StockOpnameHistoryListActivity)

                rvListBarang.visibility = View.GONE

                getDataList(lLoading)
            }
            false
        }

        setData()

        getDataList(lLoading)

        lAddStock.setOnClickListener(View.OnClickListener {
            dialogAddStock()
        })

    }

    private fun getDataList(pbLoading: LinearLayout) {
        rvListBarang.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
        val service = ApiClient.getClient()

        var status: String = ""
        if (str_selected_status == resources.getString(R.string.label_semua_status)) {

            status = ""

        } else if (str_selected_status == resources.getString(R.string.label_draft_status)) {

            status = Cons.DRAFT_STATUS
        } else if (str_selected_status == resources.getString(R.string.label_publish_status)) {

            status = Cons.PUBLISH_STATUS
        }else if (str_selected_status == resources.getString(R.string.label_pending_status)) {

            status = Cons.STATUS_PENDING
        }

        val call = service.stockOpnameStockList(id_warehouse, str_keyword_search, status)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pbLoading.visibility = View.GONE
                rvListBarang.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_stockopname", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            listHistoryOpnameList.clear()

                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)

                            val listData = Gson().fromJson(jsonDataArray.toString(), Array<RiwayatStockOpnameList>::class.java).toList()

                            listHistoryOpnameList.addAll(listData)

                            listHistoryStockOpnameAdapter.notifyDataSetChanged()

                            setAnimHeader()

                            if (listData.isEmpty()) {
                                setVisibleEmptyData()

                                setAnimViewVisible(lParentContent,lAddStock,700)

                                return
                            } else {
                                setVisibleParent()
                                lAddStock.visibility = View.VISIBLE
                                return
                            }



                        } else {
                            pbLoading.visibility = View.GONE
                            See.toast(this@StockOpnameHistoryListActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    rvListBarang.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE

                    dialogFailure("list",resources.getString(R.string.label_failure_content_server_title),resources.getString(R.string.label_failure_content_server_content))


                    //   See.toast(this@StockOpnameHistoryListActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                pbLoading.visibility = View.GONE
                dialogFailure("list",resources.getString(R.string.label_failure_content1),resources.getString(R.string.label_failure_content2))

            }
        })

    }

    fun setVisibleParent() {

        setAnimViewVisible(lParentContent, rvListBarang, 0)
        setAnimViewGone(lParentContent, lParentEmptyData, 0)
    }

    fun setVisibleEmptyData() {

        rvListBarang.visibility = View.GONE
        setAnimViewVisible(lParentContent, lParentEmptyData, 0)

    }

    fun setData() {

        listHistoryStockOpnameAdapter = ListHistoryStockOpnameAdapter(this, listHistoryOpnameList, this)
        rvListBarang.setAdapter(listHistoryStockOpnameAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvListBarang.setLayoutManager(layoutManager)
        rvListBarang.isNestedScrollingEnabled = false

    }

    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                appSpinner.visibility = View.VISIBLE


            }, 700)
        } catch (e: Exception) {
            e.printStackTrace()
            appSpinner.visibility = View.VISIBLE
        }


    }

    private fun sendAddDataStockOpname() {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)

        val service = ApiClient.getClient()

        val idUser = DBS.with(this@StockOpnameHistoryListActivity).idUser.toInt()
        val idWarehouse = dataWarehouse.idWarehouse.toInt()

        val call = service.stockOpnameStockAdd(str_name_add_stockopname, idUser, idWarehouse)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_add_stockopname", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            getDataList(lLoading)

                        } else {
                            pLoading.dismissDialog()
                            See.toast(this@StockOpnameHistoryListActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    pLoading.dismissDialog()
                    dialogFailure("send",resources.getString(R.string.label_failure_content_server_title),resources.getString(R.string.label_failure_content_server_content))

                    //             See.toast(this@StockOpnameHistoryListActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                pLoading.dismissDialog()
                dialogFailure("send",resources.getString(R.string.label_failure_content1),resources.getString(R.string.label_failure_content2))
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Cons.REQ_PUBLISH && resultCode == Cons.RES_PUBLISH) {

            getDataList(lLoading)

        }
    }

    fun dialogFailure(type: String,title : String,subTitle : String) {
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


            btnBack.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                onBackPressed()
            })

            btnRefresh.setOnClickListener(View.OnClickListener {
                dialog.dismiss()

                if (type == "list") {

                    getDataList(lLoading)
                } else {
                    sendAddDataStockOpname()
                }
            })

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


    inner class AdapterSpinnerStatus(internal var context: Context, internal var stringList: MutableList<String>) : ArrayAdapter<String>(context, R.layout.spinner_item, stringList) {

        internal var inflater: LayoutInflater? = null
        internal var resource: Int = 0
        internal var searchText = ""
        internal var selectedPosisi = 0

        init {
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }


        fun setFilter(dataSpinnerExhibitors: List<String>, search: String) {
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
                vi = inflater!!.inflate(R.layout.spinner_item, null)
            holder = ViewHolder(vi!!)
            vi.tag = holder

            val item = stringList[position]

            holder.text1.text = item


            if (selectedPosisi == position) {
                //                holder.text1.setTextColor(context.getResources().getColor(R.color.color_text_blue));
                holder.text1.setTextColor(context.resources.getColor(R.color.color_tv_blue_btn_login))
            } else {
                holder.text1.setTextColor(context.resources.getColor(R.color.color_tv_blue_btn_login))
            }


            return vi
        }

        private inner class ViewHolder internal constructor(view: View) {
            internal var text1: TextView

            init {
                text1 = view.findViewById(R.id.text1)

            }
        }
    }

    fun dialogAddStock() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_tambah_stock_opname)
            pDialog!!.setCancelable(true)

            val etStock = pDialog.etStock
            val btnTambah = pDialog.btnTambah


            btnTambah.setOnClickListener(View.OnClickListener {

                str_name_add_stockopname = etStock.text.toString()
                if (str_name_add_stockopname == "") {

                    etStock.requestFocus()
                    etStock.error = resources.getString(R.string.label_form_wajib_diisi)
                } else {

                    pDialog.dismiss()
                    sendAddDataStockOpname()
                }
            })

            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@StockOpnameHistoryListActivity)
    }
}
