package ie.wit.fragments

import android.os.Bundle
import android.renderscript.Sampler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.BookingListener
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_booking.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*



class BookingFragment : Fragment(), AnkoLogger {

    lateinit var app: BookingApp
    var bookings = BookingModel()
    lateinit var loader: AlertDialog
    lateinit var eventListener: ValueEventListener
    var favourite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BookingApp


    }

    override fun onResume() {
        super.onResume()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_booking, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_book)
        setFavouriteListener(root)
        setButtonListener(root)
        return root;
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            BookingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: View) {
        layout.bookButton.setOnClickListener {

            bookings.partyName = layout.partyname.text.toString()
            bookings.partyContact = layout.bookingContact.text.toString()
            bookings.partyAmount = parseInt(layout.bookedpartyamount.text.toString())
            bookings.bookingTime = layout.textView15.text.toString()
            val day: Int = layout.bookingDate.getDayOfMonth()
            val month: Int = layout.bookingDate.getMonth()
            val year: Int = layout.bookingDate.getYear()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat("dd-MM-yyyy")

            val formatedDate = sdf.format(calendar.getTime())
            val date: Date = sdf.parse(formatedDate)
            val date1 = date.toString()

            if (layout.bookingContact.text.isEmpty() ||
                layout.partyname.text.isEmpty() ||
                layout.partyamount.text.isEmpty() ||
                layout.textView15.text.isEmpty()
            ) {
                Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT)
                    .show();
            } else {
//                app.bookingsStore.create(
//                    BookingModel(
//                        partyName = bookings.partyName,
//                        partyAmount = bookings.partyAmount,
//                        partyContact = bookings.partyContact,
//                        bookingDate = date1,
//                        bookingTime = bookings.bookingTime
//                    )
                writeNewBooking(
                    BookingModel(
                        partyName = bookings.partyName,
                        partyAmount = bookings.partyAmount,
                        partyContact = bookings.partyContact,
                        bookingDate = date1,
                        bookingTime = bookings.bookingTime,
                        email = app.auth.currentUser?.email,
                        isfavourite = favourite,
                        profilepic = app.userImage.toString(),

                        )

                )
            }
        }

    }


    fun writeNewBooking(booking: BookingModel) {
        // Create new booking at /bookings & /bookings/$uid
        showLoader(loader, "Adding Booking to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("bookings").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        booking.uid = key
        val bookingValues = booking.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/bookings/$key"] = bookingValues
        childUpdates["/user-bookings/$uid/$key"] = bookingValues
        info(booking)
        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

    fun setFavouriteListener(layout: View) {
        layout.imagefavourite.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (!favourite) {
                    layout.imagefavourite.setImageResource(android.R.drawable.star_big_on)
                    favourite = true
                } else {
                    layout.imagefavourite.setImageResource(android.R.drawable.star_big_off)
                    favourite = false
                }
            }
        })
    }
}

