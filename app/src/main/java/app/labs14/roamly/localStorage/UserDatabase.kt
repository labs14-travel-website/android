package app.labs14.roamly.localStorage

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.EventLocation
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User
import app.labs14.roamly.viewModels.ActivityEventViewModel
import app.labs14.roamly.viewModels.EventLocationViewModel
import app.labs14.roamly.viewModels.TripViewModel
import app.labs14.roamly.viewModels.UserViewModel

@Database(entities = [User::class, EventLocation::class, Trip::class, ActivityEvent::class], exportSchema = true,version = 8)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun eventLocationDao(): EventLocationDao
    abstract fun tripDao(): TripDao
    abstract fun activityEventDao(): ActivityEventDao



    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            Log.i("Test 123", "user DB Hit")
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
        private val tripDao = db?.tripDao()
        private val activityEventDao = db?.activityEventDao()
        private val eventLocationDao = db?.eventLocationDao()

        override fun doInBackground(vararg p0: Unit?) {

            Log.i("Test 123", "Mock Data Hit")

            userDao?.insert(User("Test User1"))
            userDao?.insert(User("Test User2"))
            userDao?.insert(User("Test User3"))


            var users = userDao?.getAllUsers()
            var currentUser = users?.value!![0]

            tripDao?.insert(Trip(currentUser.user_id,"To Bali!"))
            var trips = tripDao?.getTrip(currentUser.user_id)

            var counter = 0

            trips?.value!!.forEach{ it ->
                counter++
                activityEventDao?.insert(ActivityEvent(it?.trip_id,"Event number $counter"))
                var activityEvent = activityEventDao?.getActivityEvents(it.trip_id)

                var aeCounter = 0

                activityEvent?.value!!.forEach {
                    aeCounter++
                    eventLocationDao?.insert(EventLocation(it.activity_event_id,1.4F,2.53F))
                    var eventLocation = eventLocationDao?.getEventLocation(it.activity_event_id)
                    it.location = eventLocation?.value!![0]
                }

                it.activityEvents = activityEvent.value!!
            }

            currentUser.trips = trips.value!!
        }
    }
}