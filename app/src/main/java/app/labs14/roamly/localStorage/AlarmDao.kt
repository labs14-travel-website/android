package app.labs14.roamly.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(alarm:Alarm)

    @Delete
    fun delete(alarm:Alarm)

    @Query("DELETE FROM alarm_table")
    fun deleteAllAlamrs()

    @Query("SELECT * FROM alarm_table")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarm_table WHERE alarm_id = :id")
    fun getAlarms(id: Int): LiveData<List<Alarm>>
}