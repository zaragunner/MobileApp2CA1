package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.util.*

@Parcelize
data class  BookingModel(var id: Long = 0,
                        val restaurantName : String,
                        val numberOfPeople: Int,
                        val date: Date,
                         val time: Time
) : Parcelable

