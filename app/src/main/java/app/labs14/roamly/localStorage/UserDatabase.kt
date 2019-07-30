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

@Database(entities =[Itinerary::class, Attraction::class], exportSchema = true,version = 16)
abstract class UserDatabase : RoomDatabase() {

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
        private val itineraryDao = db?.itineraryDao()
        private val attractionDao = db?.attractionDao()

        override fun doInBackground(vararg p0: Unit?) {
            itineraryDao?.insert(Itinerary("Bali", "This place is rad"))
            itineraryDao?.insert(Itinerary("Vegas", "What happens here, stays here"))
            itineraryDao?.insert(Itinerary("Fiji", "Climb it!"))
            itineraryDao?.insert(Itinerary("San Francisco", "Brapp brapp!"))

            attractionDao?.insert(Attraction(1,"Swim with Sharks1-1", 3000,4000,25,25,"Don't be eaten","23.5215215", "65.52353", "Middle of the ocean street", "(555)555-5555",1,3000,"Uber"))
            attractionDao?.insert(Attraction(1,"Swim with Sharks1-2", 3000,4000,25,25,"Don't be eaten","23.5215215", "65.52353", "Middle of the ocean street", "(555)555-5555",1,3000,"Uber"))
            attractionDao?.insert(Attraction(1,"Swim with Sharks1-3", 3000,4000,25,25,"Don't be eaten","23.5215215", "65.52353", "Middle of the ocean street", "(555)555-5555",1,3000,"Uber"))
            attractionDao?.insert(Attraction(1,"Swim with Sharks1-4", 3000,4000,25,25,"Don't be eaten","23.5215215", "65.52353", "Middle of the ocean street", "(555)555-5555",1,3000,"Uber"))
        }
    }
}