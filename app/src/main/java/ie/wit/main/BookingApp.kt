package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.BookingMemStore
import ie.wit.models.BookingStore

class BookingApp : Application() {

    lateinit var donationsStore: BookingStore

    override fun onCreate() {
        super.onCreate()
        donationsStore = BookingMemStore()
        Log.v("Donate","Donation App started")
    }
}