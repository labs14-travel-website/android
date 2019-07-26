package app.labs14.roamly.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//shoon 2019/07/26
@Entity(tableName = "note_table")
data class Note(

    var title: String,

    var description: String,

    var priority: Int
) {

    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}