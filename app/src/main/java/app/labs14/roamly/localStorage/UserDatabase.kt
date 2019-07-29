package app.labs14.roamly.localStorage

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.User

@Database(entities = [User::class, Itinerary::class, Attraction::class], exportSchema = true,version = 11)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun itineraryDao(): ItineraryDao
    abstract fun attractionDao(): AttractionDao

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
                        .addCallback(roomCallback)
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
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    //Mock Data here
    class PopulateDbAsyncTask(db: UserDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val userDao = db?.userDao()
        private val itineraryDao = db?.itineraryDao()
        private val attractionDao = db?.attractionDao()

        override fun doInBackground(vararg p0: Unit?) {
        //Mock data goes here
        }
    }
}