package app.labs14.roamly.data

import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Itinerary
import org.json.JSONObject
import kotlin.concurrent.thread

class RoamlyDao {
    private val ROAMLY_URL = "https://roamly-staging.herokuapp.com/gql"

    private lateinit var allRoamlyItineraries: List<Itinerary>
    private lateinit var allRoamlyAttractions: List<Attraction>


    init {
        fun getUser(){
            //TODO: set AllRoamlyItineraries and Attractions
        }
    }

    fun insert(attraction: Attraction){
        /*
        * {user(id:"3572385235235"){
        * itineraries(id:"attraction.itin_id"){
        * id : "attraction.attraction_id",
        * lat : "attraction.lat",
        * lng : "attraction.lng",
        * name : "attraction.name"
        * }}}
        *
        *
        * */
    }

    //fun update(attraction: Attraction){}

    //fun delete(attraction: Attraction){}

    //fun deleteAllAttractions(){}

    //fun getAllAttractions(): List<Attraction>{}

    //fun getAttractions(id: Int): List<Attraction>{}
}
