package com.salor.ventgo.ui.activity

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.StringRes
import android.support.transition.TransitionManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.InputType
import android.text.Spanned
import android.util.Base64
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.Toast
import com.salor.ventgo.R
import com.salor.ventgo.helper.See


import java.net.MalformedURLException
import java.net.URL
import java.text.DecimalFormat

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


open class BaseActivity : AppCompatActivity() {

    private var toast: Toast? = null

    lateinit var BOLD: Typeface
    lateinit var SEMIBOLD: Typeface
    lateinit var REGUlAR: Typeface
    lateinit var ITALIC: Typeface


    // TODO getScreen Height
    val screenHeight: Int
        get() {
            val displayMetrics = resources.displayMetrics
            return displayMetrics.heightPixels
        }

    // TODO getScreen Widht
    val screenWidht: Int
        get() {
            val displayMetrics = resources.displayMetrics
            return displayMetrics.widthPixels
        }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        SEMIBOLD = Typeface.createFromAsset(assets, "fonts/seguisb.ttf")
//        REGUlAR = Typeface.createFromAsset(assets, "fonts/segoeui.ttf")
//        BOLD = Typeface.createFromAsset(assets, "fonts/segoeuib.ttf")
//        ITALIC = Typeface.createFromAsset(assets, "fonts/Cabin-Italic.otf")
    }

    fun toast(@StringRes message: Int) {
        toast(getString(message))
    }

    fun setAnimViewVisible(lParentContent : View,vTarget : View,duration : Long) {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent as ViewGroup)
                vTarget.visibility = View.VISIBLE


            }, duration)
        } catch (e: Exception) {
            e.printStackTrace()
            vTarget.visibility = View.VISIBLE
        }


    }

    fun setAnimViewGone(lParentContent : View,vTarget : View,duration : Long) {

        try {
            Handler().postDelayed({

                TransitionManager.beginDelayedTransition(lParentContent as ViewGroup)
                vTarget.visibility = View.GONE


            }, duration)
        } catch (e: Exception) {
            e.printStackTrace()
            vTarget.visibility = View.GONE
        }


    }


    fun setOveridePendingTransisi(context: Activity) {

        try {
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun toast(toastMessage: String?) {
        if (toastMessage != null && !toastMessage.isEmpty()) {
            if (toast != null) toast!!.cancel()
            toast = Toast.makeText(this.applicationContext, toastMessage, Toast.LENGTH_LONG)
            toast!!.show()

        }
    }

    fun getPixelValue(context: Context, dimenId: Int): Int {
        var resources = context.getResources()

        val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId.toFloat(),
                resources.getDisplayMetrics()
        ).toInt()

        return px
    }

    fun encodeImageWithOutDirectory(path: String): String {

        val fileName = path.substring(path.lastIndexOf("/") + 1)
        //
        val file = File(path)
        See.logE("fileName", "" + fileName)
        //

        var encodedBase64: String = ""
        var encImage: String = ""
        try {
            val fileInputStreamReader = FileInputStream(file)
            val bytes = ByteArray(file.length().toInt())
            fileInputStreamReader.read(bytes)
            encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
            encImage = encodedBase64!!.toString()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return encImage

    }

    fun getTextFromHtml(str_html: String): Spanned? {

        val htmlAsSpanned = Html.fromHtml(str_html)

        return htmlAsSpanned
    }

    fun setEditTextPasswordTypeHide(etText: EditText) {

//        etText.transformationMethod = PasswordTransformationMethod()
        etText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

    }

    fun setEditTextPasswordTypeVisible(etText: EditText) {

        etText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

    }

    // TODO set Margins
    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }


    // TODO show keyboard
    fun showKeyboard(ettext: EditText) {
        ettext.setSelection(ettext.text.toString().trim { it <= ' ' }.length)
        ettext.requestFocus()
        ettext.postDelayed({
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(ettext, 0)
        }, 200)
    }

    // TODO gridlayout recycler view
    fun calculateNoOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 180).toInt()
    }

    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

    }

    fun setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        }
    }


    fun collumnMenu(): Int {
        var c = Companion.getScreenWidht(this) / 320
        if (c < 3) c = 3
        return c
    }


    fun setFlagFullScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun setBlackStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE


        }
    }

    fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    fun Konektivitas(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            true
        } else {
            false
        }

    }

    companion object {


        fun math(f: Float): Int {
            val c = (f + 0.5f).toInt()
            val n = f + 0.5f
            return if ((n - c) % 2 == 0f) f.toInt() else c
        }

        fun getMimeType(url: String): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }


        // TODO create custom marker
        fun createDrawableFromView(context: Context, view: View): Bitmap {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            view.layoutParams = ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
            view.buildDrawingCache()
            val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            view.draw(canvas)

            return bitmap
        }

        // TODO: 23/02/18 getExtension from file
        fun getExtension(filename: String): String {
            val filenameArray = filename.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val extension = filenameArray[filenameArray.size - 1]
            println(extension)

            return extension

        }

        fun labelPrice(price: Double): String {

            val formatter = DecimalFormat("#,###")
            val harga = "Rp " + formatter.format(price)

            return harga.replace(",", ".")
        }

        fun labelBilangan(price: Double): String {

            val formatter = DecimalFormat("#,###")
            val harga = formatter.format(price)

            return harga.replace(",", ".")
        }

        fun getFileNameFromURL(url: String?): String {
            if (url == null) {
                return ""
            }
            try {
                val resource = URL(url)
                val host = resource.host
                if (host.length > 0 && url.endsWith(host)) {
                    // handle ...example.com
                    return ""
                }
            } catch (e: MalformedURLException) {
                return ""
            }

            val startIndex = url.lastIndexOf('/') + 1
            val length = url.length

            // find end index for ?
            var lastQMPos = url.lastIndexOf('?')
            if (lastQMPos == -1) {
                lastQMPos = length
            }

            // find end index for #
            var lastHashPos = url.lastIndexOf('#')
            if (lastHashPos == -1) {
                lastHashPos = length
            }

            // calculate the end index
            val endIndex = Math.min(lastQMPos, lastHashPos)
            return url.substring(startIndex, endIndex)
        }

