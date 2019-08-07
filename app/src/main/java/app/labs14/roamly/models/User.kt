package app.labs14.roamly.models


//shoon 2019/08/07
class User(
    var id:String,
    var name: String = "",
    var email: String = "",

    var itineraries: List<Itinerary> )