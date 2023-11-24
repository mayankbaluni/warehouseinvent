package com.salor.ventgo.ui.activity.assets_keluar

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
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.scan_asset_keluar.ScanAssetKeluar
import com.salor.ventgo.obj.warehouse_list.WarehouseList
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.activity.assets_keluar.list.AssetsKeluarListActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_form_assets_keluar.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import kotlinx.android.synthetic.main.item_dialog_stock_opname_upload_data_success.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class FormAssetsKeluarActivity : BaseActivity() {

    lateinit var scanBarang : ScanAssetKeluar
    lateinit var pLoading : Loading
    var str_description : String = ""
    var id_item : Int = 0
    var str_code : String = ""
    var listWarehouse : ArrayList<WarehouseList> = ArrayList()
    lateinit var spinnerAdapterGudang : AdapterSpinnerGudang

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_assets_keluar)

        pLoading = Loading(this)

        setStatusBarGradiantLogin(this)

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })


        val str_json = intent.getStringExtra(Cons.JSON)
        scanBarang = Gson().fromJson(str_json,ScanAssetKeluar::class.java)

        str_code = scanBarang.code
        id_item = scanBarang.idItem.toInt()

        tvGudang.text = scanBarang.gudang
        tvCode.text = str_code
        tvName.text = scanBarang.name
        tvSku.text = scanBarang.sku

        ivBarcode.setBarcodeText(scanBarang.code)

        btnSubmit.setOnClickListener {
            str_description = etDescription.text.toString()

//            if (str_description == ""){
//
//                etDescription.requestFocus()
//                etDescription.error = resources.getString(R.string.label_form_wajib_diisi)
//
//            }else{

            addAssetKeluar()

//            }

        }

        setAnimHeader()


    }

    private fun addAssetKeluar() {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()
        val gson = Gson()

        val idUser = DBS.with(this).idUser.toInt()

        val call = service.assetBarangkeluarAdd(idUser,str_code,id_item,str_description)

        See.logE("idUser",""+idUser)
        See.logE("str_code",""+str_code)
        See.logE("id_item",""+id_item)
        See.logE("str_description",""+str_description)

        See.logE(Cons.CALLRESPONSE,""+call.request())

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

                            dialogTerkirim()

                        } else {
                            Toast.makeText(this@FormAssetsKeluarActivity, api_message, Toast.LENGTH_SHORT).show()
                            Log.e("toastlogin", api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pLoading.dismissDialog()
                    Toast.makeText(this@FormAssetsKeluarActivity, resources.getString(R.string.label_something_wrong), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pLoading.dismissDialog()
                Toast.makeText(this@FormAssetsKeluarActivity, resources.getString(R.string.label_koneksi_error), Toast.LENGTH_SHORT).show()
            }
        })

    }


    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvInfoDetail.visibility = View.VISIBLE


            }, 400)
        } catch (e: Exception) {
            e.printStackTrace()
            tvInfoDetail.visibility = View.VISIBLE
        }


    }


    fun dialogFailure(type : String) {
        try {

            var dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_failure_custom)
            dialog.setCancelable(false)

            val btnBack: Button = dialog.btnBack
            val btnRefresh: Button = dialog.btnRefresh

            btnBack.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                onBackPressed()
            })

            btnRefresh.setOnClickListener(View.OnClickListener {
                dialog.dismiss()

                if (type == "warehouse") {

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


    fun dialogTerkirim() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_stock_opname_upload_data_success)
            pDialog!!.setCancelable(false)

            val tvTitle : TextView = pDialog.findViewById(R.id.tvTitle)
            val tvContentDialog = pDialog.tvContentDialog
            val btnOke = pDialog.btnOke

            tvTitle.text = resources.getString(R.string.label_data_terkirim)
            tvContentDialog.text = "Data produk code "+ str_code +" berhasil ditambahkan ke sistem "

            btnOke.setOnClickListener(View.OnClickListener {

                pDialog.dismiss()
                val intent = Intent(this, AssetsKeluarListActivity::class.java)
                startActivity(intent)
                finish()
                setOveridePendingTransisi(this@FormAssetsKeluarActivity)

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


//            if (selectedPosisi == position) {
//                //                holder.text1.setTextColor(context.getResources().getColor(R.color.color_text_blue));
//                holder.text1.setTextColor(context.resources.getColor(R.color.color_tv_blue_btn_login))
//            } else {
//                holder.text1.setTextColor(context.resources.getColor(R.color.color_tv_blue_btn_login))
//            }


            return vi
        }

        private inner class ViewHolder internal constructor(view: View) {
            internal var text1: TextView

            init {
                text1 = view.findViewById(R.id.text1)

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        val intent = Intent(this@FormAssetsKeluarActivity,ScannerActivity::class.java)
        startActivity(intent)
        finish()
        setOveridePendingTransisi(this@FormAssetsKeluarActivity)
    }
}


//    private fun getDataWarehouse() {
//        pbLoadingSpinner.visibility = View.VISIBLE
//        val service = ApiClient.getClient()
//
//        val idUser = DBS.with(this).idUser.toInt()
//
//        val call = service.warehouseList(idUser)
//
//        See.logE(Cons.CALLRESPONSE, "" + call.request())
//
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                pbLoadingSpinner.visibility = View.GONE
//                if (response.isSuccessful) {
//                    try {
//                        val respon = response.body()!!.string()
//                        val json = JSONObject(respon)
//
//                        See.logE("respon_warehouse", respon)
//
//                        val api_status = json.getInt(Cons.API_STATUS)
//                        val api_message = json.getString(Cons.API_MESSAGE)
//
//                        if (api_status == Cons.INT_STATUS) {
//
//                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)
//
//                            val listData = Gson().fromJson(jsonDataArray.toString(), Array<WarehouseList>::class.java).toList()
//
//                            listWarehouse.clear()
//
//                            listWarehouse.add(WarehouseList("-1",resources.getString(R.string.labe_hint_pilih_gudang),"",""))
//
//                            listWarehouse.addAll(listData)
//
//                            spinnerAdapterGudang = AdapterSpinnerGudang(this@FormAssetsKeluarActivity, listWarehouse)
//                            spinnerAdapterGudang.setDropDownViewResource(R.layout.spinner_dropdown_item)
//                            spGudang.setAdapter(spinnerAdapterGudang)
//
//
//                            spGudang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//
//                                    selected_id_warehouse = listWarehouse.get(i).idWarehouse.toInt()
//
//                                }
//
//                                override fun onNothingSelected(adapterView: AdapterView<*>) {
//
//                                }
//                            }
//
//
//                        } else {
//                            See.toast(this@FormAssetsKeluarActivity, api_message)
//                        }
//
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//
//                } else {
//
//                    pbLoadingSpinner.visibility = View.GONE
//                    See.toast(this@FormAssetsKeluarActivity, resources.getString(R.string.label_something_wrong))
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//
//                pbLoadingSpinner.visibility = View.GONE
//                dialogFailure("warehouse")
//            }
//        })
//
//    }

