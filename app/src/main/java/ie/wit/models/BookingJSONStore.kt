package org.wit.recipebook.models


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import ie.wit.helpers.*
import ie.wit.models.BookingModel
import ie.wit.models.BookingStore
import java.nio.file.Files.write
import java.util.*

val JSON_FILE = "bookings.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<BookingModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class BookingJSONStore : BookingStore, AnkoLogger {

    val context: Context
    var bookings = mutableListOf<BookingModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BookingModel>{
        return bookings
    }

    override fun findById(id: Long): BookingModel? {
        val foundBooking: BookingModel? = bookings.find { it.id == id }
        return foundBooking
    }


    override fun create(booking: BookingModel) {
        booking.id = generateRandomId()
        bookings.add(booking)
        serialize()
    }



        override fun update(booking: BookingModel) {
        val bookingList = findAll() as ArrayList<BookingModel>
        var foundBooking: BookingModel? = bookingList.find { p -> p.id == booking.id }
        if (foundBooking != null) {
            foundBooking.partyName= booking.partyName
            foundBooking.partyContact = booking.partyContact
            foundBooking.partyAmount = booking.partyAmount
            foundBooking.bookingTime = booking.bookingTime
            foundBooking.bookingDate = booking.bookingDate

        }
        serialize()
    }
//
    override fun delete(booking: BookingModel) {
        bookings.remove(booking)
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(bookings, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        bookings = Gson().fromJson(jsonString, listType)
    }
}