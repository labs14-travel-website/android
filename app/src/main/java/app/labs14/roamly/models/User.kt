package app.labs14.roamly.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "user_table"
    , indices = [(Index(value = ["user_id"], name = "idx_users_user_id"))],
    foreignKeys =
    [(ForeignKey(
        entity = Trip::class
        , parentColumns = ["trip_id"]
        , childColumns = ["user_id"]
        , onUpdate = CASCADE
        , onDelete = CASCADE))]
)

class User {

    @PrimaryKey(autoGenerate = true)
    var user_id: Int = 0

    var name = ""

    @Ignore
    var trips = mutableListOf<Trip>()

    constructor( name: String) {
        this.name = name
    }
    constructor(id:Int, name: String) {
        this.name = name
        this.user_id = id
    }

    constructor(name: String, trips: MutableList<Trip>) {
        this.name = name
        this.trips = trips
    }


}