package app.labs14.roamly

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import app.labs14.roamly.localStorage.*
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.EventLocation
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User

//Brandon Lively - 07/28/2019

class UserRepository(application: Application) {
    private var userDao:          UserDao
    private var tripDao:          TripDao
    private var eventLocationDao: EventLocationDao
    private var activityEventDao: ActivityEventDao

    private var allUsers:          LiveData<List<User>>
    private var allTrips:          LiveData<List<Trip>>
    private var allEventLocations: LiveData<List<EventLocation>>
    private var allActivityEvents: LiveData<List<ActivityEvent>>


    init {           Log.i("Test 123", "Mock Data Hit")

        val database: UserDatabase = UserDatabase.getInstance(
            application.applicationContext
        )!!
        userDao          = database.userDao()
        tripDao          = database.tripDao()
        eventLocationDao = database.eventLocationDao()
        activityEventDao = database.activityEventDao()

        allUsers          = userDao.getAllUsers()
        allTrips          = tripDao.getAllTrips()
        allEventLocations = eventLocationDao.getAllEventLocations()
        allActivityEvents = activityEventDao.getAllActivityEvents()
    }

    //Users

    fun insert(user: User) {
        val insertUserAsyncTask = InsertUserAsyncTask(userDao).execute(user)
    }

    fun update(user: User) {
        val updateUserAsyncTask = UpdateUserAsyncTask(userDao).execute(user)
    }


    fun delete(user: User) {
        val deleteUserAsyncTask = DeleteUserAsyncTask(userDao).execute(user)
    }

    fun deleteAllUsers() {
        val deleteAllUsersAsyncTask = DeleteAllUsersAsyncTask(
            userDao
        ).execute()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun getUser(id:Long): LiveData<User> {

        var currentUser = userDao.getUser(id)!!
        //TODO: add null check
        currentUser.value!!.trips = getTripsByUser(currentUser.value!!.user_id).value!!

        return currentUser
    }



    //Trips

    fun insert(trip: Trip) {
        val insertTripAsyncTask = InsertTripAsyncTask(tripDao).execute(trip)
    }

    fun update(trip: Trip) {
        val updateTripAsyncTask = UpdateTripAsyncTask(tripDao).execute(trip)
    }


    fun delete(trip: Trip) {
        val deleteTripAsyncTask = DeleteTripAsyncTask(tripDao).execute(trip)
    }

    fun deleteAllTrips() {
        val deleteAllTripsAsyncTask = DeleteAllTripsAsyncTask(
            tripDao
        ).execute()
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return allTrips
    }

    fun getTripsByUser(userId:Long): LiveData<List<Trip>> {
        var currentTrips = tripDao.getTrip(userId)

        currentTrips.value!!.forEach {
            it.activityEvents = getActivityEventsById(it.trip_id).value!!
        }

        return currentTrips
    }



    //EventLocation


    fun insert(eventLocation: EventLocation) {
        val insertEventLocationAsyncTask = InsertEventLocationAsyncTask(eventLocationDao).execute(eventLocation)
    }

    fun update(eventLocation: EventLocation) {
        val updateEventLocationAsyncTask = UpdateEventLocationAsyncTask(eventLocationDao).execute(eventLocation)
    }


    fun delete(eventLocation: EventLocation) {
        val deleteEventLocationAsyncTask = DeleteEventLocationAsyncTask(eventLocationDao).execute(eventLocation)
    }

    fun deleteAllEventLocations() {
        val deleteAllEventLocationsAsyncTask = DeleteAllEventLocationsAsyncTask(
            eventLocationDao
        ).execute()
    }

    fun getAllEventLocations(): LiveData<List<EventLocation>> {
        return allEventLocations
    }

    fun getEventLocationById(id:Long):LiveData<List<EventLocation>>{
        return eventLocationDao.getEventLocation(id)
    }



    //ActivityEvents


    fun insert(activityEvent: ActivityEvent) {
        val insertActivityEventAsyncTask = InsertActivityEventAsyncTask(activityEventDao).execute(activityEvent)
    }

    fun update(activityEvent: ActivityEvent) {
        val updateActivityEventAsyncTask = UpdateActivityEventAsyncTask(activityEventDao).execute(activityEvent)
    }


    fun delete(activityEvent: ActivityEvent) {
        val deleteActivityEventAsyncTask = DeleteActivityEventAsyncTask(activityEventDao).execute(activityEvent)
    }

    fun deleteAllActivityEvents() {
        val deleteAllActivityEventsAsyncTask = DeleteAllActivityEventsAsyncTask(
            activityEventDao
        ).execute()
    }

    fun getAllActivityEvents(): LiveData<List<ActivityEvent>> {
        return allActivityEvents
    }

    fun getActivityEventsById(id:Long): LiveData<List<ActivityEvent>> {
        var activityEvents = activityEventDao.getActivityEvents(id)

        activityEvents.value!!.forEach{
            it.location = eventLocationDao.getEventLocation(it.activity_event_id).value!![0]
        }

        return activityEvents
    }

    companion object {

        //Users

        private class InsertUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.insert(p0[0]!!)
            }
        }

