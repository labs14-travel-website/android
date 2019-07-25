package app.labs14.roamly.model

import app.labs14.roamly.OperationCallback


interface MuseumDataSource {

    fun retrieveMuseums(callback: OperationCallback)
    fun cancel()
}