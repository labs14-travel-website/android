package app.labs14.roamly.model

class Trip {
    val id = 0
    var name = ""
    var user = User()
    var activityEvents = mutableListOf<ActivityEvent>()
}