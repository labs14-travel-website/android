package app.labs14.roamly.localStorage


import androidx.room.TypeConverter
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.EventLocation
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User
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

    @TypeConverter
    @JvmStatic
    fun eventLocationToString(eventLocation: EventLocation?): String? {
        return null
    }

    @TypeConverter
    @JvmStatic
    fun activityEventToString(activityEvent: ActivityEvent?): String? {
        return null
    }

    @TypeConverter
    @JvmStatic
    fun tripToString(trip: Trip?): String? {
        return null
    }

    @TypeConverter
    @JvmStatic
    fun userToString(user: User?): String? {
        return null
    }

    // Add more converters here, or make another class altogether
}