package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.wit.models.BookingMemStore
import ie.wit.models.BookingModel
import ie.wit.models.BookingStore
import org.wit.recipebook.models.BookingJSONStore


class BookingApp : Application() {

    lateinit var bookingsStore : BookingStore
    lateinit var chosenBooking : BookingModel
    lateinit var googleSignInClient: GoogleSignInClient


    lateinit var auth : FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate() {

        super.onCreate()
        bookingsStore = BookingMemStore()
        chosenBooking = BookingModel()
       bookingsStore = BookingJSONStore(applicationContext)

        Log.v("Donate","Donation App started")
    }
}