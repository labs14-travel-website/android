package app.labs14.roamly.models

import android.graphics.Bitmap
import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "itinerary_table")
class Itinerary(@PrimaryKey var itinerary_id:Int, var destinationName: String, var yearVisited: String) {

/*    @ColumnInfo(name = "itin_id")
    @PrimaryKey(autoGenerate = true)
    var itinerary_id: Int = 0*/

    constructor(itinerary_id: Int):this(itinerary_id,"Destination", "Jan 2020")
}