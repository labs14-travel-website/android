package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "trip_table",
    foreignKeys =
    [(ForeignKey(
        entity = User::class
        , parentColumns = ["user_id"]
        , childColumns = ["trip_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val trip_id: Long, val userId: Long, var description: String
) {

    @Ignore
    var activityEvents = listOf<ActivityEvent>()

    @Ignore
    constructor(userId: Long) : this(0, userId, "")

    @Ignore
    constructor(userId: Long, description: String) : this(0, userId, description)
}