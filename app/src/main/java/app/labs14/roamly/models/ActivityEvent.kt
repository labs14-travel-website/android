package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "activity_event_table",
    foreignKeys =
    [(ForeignKey(
        entity = Trip::class
        , parentColumns = ["trip_id"]
        , childColumns = ["activity_event_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
data class ActivityEvent(
    @PrimaryKey(autoGenerate = true)
    var activity_event_id: Long,
    val tripId: Long,
    var name: String,
    var startTime: Long,
    var endTime: Long,
    var alarmBefore:Long,
    var alarmAfter:Long,
    var description:String,
    var transportType: Int,
    var transportEta:Long,
    var transportLabel:String) {

    @Ignore
    var location = EventLocation(activity_event_id,0F, 0F)

    @Ignore
    constructor(tripId: Long, name: String) : this(0,tripId,name,0,0,0,0,"",0,0,"")

    @Ignore
    constructor(tripId: Long, name: String,startTime: Long,endTime: Long) : this(0,tripId,name,startTime,endTime,0,0,"",0,0,"")
}
