package app.labs14.roamly.models

import androidx.room.*

@Entity(tableName = "trip_table"
    , indices = [(Index(value = ["trip_id"], name = "idx_trips_trip_id"))],
    foreignKeys =
    [(ForeignKey(
        entity = ActivityEvent::class
        , parentColumns = ["activity_event_id"]
        , childColumns = ["trip_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE))]
)
class Trip {

    @PrimaryKey(autoGenerate = true)
    var trip_id = 0
    var description = ""

    @Ignore
    var activityEvents = mutableListOf<ActivityEvent>()

    constructor(trip_id: Int, description: String, activityEvents: MutableList<ActivityEvent>) {
        this.trip_id = trip_id
        this.description = description
        this.activityEvents = activityEvents
    }

    constructor(description: String, activityEvents: MutableList<ActivityEvent>) {
        this.description = description
        this.activityEvents = activityEvents
    }

    constructor()


}