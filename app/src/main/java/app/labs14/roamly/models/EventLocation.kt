package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "event_table",
    foreignKeys =
    [(ForeignKey(
        entity = ActivityEvent::class
        , parentColumns = ["activity_event_id"]
        , childColumns = ["event_location_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
data class EventLocation(
    @PrimaryKey(autoGenerate = true)
    val event_location_id: Long,
    val activityEventId: Long,
    var startLat: Float,
    var startLng: Float,
    var endLat: Float,
    var endLng: Float,
    var address: String,
    var phoneNum: String
) {

    @Ignore
    constructor(activityEventId: Long, startLng: Float, startLat: Float) :
            this(0, activityEventId, startLat, startLng, startLat, startLng, "", "")

    @Ignore
    constructor(activityEventId: Long, startLng: Float, startLat: Float, address: String, phoneNum: String) :
            this(0, activityEventId, startLat, startLng, startLat, startLng, address, phoneNum)

    @Ignore
    constructor(activityEventId: Long, startLng: Float, startLat: Float, endLat: Float, endLng: Float, address: String, phoneNum: String) :
            this(0, activityEventId, startLat, startLng, endLat, endLng, address, phoneNum)
}
