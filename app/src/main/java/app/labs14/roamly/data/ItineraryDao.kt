package app.labs14.roamly.data

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.ItineraryModel



//shoon 2019/07/26
@Dao
interface ItineraryDao {

    @Insert
    fun insert(itinerary: ItineraryModel)

    @Update
    fun update(itinerary: ItineraryModel)

    @Delete
    fun delete(itinerary: ItineraryModel)

    @Query("DELETE FROM itinerary_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM itinerary_table ORDER BY startTime DESC")
    fun getAllNotes(): LiveData<List<ItineraryModel>>

}