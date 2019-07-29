package app.labs14.roamly.localStorage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import app.labs14.roamly.models.User

@Dao
interface UserDao {

    @Insert
    fun insert(user:User)

    @Update
    fun update(user:User)

    @Delete
    fun delete(user:User)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUser(id: Long): LiveData<User>
}