package app.labs14.roamly.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.data.UserRepository
import app.labs14.roamly.models.Alarm


class AlarmViewModel(application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =UserRepository(application)
    private var allAlarm: LiveData<List<Alarm>> = repository.getAllAlarms()

    fun insert(alarm: Alarm) {
        repository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        repository.update(alarm)
    }

    fun delete(alarm: Alarm) {
        repository.delete(alarm)
    }

    fun deleteAllAlarm() {
        repository.deleteAllAlarms()
    }

    fun getAllAlarm(): LiveData<List<Alarm>> {
        return allAlarm
    }

    fun getAlarmById(id:Int):LiveData<List<Alarm>>{
        return repository.getAlarmsByItineraryId(id)
    }
}