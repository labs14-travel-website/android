package app.labs14.roamly.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "event_table")
    class EventLocation {

        @PrimaryKey(autoGenerate = true)
        var event_location_id    = 0
        var startLat    : Double = 0.0
        var startLng    : Double = 0.0
        var endLat      : Double = 0.0
        var endLng      : Double = 0.0
        var address     = ""
        var phoneNum    = ""

    constructor(d1 : Double, d2 : Double) {
        startLat = d1
        startLng = d2
    }
        constructor()
}
