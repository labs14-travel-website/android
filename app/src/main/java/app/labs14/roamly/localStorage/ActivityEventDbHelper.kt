package app.labs14.roamly.localStorage

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ActivityEventDbHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : this(context) {}

    constructor(
        context: Context,
        name: String,
        factory: SQLiteDatabase.CursorFactory,
        version: Int,
        errorHandler: DatabaseErrorHandler
    ) : this(context)

    constructor(context: Context, name: String, version: Int, openParams: SQLiteDatabase.OpenParams) : this(context) {}

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ActivityEventDBContract.ActivityEventEntry.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(ActivityEventDBContract.ActivityEventEntry.SQL_DELETE_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "roamly_local.db"
    }
}