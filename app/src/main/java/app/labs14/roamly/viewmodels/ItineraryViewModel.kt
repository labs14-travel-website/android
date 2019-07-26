package app.labs14.roamly.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.data.ItineraryRepository
import app.labs14.roamly.data.NoteRepository
import app.labs14.roamly.models.Note


//shoon 2019/07/26
class ItineraryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ItineraryRepository =
        ItineraryRepository(application)
    private var allItineraries: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allItineraries
    }
}