package app.labs14.roamly.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.format.DateUtils
import android.util.TypedValue
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun dpToPx(dp: Float, context: Context): Int {
        return dpToPx(dp, context.resources)
    }

    private fun dpToPx(dp: Float, resources: Resources): Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        return px.toInt()
    }
}



object VectorDrawableUtils {

    fun getDrawable(context: Context, drawableResId: Int): Drawable? {
        return VectorDrawableCompat.create(context.resources, drawableResId, context.theme)
    }

    fun getDrawable(context: Context, drawableResId: Int, colorFilter: Int): Drawable {
        val drawable = getDrawable(context, drawableResId)
        drawable!!.setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN)
        return drawable
    }

    fun getBitmap(context: Context, drawableId: Int): Bitmap {
        val drawable = getDrawable(context, drawableId)

        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}



object DateTimeUtils {

    fun parseDateTime(dateString: String, originalFormat: String, outputFromat: String): String {

        val formatter = SimpleDateFormat(originalFormat, Locale.US)
        var date: Date? = null
        return try {
            date = formatter.parse(dateString)

            val dateFormat = SimpleDateFormat(outputFromat, Locale("US"))

            dateFormat.format(date)

        } catch (e: ParseException) {
            e.printStackTrace().toString()
        }

    }

    fun getRelativeTimeSpan(dateString: String, originalFormat: String): String {

        val formatter = SimpleDateFormat(originalFormat, Locale.US)
        var date: Date? = null
        return try {
            date = formatter.parse(dateString)

            DateUtils.getRelativeTimeSpanString(date!!.time).toString()

        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }

    }
}


object BitmapConversion{
    fun getBytesFromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap != null) {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            return stream.toByteArray()
        }
        return null
    }
}