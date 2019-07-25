package app.labs14.roamly.models

//Brandon Lively - 07/25/2019

class ActivityEvent {
    var id          = 0
    var name        = ""
    var startTime   : Long = 0
    var endTime     : Long = 0
    var alarmBefore : Long = 0
    var alarmAfter  : Long = 0
    var startLat    : Double = 0.0
    var startLng    : Double = 0.0
    var endLat      : Double = 0.0
    var endLng      : Double = 0.0
    var address     = ""
    var phoneNum    = ""
    var description = ""

    var transportType   = 0
    var transportEta    : Long = 0
    var transportLabel  = ""

    constructor(
        id:                  Int,
        name:                String,
        startTime:           Long,
        endTime:             Long,
        alarmBefore:         Long,
        alarmAfter:          Long,
        startLat:            Double,
        startLng:            Double,
        endLat:              Double,
        endLng:              Double,
        address:             String,
        phoneNum:            String,
        description:         String,
        transportationType:  Int,
        transportationEta:   Long,
        transportationLabel: String
    ) {
        this.id             = id
        this.name           = name
        this.startTime      = startTime
        this.endTime        = endTime
        this.alarmBefore    = alarmBefore
        this.alarmAfter     = alarmAfter
        this.startLat       = startLat
        this.startLng       = startLng
        this.endLat         = endLat
        this.endLng         = endLng
        this.address        = address
        this.phoneNum       = phoneNum
        this.description    = description
        this.transportType  = transportationType
        this.transportEta   = transportationEta
        this.transportLabel = transportationLabel
    }

    constructor(name: String, startTime: Long, endTime: Long, startLat: Double, startLng: Double) {
        this.startTime = startTime
        this.endTime   = endTime
        this.startLat  = startLat
        this.startLng  = startLng
        this.name      = name
    }


}
