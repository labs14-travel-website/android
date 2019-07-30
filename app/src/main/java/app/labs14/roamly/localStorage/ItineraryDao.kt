package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.Itinerary

@Dao
interface ItineraryDao {

    @Insert
    fun insert(itinerary:Itinerary)

    @Update
    fun update(itinerary:Itinerary)

    @Delete
    fun delete(itinerary: Itinerary)

    @Query("DELETE FROM itinerary_table")
    fun deleteAllItineraries()

    @Query("SELECT * FROM itinerary_table")
    fun getAllItineraries(): LiveData<List<Itinerary>>
}