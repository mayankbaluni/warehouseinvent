package com.salor.ventgo.ui.activity.barang_stock

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.stock_barang_list.BarangStock
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.adapter.barang_stock.ListBarangAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_barang_stock.*
import kotlinx.android.synthetic.main.dialog_failure_custom.*
import kotlinx.android.synthetic.main.item_empty_data.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException
import java.util.*

class ListBarangActivity : BaseActivity() {

    lateinit var timer : Timer
    var str_keyword_search : String = ""
    var offset : Int = 0
    var limit : Int = 20
    var isAnim : Boolean = false
    var isNotLoad : Boolean = false
    var listData : ArrayList<BarangStock> = ArrayList()
    lateinit var listBarangAdapter : ListBarangAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_barang_stock)

        setStatusBarGradiantListSearch(this)

        timer = Timer()

        setData()

        rBack.setOnClickListener(View.OnClickListener { onBackPressed() })

        // TODO: 25/07/18 detect nested scroll to bottom
        nestedscrollview.getViewTreeObserver().addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val totalHeight = nestedscrollview.getChildAt(0).getHeight()
            val scrollY = nestedscrollview.getScrollY()
            val isBottomReached = nestedscrollview.canScrollVertically(1)

            if (!isBottomReached) {
                if (!isNotLoad) {
                    pbLoadingBottom.visibility = View.VISIBLE

                    offset += 20

                    getDataListBarang(lBottomLoading,false)

                }
            }

        })

        // TODO search barang asset
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

//                rvListBarang.visibility = View.GONE
//                lLoading.visibility = View.VISIBLE
//
//                str_keyword_search = etSearch.getText().toString()
//
//                timer.cancel()
//                timer = Timer()
//                timer.schedule(
//                        object : TimerTask() {
//                            override fun run() {
//
//                                // TODO: do what you need here (refresh list)
//                                try {
//                                    this@ListBarangActivity.runOnUiThread(Runnable {
//                                        try {
//
//                                            listData.clear()
//                                            offset = 0
//
//                                            getDataListBarang(lLoading,true)
//
//                                        } catch (e: Exception) {
//                                            e.printStackTrace()
//                                        }
//                                    })
//                                } catch (e: Exception) {
//                                    e.printStackTrace()
//                                }
//                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
//                            }
//                        },
//                        5000
//                )
            }
        })

        // TODO set ime click Search
        etSearch.setOnEditorActionListener() { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                str_keyword_search = etSearch.getText().toString()

                isNotLoad = false

                hideKeyboardwithoutPopulate(this@ListBarangActivity)

                rvListBarang.visibility = View.GONE
                listData.clear()
                offset = 0

                getDataListBarang(lLoading,true)
            }
            false
        }


        getDataListBarang(lLoading,false)
    }

    fun setData(){

        listBarangAdapter = ListBarangAdapter(this, listData,this)
        rvListBarang.setAdapter(listBarangAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvListBarang.setLayoutManager(layoutManager)
        rvListBarang.isNestedScrollingEnabled = false

    }

    private fun getDataListBarang(pbLoading : LinearLayout, isSearch : Boolean) {
        pbLoading.visibility = View.VISIBLE
        val service = ApiClient.getClient()

        val call = service.stockOpnameStock(limit,offset,str_keyword_search)

        See.logE(Cons.CALLRESPONSE, "" + call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pbLoading.visibility = View.GONE
                rvListBarang.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_barang_stock", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            val jsonDataArray = json.getJSONArray(Cons.ITEMS_DATA)

                            val listBarang = Gson().fromJson(jsonDataArray.toString(), Array<BarangStock>::class.java).toList()

                            listData.addAll(listBarang)

                            listBarangAdapter.notifyDataSetChanged()

                            if (!isSearch){
                                if (listData.isEmpty()){
                                    isNotLoad = true
                                }
                            }

                            if(!isAnim){
                                setAnimHeader()
                            }

                            if(offset == 0){

                                if (listData.isEmpty()){
                                    setVisibleEmptyData()

                                    return
                                }else{
                                    setVisibleParent()
                                    return
                                }

                            }

                        } else {
                            pbLoading.visibility = View.GONE
                            See.toast(this@ListBarangActivity, api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {

                    pbLoading.visibility = View.GONE
                    dialogFailure("list",resources.getString(R.string.label_failure_content_server_title),resources.getString(R.string.label_failure_content_server_content))

                    //   See.toast(this@ListBarangActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                pbLoading.visibility = View.GONE
                dialogFailure("list",resources.getString(R.string.label_failure_content1),resources.getString(R.string.label_failure_content2))
            }
        })

    }

    fun setAnimHeader() {

        isAnim = true
        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                ivSearch.visibility = View.VISIBLE


            }, 400)
        } catch (e: Exception) {
            e.printStackTrace()
            ivSearch.visibility = View.VISIBLE
        }


    }

    fun setVisibleParent(){

        setAnimViewVisible(lParentContent,rvListBarang,0)
        setAnimViewGone(lParentContent,lParentEmptyData,0)
    }

    fun setVisibleEmptyData(){

        rvListBarang.visibility = View.GONE
        setAnimViewVisible(lParentContent,lParentEmptyData,0)

    }

    fun dialogFailure(type : String,title : String,subTitle : String) {
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
                if (type == "list"){

                    getDataListBarang(lLoading,false)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@ListBarangActivity)
    }
}
