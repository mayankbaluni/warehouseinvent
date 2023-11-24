package com.salor.ventgo.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.salor.ventgo.R
import com.salor.ventgo.db.DBS
import com.salor.ventgo.db.dao_user.Database
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.LoadImage
import com.salor.ventgo.obj.DataHome
import com.salor.ventgo.obj.login.DataUser
import com.salor.ventgo.ui.activity.profile.ProfileActivity
import com.salor.ventgo.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_top_home.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class HomeActivity : BaseActivity() {

    lateinit var dataUser : DataUser
    lateinit var userDatabase: Database
    var isAssets: Boolean = true
    lateinit var loadImage: LoadImage
    var listDataHome: ArrayList<DataHome> = ArrayList()
    lateinit var homeAdapter: HomeAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        try {
            userDatabase = Room.databaseBuilder(applicationContext, Database::class.java, Cons.DB_NAME_LOGIN_USER).fallbackToDestructiveMigration().build()
        }catch (e : Exception){
            e.printStackTrace()
        }

        setStatusBarGradiantLogin(this)

        loadImage = LoadImage(this)

//        rvList.visibility = View.GONE

        setData()

        tvTitleToolbar.setOnClickListener(View.OnClickListener {

            dialogSelectProduct()

        })

        lProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
            setOveridePendingTransisi(this@HomeActivity)

        })


    }

    override fun onResume() {
        super.onResume()
        try {
            fetchTodoById(Cons.INT_ID)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun setData() {
      //  rvList.visibility = View.GONE
        //setAnimViewGone(lParentContent,rvList)

        listDataHome.clear()

        listDataHome.add(DataHome(1, resources.getString(R.string.label_menu_title_menu_asset_masuk), resources.getString(R.string.label_menu_desc_1_menu_asset_masuk), resources.getString(R.string.label_menu_desc_2_menu_asset_masuk), R.drawable.ic_barang_masuk))
        listDataHome.add(DataHome(2, resources.getString(R.string.label_menu_title_menu_asset_keluar), resources.getString(R.string.label_menu_desc_1_menu_asset_keluar), resources.getString(R.string.label_menu_desc_2_menu_asset_keluar), R.drawable.ic_barang_keluar))
        listDataHome.add(DataHome(3, resources.getString(R.string.label_menu_title_menu_stock_opname), resources.getString(R.string.label_menu_desc_1_menu_stock_opname), resources.getString(R.string.label_menu_desc_2_menu_stock_opname), R.drawable.ic_stock_opname))
        listDataHome.add(DataHome(4, resources.getString(R.string.label_menu_title_menu_barang_assets), resources.getString(R.string.label_menu_desc_1_menu_barang_assets), resources.getString(R.string.label_menu_desc_2_menu_barang_assets), R.drawable.ic_product_assets))

        homeAdapter = HomeAdapter(this, listDataHome, this)
        rvList.setAdapter(homeAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvList.setLayoutManager(layoutManager)
        rvList.isNestedScrollingEnabled = false

     //   setAnimViewVisible(lParentContent,rvList,0)

    }

    fun setDataStock() {

   //     rvList.visibility = View.GONE
  //      setAnimViewGone(lParentContent,rvList)

        listDataHome.clear()

        listDataHome.add(DataHome(1, resources.getString(R.string.label_menu_title_menu_stock_masuk), resources.getString(R.string.label_menu_desc_1_menu_asset_masuk), resources.getString(R.string.label_menu_desc_2_menu_asset_masuk), R.drawable.ic_barang_masuk))
        listDataHome.add(DataHome(2, resources.getString(R.string.label_menu_title_menu_stock_keluar), resources.getString(R.string.label_menu_desc_1_menu_asset_keluar), resources.getString(R.string.label_menu_desc_2_menu_asset_keluar), R.drawable.ic_barang_keluar))
        listDataHome.add(DataHome(3, resources.getString(R.string.label_menu_title_menu_stock_opname), resources.getString(R.string.label_menu_desc_1_menu_stock_opname), resources.getString(R.string.label_menu_desc_2_menu_stock_opname), R.drawable.ic_stock_opname))
        listDataHome.add(DataHome(4, resources.getString(R.string.label_menu_title_menu_barang_stock), resources.getString(R.string.label_menu_desc_1_menu_barang_assets), resources.getString(R.string.label_menu_desc_2_menu_barang_assets), R.drawable.ic_product_assets))

        homeAdapter = HomeAdapter(this, listDataHome, this)
        rvList.setAdapter(homeAdapter)
        val layoutManager = LinearLayoutManager(this)
        rvList.setLayoutManager(layoutManager)
        rvList.isNestedScrollingEnabled = false

     //   setAnimViewVisible(lParentContent,rvList,0)


    }

    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent)
                tvEmail.visibility = View.VISIBLE

            }, 700)
        } catch (e: Exception) {
            e.printStackTrace()
            tvEmail.visibility = View.VISIBLE
        }


    }


