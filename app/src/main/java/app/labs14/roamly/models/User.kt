package app.labs14.roamly.models

class User {
    var id = 0
    var name = ""
    var trips = mutableListOf<Trip>()

    constructor( name: String) {
        this.name = name
    }
    constructor(id:Int, name: String) {
        this.name = name
        this.id = id
    }
}