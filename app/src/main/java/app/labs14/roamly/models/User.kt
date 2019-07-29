package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(tableName = "user_table")

data class User(
    @PrimaryKey(autoGenerate = true)
    var user_id: Long,
    var name: String = ""
) {
    @Ignore
    var trips = listOf<Trip>()
    constructor(name:String = "") : this(0,name)
}