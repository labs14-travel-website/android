package app.labs14.roamly.localStorage.user;

import android.provider.BaseColumns;

public class UserDbContract {
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME                  = "users";
        public static final String COLUMN_NAME_NAME  = "name";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( " +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NAME + " TEXT" +
                "  );";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
