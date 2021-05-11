package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class  BookingModel(
    var uid: String? = "",
    var email: String? = "zaragunner@hotmail.com",
    var partyName: String = "",
    var partyContact: String = "",
    var partyAmount: Int = 0,
    var bookingTime: String = "",
    var bookingDate: String = "",
    var isfavourite: Boolean = false)
                : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "email" to email,
            "partyName" to partyName,
            "partyContact" to partyContact,
            "partyAmount" to partyAmount,
            "bookingTime" to bookingTime,
            "bookingDate" to bookingDate,
            "isfavourite" to isfavourite
        )
    }
}


