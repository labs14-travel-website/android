package app.labs14.roamly.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.UserRepository
import app.labs14.roamly.models.Attraction

class AttractionViewModel(application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allAttraction: LiveData<List<Attraction>> = repository.getAllAttractions()

    fun insert(attraction: Attraction) {
        repository.insert(attraction)
    }

    fun update(attraction: Attraction) {
        repository.update(attraction)
    }

    fun delete(attraction: Attraction) {
        repository.delete(attraction)
    }

    fun deleteAllAttraction() {
        repository.deleteAllAttractions()
    }

    fun getAllAttraction(): LiveData<List<Attraction>> {
        return allAttraction
    }

    fun getAttractionById(id:Long):LiveData<List<Attraction>>{
        return repository.getAttractionsByItineraryId(id)
    }
}