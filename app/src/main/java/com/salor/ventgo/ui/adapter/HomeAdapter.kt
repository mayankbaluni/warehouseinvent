package com.salor.ventgo.ui.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.support.transition.TransitionManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.salor.ventgo.R
import com.salor.ventgo.obj.DataHome
import com.salor.ventgo.ui.activity.HomeActivity
import com.salor.ventgo.ui.activity.assets_keluar.ScannerActivity
import com.salor.ventgo.ui.activity.barang_assets.ListBarangActivity
import com.salor.ventgo.ui.activity.stock_opname_assets.ListGudangActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class HomeAdapter(private val context: Activity, private val item_homes: List<DataHome>, internal var homeActivity: HomeActivity) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_adapter, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        val item = item_homes[position]


        // TODO: 23/02/18 setDataList

        holder.tvTitle.text = item.title
        holder.tvDesc1.text = item.description1
        holder.tvDesc2.text = item.description2

        holder.ivImage.setImageResource(item.image)


        holder.cvParent.setOnClickListener {

            if (homeActivity.isAssets){

                if(position == 0){

                    if (Build.VERSION.SDK_INT >= 23) {
                        val permissionlistener = object : PermissionListener {
                            override fun onPermissionGranted() {

                                try {
                                    val intent = Intent(context, com.salor.ventgo.ui.activity.assets_masuk.ScannerActivity::class.java)
                                    context.startActivity(intent)
                                    homeActivity.setOveridePendingTransisi(homeActivity)
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }

                            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                                Toast.makeText(context, context.resources.getString(R.string.label_permission_diperlukan), Toast.LENGTH_SHORT).show()
                            }
                        }

                        TedPermission(context)
                                .setPermissionListener(permissionlistener)
                                .setRationaleMessage(context.resources.getString(R.string.label_rational_message))
                                .setDeniedMessage(context.resources.getString(R.string.label_denied_message))
                                .setPermissions(Manifest.permission.CAMERA)

                                .check()


                    } else {

                        try {
                            val intent = Intent(context, com.salor.ventgo.ui.activity.assets_masuk.ScannerActivity::class.java)
                            context.startActivity(intent)
                            homeActivity.setOveridePendingTransisi(homeActivity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }


                }else if(position == 1){

                    if (Build.VERSION.SDK_INT >= 23) {
                        val permissionlistener = object : PermissionListener {
                            override fun onPermissionGranted() {

                                try {
                                    val intent = Intent(context, ScannerActivity::class.java)
                                    context.startActivity(intent)
                                    homeActivity.setOveridePendingTransisi(homeActivity)
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }

                            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                                Toast.makeText(context, context.resources.getString(R.string.label_permission_diperlukan), Toast.LENGTH_SHORT).show()
                            }
                        }

                        TedPermission(context)
                                .setPermissionListener(permissionlistener)
                                .setRationaleMessage(context.resources.getString(R.string.label_rational_message))
                                .setDeniedMessage(context.resources.getString(R.string.label_denied_message))
                                .setPermissions(Manifest.permission.CAMERA)

                                .check()


                    } else {

                        try {
                            val intent = Intent(context, ScannerActivity::class.java)
                            context.startActivity(intent)
                            homeActivity.setOveridePendingTransisi(homeActivity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }


                }else if(position == 2){

                    val intent = Intent(context,ListGudangActivity::class.java)
                    context.startActivity(intent)
                    homeActivity.setOveridePendingTransisi(homeActivity)

                }else if (position == 3){

                    val intent = Intent(context,ListBarangActivity::class.java)
                    context.startActivity(intent)
                    homeActivity.setOveridePendingTransisi(homeActivity)

                }

            }else{

                if(position == 0){

                    if (Build.VERSION.SDK_INT >= 23) {
                        val permissionlistener = object : PermissionListener {
                            override fun onPermissionGranted() {

                                try {
                                    val intent = Intent(context, com.salor.ventgo.ui.activity.stock_masuk.ScannerActivity::class.java)
                                    context.startActivity(intent)
                                    homeActivity.setOveridePendingTransisi(homeActivity)
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }

                            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                                Toast.makeText(context, context.resources.getString(R.string.label_permission_diperlukan), Toast.LENGTH_SHORT).show()
                            }
                        }

                        TedPermission(context)
                                .setPermissionListener(permissionlistener)
                                .setRationaleMessage(context.resources.getString(R.string.label_rational_message))
                                .setDeniedMessage(context.resources.getString(R.string.label_denied_message))
                                .setPermissions(Manifest.permission.CAMERA)

                                .check()


                    } else {

                        try {
                            val intent = Intent(context, com.salor.ventgo.ui.activity.stock_masuk.ScannerActivity::class.java)
                            context.startActivity(intent)
                            homeActivity.setOveridePendingTransisi(homeActivity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }


                }else if(position == 1){

                    if (Build.VERSION.SDK_INT >= 23) {
                        val permissionlistener = object : PermissionListener {
                            override fun onPermissionGranted() {

                                try {
                                    val intent = Intent(context, com.salor.ventgo.ui.activity.stock_keluar.ScannerActivity::class.java)
                                    context.startActivity(intent)
                                    homeActivity.setOveridePendingTransisi(homeActivity)
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }

                            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                                Toast.makeText(context, context.resources.getString(R.string.label_permission_diperlukan), Toast.LENGTH_SHORT).show()
                            }
                        }

                        TedPermission(context)
                                .setPermissionListener(permissionlistener)
                                .setRationaleMessage(context.resources.getString(R.string.label_rational_message))
                                .setDeniedMessage(context.resources.getString(R.string.label_denied_message))
                                .setPermissions(Manifest.permission.CAMERA)

                                .check()


                    } else {

                        try {
                            val intent = Intent(context, com.salor.ventgo.ui.activity.stock_keluar.ScannerActivity::class.java)
                            context.startActivity(intent)
                            homeActivity.setOveridePendingTransisi(homeActivity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }


                }else if(position == 2){

                    val intent = Intent(context,com.salor.ventgo.ui.activity.stock_opname_stock.ListGudangActivity::class.java)
                    context.startActivity(intent)
                    homeActivity.setOveridePendingTransisi(homeActivity)

                }else if (position == 3){

                    val intent = Intent(context,com.salor.ventgo.ui.activity.barang_stock.ListBarangActivity::class.java)
                    context.startActivity(intent)
                    homeActivity.setOveridePendingTransisi(homeActivity)

                }

            }



        }

    }

    override fun getItemCount(): Int {
        return item_homes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var lParentContent : ViewGroup
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
            lParentContent = itemView.findViewById(R.id.lParentContent)

        }
    }

    fun setAnimContent(view : ViewGroup,target : View) {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(view)
                target.visibility = View.VISIBLE

            }, 400)
        } catch (e: Exception) {
            e.printStackTrace()
            target.visibility = View.VISIBLE
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
