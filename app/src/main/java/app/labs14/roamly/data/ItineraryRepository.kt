package app.labs14.roamly.data


import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import app.labs14.roamly.models.ItineraryModel


//shoon 2019/07/26
class ItineraryRepository(application: Application) {

    private var ItineraryDao: ItineraryDao

    private var allItineraries: LiveData<List<ItineraryModel>>

    init {
        val database: ItineraryDatabase = ItineraryDatabase.getInstance(
            application.applicationContext
        )!!
        ItineraryDao = database.itineraryDao()
        allItineraries = ItineraryDao.getAllItineraries()
    }

    fun insert(Itinerary: ItineraryModel) {
        val insertItineraryModelAsyncTask = InsertItineraryAsyncTask(ItineraryDao).execute(Itinerary)
    }

    fun update(Itinerary: ItineraryModel) {
        val updateItineraryModelAsyncTask = UpdateItineraryAsyncTask(ItineraryDao).execute(Itinerary)
    }


    fun delete(Itinerary: ItineraryModel) {
        val deleteItineraryModelAsyncTask = DeleteItineraryAsyncTask(ItineraryDao).execute(Itinerary)
    }

    fun deleteAllItineraries() {
        val deleteAllItineraryModelsAsyncTask = Companion.DeleteAllItinerarisAsyncTask(
            ItineraryDao
        ).execute()
    }

    fun getAllItineraries(): LiveData<List<ItineraryModel>> {
        return allItineraries
    }

    companion object {
        private class InsertItineraryAsyncTask(ItineraryDao: ItineraryDao) : AsyncTask<ItineraryModel, Unit, Unit>() {
            val ItineraryDao = ItineraryDao

            override fun doInBackground(vararg p0: ItineraryModel?) {
                ItineraryDao.insert(p0[0]!!)
            }
        }

        private class UpdateItineraryAsyncTask(ItineraryDao: ItineraryDao) : AsyncTask<ItineraryModel, Unit, Unit>() {
            val ItineraryDao = ItineraryDao

            override fun doInBackground(vararg p0: ItineraryModel?) {
                ItineraryDao.update(p0[0]!!)
            }
        }

        private class DeleteItineraryAsyncTask(ItineraryDao: ItineraryDao) : AsyncTask<ItineraryModel, Unit, Unit>() {
            val ItineraryDao = ItineraryDao

            override fun doInBackground(vararg p0: ItineraryModel?) {
                ItineraryDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllItinerarisAsyncTask(ItineraryDao: ItineraryDao) : AsyncTask<Unit, Unit, Unit>() {
            val ItineraryDao = ItineraryDao

            override fun doInBackground(vararg p0: Unit?) {
                ItineraryDao.deleteAllItineraries()
            }
        }
    }
}