package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class  BookingModel(var id: Long = 0,
                         var partyName: String = "",
                         var partyContact: String = "",
                         var partyAmount: Int = 0,
                         var bookingTime: String = "",
                         var bookingDate: String = ""

) : Parcelable
