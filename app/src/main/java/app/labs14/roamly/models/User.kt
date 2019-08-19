package app.labs14.roamly.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


//shoon 2019/08/07
@Entity(tableName = "user_table")
class User(
    var id:String,
    var name: String = "",
    var email: String = "",

    var itineraries: List<Itinerary> )