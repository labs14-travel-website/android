package app.labs14.roamly.localStorage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import app.labs14.roamly.models.User

import java.util.ArrayList

class UserSqlDao(context: Context) {

    val allUsers: List<User>
        get() {
            val cursor = db.rawQuery(
                "SELECT * FROM " + UserDbContract.UserEntry.TABLE_NAME,
                arrayOf()
            )

            val rows = ArrayList<User>()
            while (cursor.moveToNext()) {
                rows.add(getUser(cursor))
            }

            cursor.close()
            return rows
        }

    init {
        val dbHelper = UserDbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun updateUser(user: User) {
        val affectedRows = db.update(
            UserDbContract.UserEntry.TABLE_NAME,
            getContentValues(user),
            UserDbContract.UserEntry._ID + "=?",
            arrayOf(user.id.toString())
        )
    }

    fun deleteUser(user: User) {
        val affectedRows = db.delete(
            UserDbContract.UserEntry.TABLE_NAME,
            UserDbContract.UserEntry._ID + "=?",
            arrayOf(user.id.toString())
        )
    }

    private fun getUser(cursor: Cursor): User {
        var index: Int = cursor.getColumnIndexOrThrow(UserDbContract.UserEntry._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(UserDbContract.UserEntry.COLUMN_NAME_IS_READ)
        val isRead = cursor.getInt(index)

        return User()
    }

    companion object {
        private lateinit var db: SQLiteDatabase

        fun addUser(user: User) {
            val values = getContentValues(user)

            val insert = db.insert(UserDbContract.UserEntry.TABLE_NAME, null, values)
        }

        private fun getContentValues(user: User): ContentValues {
            val values = ContentValues()

            values.put(UserDbContract.UserEntry._ID, user.id)
            return values
        }
    }
}