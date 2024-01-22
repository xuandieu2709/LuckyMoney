package vn.xdeuhug.luckyMoney.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.InputFilter
import android.text.Spanned
import android.util.Base64
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import timber.log.Timber
import vn.xdeuhug.luckyMoney.app.AppApplication
import vn.xdeuhug.luckyMoney.constants.AppConstants
import vn.xdeuhug.luckyMoney.other.CenterLayoutManager
import vn.xdeuhug.luckyMoney.other.PreCachingLayoutManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.NetworkInterface
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern


object AppUtils {

    fun initRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(
            view, LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        )
        view.adapter = adapter
    }

    fun configRecyclerView(
        recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager?
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        (recyclerView.itemAnimator)!!.changeDuration = 0
        ((recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun configRecyclerViewWithFlexBoxLayout(
        recyclerView: RecyclerView, layoutManager: FlexboxLayoutManager?
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        (recyclerView.itemAnimator)!!.changeDuration = 0
        ((recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.isNestedScrollingEnabled = false
        layoutManager!!.flexWrap = FlexWrap.WRAP
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun getMimeType(url: String): String {
        try {
            return url.substring(url.lastIndexOf(".") + 1)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getRandomString(len: Int): String {
        val AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val rnd = SecureRandom()
        val sb = java.lang.StringBuilder(len)
        for (i in 0 until len) sb.append(AB[rnd.nextInt(AB.length)])
        return sb.toString()
    }

    fun get(string: String): String {
        var result = ""
        val stringText = string.split(" ")
        for (i in stringText.indices) {
            if (stringText[i].isNotEmpty()) { // Kiểm tra độ dài của chuỗi
                result += stringText[i].substring(0, 1).lowercase()
            }
        }
        return result
    }

    // Extension function để chuyển đổi List thành ArrayList
    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

}