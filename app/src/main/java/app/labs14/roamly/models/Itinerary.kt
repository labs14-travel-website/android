package app.labs14.roamly.models

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.room.*
import app.labs14.roamly.data.Constants
import app.labs14.roamly.utils.BitmapConversion
import app.labs14.roamly.utils.Utils

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "itinerary_table")
class Itinerary(@PrimaryKey var itinerary_id:Int, var destinationName: String, var yearVisited: String) {

    constructor(itinerary_id: Int):this(itinerary_id,"Destination", "Jan 2020")
}