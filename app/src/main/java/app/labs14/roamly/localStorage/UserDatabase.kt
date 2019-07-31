package app.labs14.roamly.localStorage

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.User

@Database(entities =[Itinerary::class, Attraction::class], exportSchema = true,version = 18)
abstract class UserDatabase : RoomDatabase() {

    abstract fun itineraryDao(): ItineraryDao
    abstract fun attractionDao(): AttractionDao

    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
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

    //Mock Data here
    class PopulateDbAsyncTask(db: UserDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val itineraryDao = db?.itineraryDao()
        private val attractionDao = db?.attractionDao()

        override fun doInBackground(vararg p0: Unit?) {
            itineraryDao?.insert(Itinerary("Bali", "This place is rad"))
            itineraryDao?.insert(Itinerary("Vegas", "What happens here, stays here"))
            itineraryDao?.insert(Itinerary("Fuji", "Climb it!"))
            itineraryDao?.insert(Itinerary("San Francisco", "Brapp brapp!"))
            itineraryDao?.insert(Itinerary("Thailand", "Coconut beaches"))
            itineraryDao?.insert(Itinerary("Tunisia", "An olive branch"))
            itineraryDao?.insert(Itinerary("Ghana", "Colorful markets by the cities"))
            itineraryDao?.insert(Itinerary("Antarctica", "See a 24-hour day of sunlight"))
            itineraryDao?.insert(Itinerary("Patagonia", "Vast expanses of landscapes and beasts"))
            itineraryDao?.insert(Itinerary("Brooklyn", "Come walk in the gentrified inner city"))

            attractionDao?.insert(Attraction(1,"Ocean Snorkeling", 1564486657,1564496100,25,25,"Get close to seastars and manatees","-8.409518", "115.188919", "Bali, Indonesia", "(525) 264-1082",1,1564485950,"Airplane"))
            attractionDao?.insert(Attraction(1,"Jungle Treking", 1564486657,1564496100,25,25,"Get lost in the green","-8.409518", "115.188919", "Bali, Indonesia", "(406) 703-6279",1,1564485950,"Airplane"))
            attractionDao?.insert(Attraction(1,"Swimming with Sharks", 1564486657,1564496100,25,25,"Don't be eaten","-8.409518", "115.188919", "Bali, Indonesia", "(403) 214-5135",1,1564485950,"Airplane"))

            attractionDao?.insert(Attraction(2,"Live Music", 1564486657,1564496100,25,25,"Come for a famous star","36.114647", "-115.172813", "Washington", "(670) 480-9095",2,1564485950,"Walking"))
            attractionDao?.insert(Attraction(2,"Comedy Show", 1564486657,1564496100,25,25,"Laugh at a silly gag","36.114647", "-115.172813", "Washington", "(992) 420-0332",2,1564485950,"Walking"))
            attractionDao?.insert(Attraction(2,"Casino Gambling", 1564486657,1564496100,25,25,"Have fun tossing $ away","36.114647", "-115.172813", "Washington", "(849) 729-8349",2,1564485950,"Walking"))

            attractionDao?.insert(Attraction(3,"Downhill Skiing", 1564486657,1564496100,25,25,"Ski down the majestic mountain","35.360638", "138.729050", "Mount Fuji, Japan", "(663) 619-6104",3,1564485950,"Train"))
            attractionDao?.insert(Attraction(3,"Haunted Forest Tour", 1564486657,1564496100,25,25,"Explore the scary forest","35.360638", "138.729050", "Mount Fuji, Japan", "(888) 282-4410",3,1564485950,"Train"))
            attractionDao?.insert(Attraction(3,"Mountain Climbing", 1564486657,1564496100,25,25,"Take oxygen","35.360638", "138.729050", "Mount Fuji, Japan", "(950) 585-1665",3,1564485950,"Train"))

            attractionDao?.insert(Attraction(4,"Fisherman's Wharf", 1564486657,1564496100,25,25,"Hear the sea lions and eat seafood","37.773972", "-122.431297", "California", "(463) 939-1809",4,1564485950,"Bicycle"))
            attractionDao?.insert(Attraction(4,"Alcatraz Historical Tour", 1564486657,1564496100,25,25,"Explore the prison island","37.773972", "-122.431297", "California", "(611) 934-0394",4,1564485950,"Bicycle"))
            attractionDao?.insert(Attraction(4,"Golden Gate Ruins", 1564486657,1564496100,25,25,"Experience ancient times","37.773972", "-122.431297", "California", "(985) 744-4351",4,1564485950,"Bicycle"))

            attractionDao?.insert(Attraction(5,"Temple Hopping", 1564486657,1564496100,25,25,"See the exquisitely preserved temples","13.736717", "100.523186", "Thailand", "(477) 697-6290",5,1564485950,"Tuk-tuk"))
            attractionDao?.insert(Attraction(5,"Yoga Teacher Training", 1564486657,1564496100,25,25,"Join an advanced yoga seminar","13.736717", "100.523186", "Thailand", "(515) 696-1405",5,1564485950,"Tuk-tuk"))
            attractionDao?.insert(Attraction(5,"Tiger Petting", 1564486657,1564496100,25,25,"Which is the furriest?","13.736717", "100.523186", "Thailand", "(321) 633-3550",5,1564485950,"Tuk-tuk"))

            attractionDao?.insert(Attraction(6,"Olive Treks", 1564486657,1564496100,25,25,"Visit the olive groves","36.862499", "10.195556", "Tunisia, Africa", "(573) 602-8514",6,1564485950,"Ship"))
            attractionDao?.insert(Attraction(6,"Camel Racing", 1564486657,1564496100,25,25,"Who says camels can't run?","36.862499", "10.195556", "Tunisia, Africa", "(468) 933-2574",6,1564485950,"Ship"))
            attractionDao?.insert(Attraction(6,"Soldier Training", 1564486657,1564496100,25,25,"Become a soldier for a day","36.862499", "10.195556", "Tunisia, Africa", "(558) 353-3320",6,1564485950,"Ship"))

            attractionDao?.insert(Attraction(7,"Fruit Markets", 1564486657,1564496100,25,25,"Pick out some of the juiciest fruit","7.946527", "-1.023194", "Ghana, Africa", "(480) 378-0288",1,1564485950,"Airplane"))
            attractionDao?.insert(Attraction(7,"Rave Party", 1564486657,1564496100,25,25,"The sounds of the people","7.946527", "-1.023194", "Ghana, Africa", "(689) 880-4165",1,1564485950,"Airplane"))
            attractionDao?.insert(Attraction(7,"Hammock Massage", 1564486657,1564496100,25,25,"Get massaged by the beach on a hammock","7.946527", "-1.023194", "Ghana, Africa", "(672) 909-9102",1,1564485950,   "Airplane"))

            attractionDao?.insert(Attraction(8,"Igloo Construction", 1564486657,1564496100,25,25,"Build an ice shelter","-75.2509766", "-0.071389", "Antarctica", "(957) 825-3071",6,1564485950,"Ship"))
            attractionDao?.insert(Attraction(8,"Research and Measuring", 1564486657,1564496100,25,25,"Volunteer to gather data","-75.2509766", "-0.071389", "Antarctica", "(272) 859-5475",6,1564485950,"Ship"))
            attractionDao?.insert(Attraction(8,"Snowshoeing", 1564486657,1564496100,25,25,"Hike over the vast snowy desert","-75.2509766", "-0.071389", "Antarctica", "(402) 986-9213",6,1564485950,"Ship"))

            attractionDao?.insert(Attraction(9,"Wildlife Photography", 1564486657,1564496100,25,25,"Witness breathtaking scenery","48", "74", "Patagonia, Chile", "(734) 894-9467",3,1564485950,"Train"))
            attractionDao?.insert(Attraction(9,"Horseback Riding", 1564486657,1564496100,25,25,"Saddle up!","48", "74", "Patagonia, Chile", "(429) 858-7774",3,1564485950,"Train"))
            attractionDao?.insert(Attraction(9,"Paragliding", 1564486657,1564496100,25,25,"Fly through the clouds","48", "74", "Patagonia, Chile", "(813) 287-9376",3,1564485950,"Train"))

            attractionDao?.insert(Attraction(10,"DJ Jams and Rap Battle", 1564486657,1564496100,25,25,"Get pumped with great impromptu music","40.650002", "-73.949997", "Brooklyn, New York", "(702) 305-6673",7,1564485950,"Car"))
            attractionDao?.insert(Attraction(10,"Circuit of the Arts", 1564486657,1564496100,25,25,"Local artists showing","40.650002", "-73.949997", "Brooklyn, New York", "(400) 263-7329",7,1564485950,"Car"))
            attractionDao?.insert(Attraction(10,"Outdoor Basketball Game", 1564486657,1564496100,25,25,"Watch amazing feats of skill","40.650002", "-73.949997", "Brooklyn, New York", "(872) 886-7323",7,1564485950,"Car"))
        }
    }
}