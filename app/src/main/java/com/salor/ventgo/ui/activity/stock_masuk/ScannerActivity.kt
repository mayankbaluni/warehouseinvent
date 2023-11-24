package com.salor.ventgo.ui.activity.stock_masuk

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.*
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.obj.scan_stock_masuk.ScanStockMasuk
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.salor.ventgo.ui.activity.stock_masuk.list.StockMasukListActivity
import com.google.gson.Gson
import com.google.zxing.ResultMetadataType
import com.google.zxing.client.android.BeepManager
import com.google.zxing.integration.android.IntentIntegrator

import com.google.zxing.ResultPoint
import com.google.zxing.client.android.InactivityTimer
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_scanner_stock_masuk.*
import kotlinx.android.synthetic.main.item_dialog_scanner_stock_opname_input_kode_barang.*
import kotlinx.android.synthetic.main.item_dialog_stock_opname_scan_success.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ScannerActivity : BaseActivity(), DecoratedBarcodeView.TorchListener {

    lateinit var pLoading : Loading
    var isFlash : Boolean = false
    var capture: CaptureManager? = null
    var barcodeScannerView: DecoratedBarcodeView? = null
    var switchFlashlightButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_stock_masuk)

        setStatusBarTransparent()

        pLoading = Loading(this)
        barcodeScannerView = findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
        barcodeScannerView!!.setTorchListener(this)

        switchFlashlightButton = findViewById<View>(R.id.switch_flashlight) as Button

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...
        if (!hasFlash()) {
            ivFlash.setImageResource(R.drawable.ic_flash_off)
        }


        ivBack.setOnClickListener {
            onBackPressed()
        }


        if (IntentIntegrator.isScan) {

            capture = CaptureManager(this, barcodeScannerView!!)

            capture!!.initializeFromIntent(intent, savedInstanceState)

            capture!!.decode(true)

//            dialogScanSuccess()

//            onPause()
//            barcodeScannerView!!.pause()
//            capture!!.onDestroy()

        } else {

            capture = CaptureManager(this, barcodeScannerView!!)
            capture!!.initializeFromIntent(intent, savedInstanceState)
            capture!!.decode(false)


        }

        ivFlash.setOnClickListener(View.OnClickListener {


            if (isFlash) {
                ivFlash.setImageResource(R.drawable.ic_flash_off)
                barcodeScannerView!!.setTorchOff()

                isFlash = false

            }else{
                ivFlash.setImageResource(R.drawable.ic_flash_on)
                barcodeScannerView!!.setTorchOn()

                isFlash = true
            }

        })


        ivInputCode.setOnClickListener(View.OnClickListener {
            barcodeScannerView!!.pause()
            dialogInputCode()
        })

        btnList.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@ScannerActivity, StockMasukListActivity::class.java)
            startActivity(intent)
            finish()
            setOveridePendingTransisi(this@ScannerActivity)


        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if(requestCode == Cons.REQ_SCAN && resultCode == Cons.RES_SCAN){
//
//            capture!!.decode(false)
//
//        }

    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun onTorchOn() {
        ivFlash.setImageResource(R.drawable.ic_flash_on)
    }

    override fun onTorchOff() {
        ivFlash.setImageResource(R.drawable.ic_flash_off)
    }

    class CaptureManager(private val activity: ScannerActivity, private val barcodeView: DecoratedBarcodeView) {
        private var orientationLock = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        private var returnBarcodeImagePath = false

        lateinit var pLoading: Loading
        private var destroyed = false

        private val inactivityTimer: InactivityTimer
        lateinit var beepManager: BeepManager

        lateinit var handler: Handler

        private var finishWhenClosed = false

        private val callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                barcodeView.pause()
                beepManager.playBeepSoundAndVibrate()

                handler.post { returnResult(result) }

            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {

            }
        }

        private val stateListener = object : CameraPreview.StateListener {
            override fun previewSized() {

            }

            override fun previewStarted() {

            }

            override fun previewStopped() {

            }

            override fun cameraError(error: Exception) {
                displayFrameworkBugMessageAndExit()
            }

            override fun cameraClosed() {
                if (finishWhenClosed) {
                    Log.d(TAG, "Camera closed; finishing activity")
                    finish()
                }
            }
        }

        private var askedPermission = false

        init {
            pLoading = Loading(activity)

            barcodeView.barcodeView.addStateListener(stateListener)

            handler = Handler()

            inactivityTimer = InactivityTimer(activity, Runnable {
                Log.d(TAG, "Finishing due to inactivity")
                finish()
            })

            beepManager = BeepManager(activity)
        }

        /**
         * Perform initialization, according to preferences set in the intent.
         *
         * @param intent the intent containing the scanning preferences
         * @param savedInstanceState saved state, containing orientation lock
         */
        fun initializeFromIntent(intent: Intent?, savedInstanceState: Bundle?) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            if (savedInstanceState != null) {
                // If the screen was locked and unlocked again, we may start in a different orientation
                // (even one not allowed by the manifest). In this case we restore the orientation we were
                // previously locked to.
                this.orientationLock = savedInstanceState.getInt(SAVED_ORIENTATION_LOCK, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            }

            if (intent != null) {
                // Only lock the orientation if it's not locked to something else yet
                val orientationLocked = intent.getBooleanExtra(Intents.Scan.ORIENTATION_LOCKED, true)
                if (orientationLocked) {
                    lockOrientation()
                }

                if (Intents.Scan.ACTION == intent.action) {
                    barcodeView.initializeFromIntent(intent)
                }

                if (!intent.getBooleanExtra(Intents.Scan.BEEP_ENABLED, true)) {
                    beepManager.isBeepEnabled = true
                }

                if (intent.hasExtra(Intents.Scan.TIMEOUT)) {
                    val runnable = Runnable { returnResultTimeout() }
                    handler.postDelayed(runnable, intent.getLongExtra(Intents.Scan.TIMEOUT, 0L))
                }

                if (intent.getBooleanExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, false)) {
                    returnBarcodeImagePath = true
                }
            }
        }

        /**
         * Lock display to current orientation.
         */
        protected fun lockOrientation() {
            // Only get the orientation if it's not locked to one yet.
            if (this.orientationLock == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                // Adapted from http://stackoverflow.com/a/14565436
                val display = activity.windowManager.defaultDisplay
                val rotation = display.rotation
                val baseOrientation = activity.resources.configuration.orientation
                var orientation = 0
                if (baseOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    }
                } else if (baseOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    }
                }

                this.orientationLock = orientation
            }

            activity.requestedOrientation = this.orientationLock
        }

        /**
         * Start decoding.
         */
        fun decode(isLoading: Boolean) {
            if (isLoading) {

            } else {
                barcodeView.decodeSingle(callback)
            }
        }

        /**
         * Call from Activity#onResume().
         */
        fun onResume() {
            if (Build.VERSION.SDK_INT >= 23) {
                openCameraWithPermission()
            } else {
                barcodeView.resume()
            }
            inactivityTimer.start()
        }

        @TargetApi(23)
        private fun openCameraWithPermission() {
            if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                barcodeView.resume()
            } else if (!askedPermission) {
                ActivityCompat.requestPermissions(this.activity,
                        arrayOf(Manifest.permission.CAMERA),
                        cameraPermissionReqCode)
                askedPermission = true
            } else {
                // Wait for permission result
            }
        }

        /**
         * Call from Activity#onRequestPermissionsResult
         * @param requestCode The request code passed in [android.support.v4.app.ActivityCompat.requestPermissions].
         * @param permissions The requested permissions.
         * @param grantResults The grant results for the corresponding permissions
         * which is either [android.content.pm.PackageManager.PERMISSION_GRANTED]
         * or [android.content.pm.PackageManager.PERMISSION_DENIED]. Never null.
         */
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (requestCode == cameraPermissionReqCode) {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    barcodeView.resume()
                } else {
                    // TODO: display better error message.
                    displayFrameworkBugMessageAndExit()
                }
            }
        }

        /**
         * Call from Activity#onPause().
         */
        fun onPause() {

            inactivityTimer.cancel()
            barcodeView.pauseAndWait()
        }

        /**
         * Call from Activity#onDestroy().
         */
        fun onDestroy() {
            destroyed = true
            inactivityTimer.cancel()
            handler.removeCallbacksAndMessages(null)
        }

        /**
         * Call from Activity#onSaveInstanceState().
         */
        fun onSaveInstanceState(outState: Bundle) {
            outState.putInt(SAVED_ORIENTATION_LOCK, this.orientationLock)
        }

        /**
         * Save the barcode image to a temporary file stored in the application's cache, and return its path.
         * Only does so if returnBarcodeImagePath is enabled.
         *
         * @param rawResult the BarcodeResult, must not be null
         * @return the path or null
         */
        private fun getBarcodeImagePath(rawResult: BarcodeResult): String? {
            var barcodeImagePath: String? = null
            if (returnBarcodeImagePath) {
                val bmp = rawResult.bitmap
                try {
                    val bitmapFile = File.createTempFile("barcodeimage", ".jpg", activity.cacheDir)
                    val outputStream = FileOutputStream(bitmapFile)
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                    barcodeImagePath = bitmapFile.absolutePath
                } catch (e: IOException) {
                    Log.w(TAG, "Unable to create temporary file and store bitmap! $e")
                }

            }
            return barcodeImagePath
        }

        private fun finish() {
            activity.finish()
        }

        protected fun closeAndFinish() {
            if (barcodeView.barcodeView.isCameraClosed) {
                finish()
            } else {
                finishWhenClosed = true
            }

            barcodeView.pause()
            inactivityTimer.cancel()
        }

        protected fun returnResultTimeout() {
            val intent = Intent(Intents.Scan.ACTION)
            intent.putExtra(Intents.Scan.TIMEOUT, true)
            activity.setResult(Activity.RESULT_CANCELED, intent)
            closeAndFinish()
        }

        protected fun returnResult(rawResult: BarcodeResult) {
//            val intent = resultIntent(rawResult, getBarcodeImagePath(rawResult))

//            See.toast(activity,""+rawResult.toString())

//            dialogSuccessScan(rawResult.toString())


            //See.toast(activity,rawResult.toString())
            userScan(rawResult.toString())



//            activity.setResult(Activity.RESULT_OK, intent)
//            closeAndFinish()
        }

        fun getReturnBarcode(rawResult: BarcodeResult): String? {

            return getBarcodeImagePath(rawResult)
        }

        protected fun displayFrameworkBugMessageAndExit() {
            if (activity.isFinishing || this.destroyed || finishWhenClosed) {
                return
            }
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(activity.getString(com.google.zxing.client.android.R.string.zxing_app_name))
            builder.setMessage(activity.getString(com.google.zxing.client.android.R.string.zxing_msg_camera_framework_bug))
            builder.setPositiveButton(com.google.zxing.client.android.R.string.zxing_button_ok) { dialog, which -> finish() }
            builder.setOnCancelListener { finish() }
            builder.show()
        }

        companion object {
            private val TAG = CaptureManager::class.java.simpleName

            var cameraPermissionReqCode = 250
            private val SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK"

            /**
             * Create a intent to return as the Activity result.
             *
             * @param rawResult the BarcodeResult, must not be null.
             * @param barcodeImagePath a path to an exported file of the Barcode Image, can be null.
             * @return the Intent
             */


            fun resultIntent(rawResult: BarcodeResult, barcodeImagePath: String?): Intent {
                val intent = Intent(Intents.Scan.ACTION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                intent.putExtra(Intents.Scan.RESULT, rawResult.toString())
                intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult.barcodeFormat.toString())
                val rawBytes = rawResult.rawBytes
                if (rawBytes != null && rawBytes.size > 0) {
                    intent.putExtra(Intents.Scan.RESULT_BYTES, rawBytes)
                }
                val metadata = rawResult.resultMetadata
                if (metadata != null) {
                    if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                        intent.putExtra(Intents.Scan.RESULT_UPC_EAN_EXTENSION,
                                metadata[ResultMetadataType.UPC_EAN_EXTENSION].toString())
                    }
                    val orientation = metadata[ResultMetadataType.ORIENTATION] as Number
                    if (orientation != null) {
                        intent.putExtra(Intents.Scan.RESULT_ORIENTATION, orientation.toInt())
                    }
                    val ecLevel = metadata[ResultMetadataType.ERROR_CORRECTION_LEVEL] as String
                    if (ecLevel != null) {
                        intent.putExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL, ecLevel)
                    }
                    val byteSegments = metadata[ResultMetadataType.BYTE_SEGMENTS] as Iterable<ByteArray>
                    if (byteSegments != null) {
                        var i = 0
                        for (byteSegment in byteSegments) {
                            intent.putExtra(Intents.Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, byteSegment)
                            i++
                        }
                    }
                }
                if (barcodeImagePath != null) {
                    intent.putExtra(Intents.Scan.RESULT_BARCODE_IMAGE_PATH, barcodeImagePath)
                }
                return intent
            }
        }


        private fun userScan(code: String) {
            pLoading.showLoading(activity.resources.getString(R.string.label_loading_title_dialog), false)
            val service = ApiClient.getClient()
            val gson = Gson()

            val call = service.stockBarangMasukScan(code)
            See.logE("code", "" + code)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    pLoading.dismissDialog()
                    if (response.isSuccessful) {
                        try {
                            val respon = response.body()!!.string()
                            val json = JSONObject(respon)

                            See.logE("respon_chekin", respon)

                            val api_status = json.getInt(Cons.API_STATUS)
                            val api_message = json.getString(Cons.API_MESSAGE)


                            if (api_status == Cons.INT_STATUS) {

                                val jsonObjScan = json.getJSONObject(Cons.ITEMS_DATA)

                                val datum: ScanStockMasuk
                                datum = gson.fromJson(jsonObjScan.toString(), ScanStockMasuk::class.java!!)


                                // TODO jika scan sukses

                                barcodeView.resume()
                                barcodeView.decodeSingle(callback)

                                val intent = Intent(activity, FormStockMasukActivity::class.java)
                                intent.putExtra(Cons.JSON, Gson().toJson(datum))
                                activity.startActivity(intent)
                                activity.finish()
                                activity.setOveridePendingTransisi(activity)


                            } else {

                                See.toast(activity, api_message)
                                barcodeView.resume()
                                barcodeView.decodeSingle(callback)

                            }

                        } catch (e: IOException) {
                            e.printStackTrace()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    } else {
                        barcodeView.resume()
                        barcodeView.decodeSingle(callback)
                        pLoading.dismissDialog()
                        See.toast(activity, activity.resources.getString(R.string.label_something_wrong))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    barcodeView.resume()
                    barcodeView.decodeSingle(callback)
                    pLoading.dismissDialog()
                    See.toast(activity, activity.resources.getString(R.string.label_koneksi_error))
                }
            })

        }



        fun loadImagePicasso(uLoad: String, target: ImageView, mDialog: ProgressBar?) {
            val load = uLoad.replace(" ", "%20")
            if (load.equals("", ignoreCase = true) || load.isEmpty() || load.equals("null", ignoreCase = true)) {
                mDialog!!.visibility = View.GONE
                Picasso
                        .with(activity)
                        .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image_512dp.png?alt=media&token=4d687aac-9ca5-41dc-b33e-cea38654923f")
                        .into(target)

            } else {
                mDialog!!.visibility = View.VISIBLE
                Picasso
                        .with(activity)
                        .load(load)

                        .into(target)
            }
        }
    }

    fun dialogInputCode() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_scanner_stock_opname_input_kode_barang)
            pDialog!!.setCancelable(false)

            val etCode = pDialog.etCode
            val ivCloseDialog = pDialog.ivCloseDialog
            val btnCheck = pDialog.btnCheck


            btnCheck.setOnClickListener(View.OnClickListener {

                val str_code = etCode.text.toString()

                if (str_code == ""){

                    etCode.requestFocus()
                    etCode.error = resources.getString(R.string.label_form_wajib_diisi)

                }else{
                    pDialog.dismiss()

                    userScan(str_code)
//                    val intent = Intent(this@ScannerActivity,FormStockMasukActivity::class.java)
//                    startActivity(intent)

                }

            })

            ivCloseDialog.setOnClickListener(View.OnClickListener {
                pDialog.dismiss()

                barcodeScannerView!!.resume()

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

    private fun userScan(code: String) {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()
        val gson = Gson()

        val call = service.stockBarangMasukScan(code)
        See.logE("code", "" + code)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_chekin", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)


                        if (api_status == Cons.INT_STATUS) {

                            val jsonObjScan = json.getJSONObject(Cons.ITEMS_DATA)

                            val datum: ScanStockMasuk
                            datum = gson.fromJson(jsonObjScan.toString(), ScanStockMasuk::class.java!!)


                            // TODO jika scan sukses

                            barcodeScannerView!!.resume()

                            val intent = Intent(this@ScannerActivity, FormStockMasukActivity::class.java)
                            intent.putExtra(Cons.JSON, Gson().toJson(datum))
                            startActivity(intent)
                            finish()
                            setOveridePendingTransisi(this@ScannerActivity)


                        } else {

                            See.toast(this@ScannerActivity, api_message)
                            barcodeScannerView!!.resume()

                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    barcodeScannerView!!.resume()
                    pLoading.dismissDialog()
                    See.toast(this@ScannerActivity, resources.getString(R.string.label_something_wrong))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                barcodeScannerView!!.resume()
                pLoading.dismissDialog()
                See.toast(this@ScannerActivity, resources.getString(R.string.label_koneksi_error))
            }
        })

    }


    fun dialogScanSuccess() {

        try {

            val pDialog = Dialog(this, R.style.DialogLight)
            pDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            pDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pDialog!!.setContentView(R.layout.item_dialog_stock_opname_scan_success)
            pDialog!!.setCancelable(false)

            val btnListProduk = pDialog.btnListProduk
            val btnScanResume = pDialog.btnScanResume

            btnListProduk.setOnClickListener(View.OnClickListener {

                pDialog.dismiss()
                onBackPressed()
            })


            btnScanResume.setOnClickListener(View.OnClickListener {

                pDialog.dismiss()

                barcodeScannerView!!.resume()
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

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this@ScannerActivity)
    }
}
