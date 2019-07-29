package app.labs14.roamly.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.UserRepository
import app.labs14.roamly.models.Trip
import app.labs14.roamly.models.User

class TripViewModel (application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allTrips: LiveData<List<Trip>> = repository.getAllTrips()

    fun insert(trip: Trip) {
        repository.insert(trip)
    }

    fun update(trip: Trip) {
        repository.update(trip)
    }

    fun delete(trip: Trip) {
        repository.delete(trip)
    }

    fun deleteAllTrips() {
        repository.deleteAllTrips()
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return allTrips
    }

    fun getTripsById(id:Long):LiveData<List<Trip>>{
        return repository.getTripsByUser(id)
    }
}