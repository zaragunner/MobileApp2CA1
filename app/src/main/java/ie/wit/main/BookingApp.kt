package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.BookingMemStore
import ie.wit.models.BookingStore

class BookingApp : Application() {

    lateinit var bookingsStore: BookingStore

    override fun onCreate() {
        super.onCreate()
        bookingsStore = BookingMemStore()
        Log.v("Donate","Donation App started")
    }
}