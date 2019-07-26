package app.labs14.roamly.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.labs14.roamly.models.ItineraryModel


//shoon 2019/07/26
@Database(entities = [ItineraryModel::class], version = 1)
abstract class ItineraryDatabase : RoomDatabase() {

    abstract fun itineraryDao(): ItineraryDao


    companion object {
        private var instance: ItineraryDatabase? = null

        fun getInstance(context: Context): ItineraryDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItineraryDatabase::class.java, "itinerary_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ItineraryDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val itineraryDao = db?.itineraryDao()

        override fun doInBackground(vararg p0: Unit?) {
            itineraryDao?.insert(ItineraryModel("description 1"))
            itineraryDao?.insert(ItineraryModel("description 2"))
            itineraryDao?.insert(ItineraryModel( "description 3"))
        }
    }

}