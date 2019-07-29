package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User

@Dao
interface TripDao {

    @Insert
    fun insert(trip:Trip)

    @Update
    fun update(trip:Trip)

    @Delete
    fun delete(trip: Trip)

    @Query("DELETE FROM trip_table")
    fun deleteAllTrips()

    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip_table WHERE userId = :id")
    fun getTrip(id: Long): LiveData<List<Trip>>
}