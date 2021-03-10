package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.fragment_booking.view.*
import java.lang.Integer.parseInt
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



class BookingFragment : Fragment() {

    lateinit var app: BookingApp
    var bookings = BookingModel()


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
        activity?.title = getString(R.string.action_book)



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
            val month: Int =layout.bookingDate.getMonth()
            val year: Int = layout.bookingDate.getYear()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat("dd-MM-yyyy")

            val formatedDate = sdf.format(calendar.getTime())
            val date: Date = sdf.parse(formatedDate)
            val date1 = date.toString()

            app.bookingsStore.create(BookingModel(partyName= bookings.partyName , partyAmount = bookings.partyAmount,partyContact = bookings.partyContact, bookingDate = date1, bookingTime=bookings.bookingTime))
        }
        }
    }

