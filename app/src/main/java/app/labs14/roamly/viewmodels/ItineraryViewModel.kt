package app.labs14.roamly.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.data.ItineraryRepository
import app.labs14.roamly.data.NoteRepository
import app.labs14.roamly.models.ItineraryModel
import app.labs14.roamly.models.Note


//shoon 2019/07/26
class ItineraryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ItineraryRepository =
        ItineraryRepository(application)
    private var allItineraries: LiveData<List<ItineraryModel>> = repository.getAllItineraries()

    fun insert(note: ItineraryModel) {
        repository.insert(note)
    }

    fun update(note: ItineraryModel) {
        repository.update(note)
    }

    fun delete(note: ItineraryModel) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllItineraries()
    }

    fun getAllItineraries(): LiveData<List<ItineraryModel>> {
        return allItineraries
    }
}