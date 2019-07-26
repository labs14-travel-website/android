package app.labs14.roamly.models


import androidx.room.Entity
import androidx.room.PrimaryKey

//shoon 2019/07/26
@Entity(tableName = "address_table")
class AddressModel(
    var countryID:Long,
    var zipcode:String,
    var state:String,
    var city:String,
    var address:String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long= 0
}

