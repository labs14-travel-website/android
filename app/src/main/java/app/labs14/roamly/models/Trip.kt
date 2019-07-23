package app.labs14.roamly.models

class Trip {
    val id = 0
    var name = ""
    var userId = 0
    var location =  ""
    var activityEvents = mutableListOf<ActivityEvent>()
}