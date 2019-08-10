package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019
@Entity(tableName = "users_table")
class Users(
    var user: ArrayList<User>)