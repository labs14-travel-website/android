package app.labs14.roamly.localStorage

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.EventLocation
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User

@Database(entities = [User::class, EventLocation::class, Trip::class, ActivityEvent::class], exportSchema = true,version = 3)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventLocationDao(): EventLocationDao
    abstract fun tripDao(): TripDao
    abstract fun activityEventDao(): ActivityEventDao


    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        //.addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //PopulateDbAsyncTask(instance).execute()
            }
        }
    }


    //Mock Data here
    class PopulateDbAsyncTask(db: UserDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val userDao = db?.userDao()

        override fun doInBackground(vararg p0: Unit?) {
            userDao?.insert(User(""))
            userDao?.insert(User(""))
            userDao?.insert(User(""))
        }
    }
}