package ie.wit.main

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

import ie.wit.models.BookingModel
import ie.wit.models.BookingStore



class BookingApp : Application() {

    lateinit var bookingsStore : BookingStore
    lateinit var chosenBooking : BookingModel
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference
    lateinit var userImage: Uri
    lateinit var auth : FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate() {

        super.onCreate()
      
        chosenBooking = BookingModel()

        Log.v("Donate","Donation App started")
    }
}