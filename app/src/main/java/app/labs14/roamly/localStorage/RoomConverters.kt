package app.labs14.roamly.localStorage


import androidx.room.TypeConverter
import java.util.Date


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
    // Add more converters here, or make another class altogether
}