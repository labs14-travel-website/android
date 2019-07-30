package app.labs14.roamly.models

import android.graphics.Bitmap
import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "itinerary_table")
class Itinerary(var destinationName: String, var description: String) {

    @ColumnInfo(name = "itinerary_id")
    @PrimaryKey(autoGenerate = true)
    var itinerary_id: Long = 0
}