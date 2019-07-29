package app.labs14.roamly.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.labs14.roamly.UserRepository
import app.labs14.roamly.models.User

class UserViewModel(application:Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allUsers: LiveData<List<User>> = repository.getAllUsers()

    fun insert(user: User) {
        repository.insert(user)
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun delete(user: User) {
        repository.delete(user)
    }

    fun deleteAllUsers() {
        repository.deleteAllUsers()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }
}