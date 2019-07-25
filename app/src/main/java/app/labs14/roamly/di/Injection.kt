package app.labs14.roamly.di

import app.labs14.roamly.model.MuseumDataSource
import app.labs14.roamly.model.MuseumRepository

object Injection {

    //MuseumRepository could be a singleton
    fun providerRepository(): MuseumDataSource {
        return MuseumRepository()
    }
}