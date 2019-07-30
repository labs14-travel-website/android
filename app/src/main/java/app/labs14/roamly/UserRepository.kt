package app.labs14.roamly

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import app.labs14.roamly.localStorage.*
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.User

//Brandon Lively - 07/28/2019

class UserRepository(application: Application) {
    private var itineraryDao: ItineraryDao
    private var attractionDao: AttractionDao

    private var allItineraries: LiveData<List<Itinerary>>
    private var allAttractions: LiveData<List<Attraction>>


    init {
        val database: UserDatabase = UserDatabase.getInstance(
            application.applicationContext
        )!!
        itineraryDao = database.itineraryDao()
        attractionDao = database.attractionDao()

        allItineraries = itineraryDao.getAllItineraries()
        allAttractions = attractionDao.getAllAttractions()
    }


    //Itineraries

    fun insert(itinerary: Itinerary) {
        val insertItineraryAsyncTask = InsertItineraryAsyncTask(itineraryDao).execute(itinerary)
    }

    fun update(itinerary: Itinerary) {
        val updateItineraryAsyncTask = UpdateItineraryAsyncTask(itineraryDao).execute(itinerary)
    }


    fun delete(itinerary: Itinerary) {
        val deleteItineraryAsyncTask = DeleteItineraryAsyncTask(itineraryDao).execute(itinerary)
    }

    fun deleteAllItineraries() {
        val deleteAllItinerariesAsyncTask = DeleteAllItinerariesAsyncTask(
            itineraryDao
        ).execute()
    }

    fun getAllItineraries(): LiveData<List<Itinerary>> {
        return allItineraries
    }


    //Attractions


    fun insert(attraction: Attraction) {
        val insertAttractionAsyncTask = InsertAttractionAsyncTask(attractionDao).execute(attraction)
    }

    fun update(attraction: Attraction) {
        val updateAttractionAsyncTask = UpdateAttractionAsyncTask(attractionDao).execute(attraction)
    }


    fun delete(attraction: Attraction) {
        val deleteAttractionAsyncTask = DeleteAttractionAsyncTask(attractionDao).execute(attraction)
    }

    fun deleteAllAttractions() {
        val deleteAllAttractionsAsyncTask = DeleteAllAttractionsAsyncTask(
            attractionDao
        ).execute()
    }

    fun getAllAttractions(): LiveData<List<Attraction>> {
        return allAttractions
    }

    fun getAttractionsByItineraryId(id: Long): LiveData<List<Attraction>> {
        var activityEvents = attractionDao.getAttractions(id)
        return activityEvents
    }

    companion object {

        //Itineraries
        private class InsertItineraryAsyncTask(itineraryDao: ItineraryDao) : AsyncTask<Itinerary, Unit, Unit>() {
            val ItineraryDao = itineraryDao

            override fun doInBackground(vararg p0: Itinerary?) {
                ItineraryDao.insert(p0[0]!!)
            }
        }

        private class UpdateItineraryAsyncTask(itineraryDao: ItineraryDao) : AsyncTask<Itinerary, Unit, Unit>() {
            val ItineraryDao = itineraryDao

            override fun doInBackground(vararg p0: Itinerary?) {
                ItineraryDao.update(p0[0]!!)
            }
        }

        private class DeleteItineraryAsyncTask(itineraryDao: ItineraryDao) : AsyncTask<Itinerary, Unit, Unit>() {
            val ItineraryDao = itineraryDao

            override fun doInBackground(vararg p0: Itinerary?) {
                ItineraryDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllItinerariesAsyncTask(itineraryDao: ItineraryDao) : AsyncTask<Unit, Unit, Unit>() {
            val ItineraryDao = itineraryDao

            override fun doInBackground(vararg p0: Unit?) {
                ItineraryDao.deleteAllItineraries()
            }
        }

        //Attraction

        private class InsertAttractionAsyncTask(attractionDao: AttractionDao) : AsyncTask<Attraction, Unit, Unit>() {
            val activityEventDao = attractionDao

            override fun doInBackground(vararg p0: Attraction?) {
                activityEventDao.insert(p0[0]!!)
            }
        }

        private class UpdateAttractionAsyncTask(attractionDao: AttractionDao) : AsyncTask<Attraction, Unit, Unit>() {
            val activityEventDao = attractionDao

            override fun doInBackground(vararg p0: Attraction?) {
                activityEventDao.update(p0[0]!!)
            }
        }

        private class DeleteAttractionAsyncTask(attractionDao: AttractionDao) : AsyncTask<Attraction, Unit, Unit>() {
            val activityEventDao = attractionDao

            override fun doInBackground(vararg p0: Attraction?) {
                activityEventDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllAttractionsAsyncTask(attractionDao: AttractionDao) : AsyncTask<Unit, Unit, Unit>() {
            val activityEventDao = attractionDao

            override fun doInBackground(vararg p0: Unit?) {
                activityEventDao.deleteAllAttractions()
            }
        }
    }
}
