package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.ActivityEvent

@Dao
interface ActivityEventDao {

    @Insert
    fun insert(activityEvent:ActivityEvent)

    @Update
    fun update(activityEvent:ActivityEvent)

    @Delete
    fun delete(activityEvent:ActivityEvent)

    @Query("DELETE FROM activity_event_table")
    fun deleteAllActivityEvents()

    @Query("SELECT * FROM activity_event_table")
    fun getAllActivityEvents(): LiveData<List<ActivityEvent>>
}