        private class UpdateUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.update(p0[0]!!)
            }
        }

        private class DeleteUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllUsersAsyncTask(userDao: UserDao) : AsyncTask<Unit, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: Unit?) {
                userDao.deleteAllUsers()
            }
        }

        //Trips
        private class InsertTripAsyncTask(tripDao: TripDao) : AsyncTask<Trip, Unit, Unit>() {
            val tripDao = tripDao

            override fun doInBackground(vararg p0: Trip?) {
                tripDao.insert(p0[0]!!)
            }
        }

        private class UpdateTripAsyncTask(tripDao: TripDao) : AsyncTask<Trip, Unit, Unit>() {
            val tripDao = tripDao

            override fun doInBackground(vararg p0: Trip?) {
                tripDao.update(p0[0]!!)
            }
        }

        private class DeleteTripAsyncTask(tripDao: TripDao) : AsyncTask<Trip, Unit, Unit>() {
            val tripDao = tripDao

            override fun doInBackground(vararg p0: Trip?) {
                tripDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllTripsAsyncTask(tripDao: TripDao) : AsyncTask<Unit, Unit, Unit>() {
            val tripDao = tripDao

            override fun doInBackground(vararg p0: Unit?) {
                tripDao.deleteAllTrips()
            }
        }

        //EventLocation
        private class InsertEventLocationAsyncTask(eventLocationDao: EventLocationDao) : AsyncTask<EventLocation, Unit, Unit>() {
            val eventLocationDao = eventLocationDao

            override fun doInBackground(vararg p0: EventLocation?) {
                eventLocationDao.insert(p0[0]!!)
            }
        }

        private class UpdateEventLocationAsyncTask(eventLocationDao: EventLocationDao) : AsyncTask<EventLocation, Unit, Unit>() {
            val eventLocationDao = eventLocationDao

            override fun doInBackground(vararg p0: EventLocation?) {
                eventLocationDao.update(p0[0]!!)
            }
        }

        private class DeleteEventLocationAsyncTask(eventLocationDao: EventLocationDao) : AsyncTask<EventLocation, Unit, Unit>() {
            val eventLocationDao = eventLocationDao

            override fun doInBackground(vararg p0: EventLocation?) {
                eventLocationDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllEventLocationsAsyncTask(eventLocationDao: EventLocationDao) : AsyncTask<Unit, Unit, Unit>() {
            val eventLocationDao = eventLocationDao

            override fun doInBackground(vararg p0: Unit?) {
                eventLocationDao.deleteAllEventLocations()
            }
        }

        //ActivityEvent

        private class InsertActivityEventAsyncTask(activityEventDao: ActivityEventDao) : AsyncTask<ActivityEvent, Unit, Unit>() {
            val activityEventDao = activityEventDao

            override fun doInBackground(vararg p0: ActivityEvent?) {
                activityEventDao.insert(p0[0]!!)
            }
        }

        private class UpdateActivityEventAsyncTask(activityEventDao: ActivityEventDao) : AsyncTask<ActivityEvent, Unit, Unit>() {
            val activityEventDao = activityEventDao

            override fun doInBackground(vararg p0: ActivityEvent?) {
                activityEventDao.update(p0[0]!!)
            }
        }

        private class DeleteActivityEventAsyncTask(activityEventDao: ActivityEventDao) : AsyncTask<ActivityEvent, Unit, Unit>() {
            val activityEventDao = activityEventDao

            override fun doInBackground(vararg p0: ActivityEvent?) {
                activityEventDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllActivityEventsAsyncTask(activityEventDao: ActivityEventDao) : AsyncTask<Unit, Unit, Unit>() {
            val activityEventDao = activityEventDao

            override fun doInBackground(vararg p0: Unit?) {
                activityEventDao.deleteAllActivityEvents()
            }
        }
    }
}
