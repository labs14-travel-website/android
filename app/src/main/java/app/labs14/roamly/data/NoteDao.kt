package app.labs14.roamly.data

import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.Note


//shoon 2019/07/26
@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

}