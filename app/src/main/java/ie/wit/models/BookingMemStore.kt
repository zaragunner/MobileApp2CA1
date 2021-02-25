package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BookingMemStore : BookingStore {

        val mybookings = ArrayList<BookingModel>()

        override fun findAll(): List<BookingModel> {
            return mybookings
        }

        override fun findById(id:Long) : BookingModel? {
            val foundBooking: BookingModel? = mybookings.find { it.id == id }
            return foundBooking
        }

        override fun create(booking: BookingModel) {
            booking.id = getId()
            mybookings.add(booking)
            logAll()
        }

        fun logAll() {
            Log.v("Booking","** My Booking List **")
            mybookings.forEach { Log.v("Booking","${it}") }
        }
    }
