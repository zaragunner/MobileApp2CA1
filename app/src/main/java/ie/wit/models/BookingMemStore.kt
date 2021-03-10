package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BookingMemStore : BookingStore {

        val bookings = ArrayList<BookingModel>()

        override fun findAll(): List<BookingModel> {
            return bookings
        }

        override fun findById(id:Long) : BookingModel? {
            val foundBooking: BookingModel? = bookings.find { it.id == id }
            return foundBooking
        }

        override fun create(booking: BookingModel) {
            booking.id = getId()
            bookings.add(booking)
            logAll()
        }

    override fun delete(booking: BookingModel) {
        TODO("Not yet implemented")
    }

    fun logAll() {
            Log.v("Booking","** My Booking List **")
            bookings.forEach { Log.v("Booking","${it}") }
        }
    }