//    fun setAnimArrow() {
//
//        try {
//            Handler().postDelayed({
//
//                TransitionManager.beginDelayedTransition(lParentContent)
//                ivImageArrow.visibility = View.VISIBLE
//
//            }, 700)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ivImageArrow.visibility = View.VISIBLE
//        }
//
//
//    }

//    fun setAnimList() {
//
//        try {
//            Handler().postDelayed({
//
//                TransitionManager.beginDelayedTransition(lParentContent)
//                rvList.visibility = View.VISIBLE
//
//
//            }, 150)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            rvList.visibility = View.VISIBLE
//        }
//
//
//    }


    fun dialogSelectProduct() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.dialog_top_home)
            pDialog!!.setCancelable(true)

            val lAssets = pDialog.lAssets
            val lStock = pDialog.lStock
            val ivCheckAssets = pDialog.ivCheckAssets
            val ivCheckStock = pDialog.ivCheckStock
            val tvStock = pDialog.tvStock
            val tvAssets = pDialog.tvAssets
            val tvTitleToolbarDialog = pDialog.tvTitleToolbarDialog

            if (isAssets) {

                tvAssets.setTextColor(resources.getColor(R.color.color_tv_blue_btn_login))
                tvStock.setTextColor(resources.getColor(R.color.color_gray_tv_name_profil))

                ivCheckAssets.visibility = View.VISIBLE
                ivCheckStock.visibility = View.GONE

                tvTitleToolbarDialog.text = resources.getString(R.string.label_assets)

            } else {

                tvAssets.setTextColor(resources.getColor(R.color.color_gray_tv_name_profil))
                tvStock.setTextColor(resources.getColor(R.color.color_tv_blue_btn_login))

                ivCheckAssets.visibility = View.GONE
                ivCheckStock.visibility = View.VISIBLE

                tvTitleToolbarDialog.text = resources.getString(R.string.label_stock)
            }


            lStock.setOnClickListener(View.OnClickListener {
                pDialog.dismiss()
                isAssets = false

                setDataStock()

                tvTitleToolbar.text = resources.getString(R.string.label_stock)
            })

            lAssets.setOnClickListener(View.OnClickListener {
                pDialog.dismiss()
                isAssets = true

                setData()

                tvTitleToolbar.text = resources.getString(R.string.label_assets)
            })

            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = pDialog!!.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.TOP
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            pDialog!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun fetchTodoById(todo_id: Int) {

        try {
            object : AsyncTask<Int, Void, DataUser>() {
                override fun doInBackground(vararg p0: Int?): DataUser? {

                    return userDatabase.daoAccess().fetchUserListById(p0[0]!!)

                }

                override fun onPostExecute(user: DataUser) {
                    super.onPostExecute(user)
                    dataUser = user

                    loadImage.LoadImagePicassoMkLoader(DBS.with(this@HomeActivity).dataImageProfile,ivPhotoProfile,pbLoadingProfile)

                    tvName.text = dataUser.name
                    tvEmail.text = dataUser.email

                    setAnimHeader()
                    //setAnimArrow()

                }
            }.execute(todo_id)
        }catch (e : Exception){
            e.printStackTrace()
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
