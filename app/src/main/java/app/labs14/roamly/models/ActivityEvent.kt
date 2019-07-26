package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/25/2019

@Entity(tableName = "activity_event_table"
    , indices = [(Index(value = ["activity_event_id"], name = "idx_activity_events_activity_event_id"))],
    foreignKeys =
    [(ForeignKey(
        entity = EventLocation::class
        , parentColumns = ["event_location_id"]
        , childColumns = ["activity_event_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE))]
)
class ActivityEvent {
    @PrimaryKey(autoGenerate = true)
    var activity_event_id                   = 0

    var name                 = ""
    var startTime            : Long = 0
    var endTime              : Long = 0
    var alarmBefore          : Long = 0
    var alarmAfter           : Long = 0

    @Ignore
    var startLocation        = EventLocation(0.0,0.0)
    @Ignore
    var endLocation          = EventLocation(0.0,0.0)

    var description          = ""

    //Data from the location where the user is coming from.
    var transportType   = 0
    var transportEta    : Long = 0
    var transportLabel  = ""

    constructor(
        id:                       Int,
        name:                     String,
        startTime:                Long,
        endTime:                  Long,
        alarmBefore:              Long,
        alarmAfter:               Long,
        description:              String,
        startLocation:            EventLocation,
        endLocation:              EventLocation,
        transportationType:       Int,
        transportationEta:        Long,
        transportationLabel:      String
    ) {
        this.activity_event_id    = id
        this.name                 = name
        this.startTime            = startTime
        this.endTime              = endTime
        this.alarmBefore          = alarmBefore
        this.alarmAfter           = alarmAfter
        this.description          = description
        this.startLocation        = startLocation
        this.endLocation          = endLocation
        this.transportType        = transportationType
        this.transportEta         = transportationEta
        this.transportLabel       = transportationLabel
    }

    constructor(name: String, startTime: Long, endTime: Long, startLocation : EventLocation, endLocation : EventLocation) {
        this.startTime      = startTime
        this.endTime        = endTime
        this.name           = name
        this.startLocation  = startLocation
        this.endLocation    = endLocation
    }

    constructor(name: String, startTime: Long, endTime: Long, startLocation : EventLocation) {
        this.startTime      = startTime
        this.endTime        = endTime
        this.name           = name
        this.startLocation  = startLocation
        this.endLocation    = startLocation
    }

    constructor(
        id: Int,
        name: String,
        startTime: Long,
        endTime: Long,
        alarmBefore: Long,
        alarmAfter: Long,
        startLocation: EventLocation,
        endLocation: EventLocation,
        description: String,
        transportType: Int,
        transportEta: Long,
        transportLabel: String
    ) {
        this.activity_event_id = id
        this.name = name
        this.startTime = startTime
        this.endTime = endTime
        this.alarmBefore = alarmBefore
        this.alarmAfter = alarmAfter
        this.startLocation = startLocation
        this.endLocation = endLocation
        this.description = description
        this.transportType = transportType
        this.transportEta = transportEta
        this.transportLabel = transportLabel
    }

    constructor()


}
