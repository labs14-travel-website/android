package app.labs14.roamly.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.UserRepository
import app.labs14.roamly.models.EventLocation
import app.labs14.roamly.models.User

class EventLocationViewModel (application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allEventLocation: LiveData<List<EventLocation>> = repository.getAllEventLocations()

    fun insert(eventLocation: EventLocation) {
        repository.insert(eventLocation)
    }

    fun update(eventLocation: EventLocation) {
        repository.update(eventLocation)
    }

    fun delete(eventLocation: EventLocation) {
        repository.delete(eventLocation)
    }

    fun deleteAllEventLocation() {
        repository.deleteAllEventLocations()
    }

    fun getAllEventLocation(): LiveData<List<EventLocation>> {
        return allEventLocation
    }
}