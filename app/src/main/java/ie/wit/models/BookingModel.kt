package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.util.*

@Parcelize
data class  BookingModel(var id: Long = 0,
                        val partyName: String,
                         val partyContact: String,
                        val partyAmount: Int,
                         val bookingTime: String,
                         val bookingDate: Date

) : Parcelable

