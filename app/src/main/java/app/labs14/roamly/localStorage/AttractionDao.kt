package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.Attraction

@Dao
interface AttractionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(attraction:Attraction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(attraction:Attraction)

    @Delete
    fun delete(attraction:Attraction)

    @Query("DELETE FROM attraction_table")
    fun deleteAllAttractions()

    @Query("SELECT * FROM attraction_table")
    fun getAllAttractions(): LiveData<List<Attraction>>

    @Query("SELECT * FROM attraction_table WHERE itinerary_id = :id")
    fun getAttractions(id: Int): LiveData<List<Attraction>>
}