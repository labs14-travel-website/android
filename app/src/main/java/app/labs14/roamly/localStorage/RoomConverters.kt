package app.labs14.roamly.localStorage


import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.util.Date
import android.graphics.Bitmap as AndroidGraphicsBitmap
import android.graphics.Bitmap
import android.R.array
import java.nio.ByteBuffer


object RoomConverters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        byteBuffer.rewind()
        return byteBuffer.array()
    }

    @TypeConverter
    @JvmStatic
    fun byteArrayToBitmap(src: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(src, 0, src.size)
    }
    //Add more converters here, or make another class altogether
}