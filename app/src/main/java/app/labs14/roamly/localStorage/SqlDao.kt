package app.labs14.roamly.localStorage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import app.labs14.roamly.models.Trip

import java.util.ArrayList

class SqlDao(context: Context) {

    val allTrips: List<Trip>
        get() {
            val cursor = db.rawQuery(
                "SELECT * FROM " + DbContract.TripEntry.TABLE_NAME,
                arrayOf()
            )

            val rows = ArrayList<Trip>()
            while (cursor.moveToNext()) {
                rows.add(getTrip(cursor))
            }

            cursor.close()
            return rows
        }

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun updateTrip(trip: Trip) {
        val affectedRows = db.update(
            DbContract.TripEntry.TABLE_NAME,
            getContentValues(trip),
            DbContract.TripEntry._ID + "=?",
            arrayOf(trip.id.toString())
        )
    }

    fun deleteTrip(trip: Trip) {
        val affectedRows = db.delete(
            DbContract.TripEntry.TABLE_NAME,
            DbContract.TripEntry._ID + "=?",
            arrayOf(trip.id.toString())
        )
    }

    private fun getTrip(cursor: Cursor): Trip {
        var index: Int = cursor.getColumnIndexOrThrow(DbContract.TripEntry._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(DbContract.TripEntry.COLUMN_NAME_IS_READ)
        val isRead = cursor.getInt(index)

        return Trip()
    }

    companion object {
        private lateinit var db: SQLiteDatabase

        fun addTrip(trip: Trip) {
            val values = getContentValues(trip)

            val insert = db.insert(DbContract.TripEntry.TABLE_NAME, null, values)
        }

        private fun getContentValues(trip: Trip): ContentValues {
            val values = ContentValues()

            values.put(DbContract.TripEntry._ID, trip.id)
            return values
        }
    }
}