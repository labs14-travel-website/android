package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.EventLocation

@Dao
interface EventLocationDao {

    @Insert
    fun insert(eventLocation:EventLocation)

    @Update
    fun update(eventLocation:EventLocation)

    @Delete
    fun delete(eventLocation:EventLocation)

    @Query("DELETE FROM event_table")
    fun deleteAllEventLocations()

    @Query("SELECT * FROM event_table")
    fun getAllEventLocations(): LiveData<List<EventLocation>>
}