//        fun setJustStatusBarTransparent(){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val window = window
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                window.statusBarColor = Color.TRANSPARENT
//            }
//        }
//
//        fun setStatusBarGradiant(activity: Activity) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val window = activity.window
//                val background = activity.resources.getDrawable(R.drawable.bg_status_bar_gradient_home)
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = activity.resources.getColor(R.color.transparent)
//                window.navigationBarColor = activity.resources.getColor(R.color.transparent)
//                window.setBackgroundDrawable(background)
//            }
//        }

        fun setStatusBarGradiantLogin(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                val background = activity.resources.getDrawable(R.drawable.bg_gradient_login)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(R.color.transparent)
                window.navigationBarColor = activity.resources.getColor(R.color.black)
                window.setBackgroundDrawable(background)
            }
        }

        fun setStatusBarGradiantListSearch(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                val background = activity.resources.getDrawable(R.drawable.bg_gradient_list)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(R.color.transparent)
                window.navigationBarColor = activity.resources.getColor(R.color.black)
                window.setBackgroundDrawable(background)
            }
        }

//        fun setColorStatusBar(activity: Activity) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val window = activity.window
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = activity.resources.getColor(R.color.color_status_bar_dark)
//                window.navigationBarColor = activity.resources.getColor(R.color.color_status_bar_dark)
//            }
//        }

        fun hideKeyboardwithoutPopulate(activity: Activity) {
            try {
                val inputMethodManager = activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                        activity.currentFocus!!.windowToken, 0)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }

        fun getScreenWidht(baseActivity: BaseActivity): Int {
            val displayMetrics = DisplayMetrics()
            baseActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }
    }
}
