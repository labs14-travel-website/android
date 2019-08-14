package app.labs14.roamly.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

//Brandon Lively - 07/28/2019

@Entity(tableName = "users_table")
class Users(
    var user: ArrayList<User>)