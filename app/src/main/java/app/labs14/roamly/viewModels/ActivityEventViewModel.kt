package app.labs14.roamly.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.UserRepository
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.User

class ActivityEventViewModel(application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allActivityEvent: LiveData<List<ActivityEvent>> = repository.getAllActivityEvents()

    fun insert(activityEvent: ActivityEvent) {
        repository.insert(activityEvent)
    }

    fun update(activityEvent: ActivityEvent) {
        repository.update(activityEvent)
    }

    fun delete(activityEvent: ActivityEvent) {
        repository.delete(activityEvent)
    }

    fun deleteAllActivityEvent() {
        repository.deleteAllActivityEvents()
    }

    fun getAllActivityEvent(): LiveData<List<ActivityEvent>> {
        return allActivityEvent
    }

    fun getActivityEventByTripId(id:Long):LiveData<List<ActivityEvent>>{
        return repository.getActivityEventsById(id)
    }
}