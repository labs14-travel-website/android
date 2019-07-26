package app.labs14.roamly.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.util.*


//shoon 2019/07/26
//@Entity(tableName = "itinerary_table")
data class ActivityEvent (
    val name: String?= "",
    val date: Long?= 1,
    var location:LocationModel?=null,
    var startTime:Long,
    var endTime:Long
    ) {
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0
    }
