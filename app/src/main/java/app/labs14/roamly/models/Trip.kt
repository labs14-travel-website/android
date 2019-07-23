package app.labs14.roamly.models

class Trip {
    val id = 0
    var name = ""
    var user = User()
    var activityEvents = mutableListOf<ActivityEvent>()
}