package app.labs14.roamly.models

import com.google.android.gms.maps.model.LatLng
import java.util.*

class ActivityEvent {
    val id = 0
    val name = ""
    val date = Date()
    var location = LatLng(0.0,0.0)
    var startTime = Date()
    var endTime = Date()

    fun setLocation(lat:Double, lon:Double){location = LatLng(lat,lon)}
}
