package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.BookingMemStore
import ie.wit.models.BookingModel
import ie.wit.models.BookingStore
import org.wit.recipebook.models.BookingJSONStore

class BookingApp : Application() {

    lateinit var bookingsStore : BookingStore
    lateinit var chosenBooking : BookingModel


    override fun onCreate() {

        super.onCreate()
        bookingsStore = BookingMemStore()
        chosenBooking = BookingModel()
       bookingsStore = BookingJSONStore(applicationContext)

        Log.v("Donate","Donation App started")
    }
}