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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookingFragment : Fragment() {

    lateinit var app: BookingApp


    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as BookingApp
        super.onCreate(savedInstanceState)

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
            val partyName = layout.partyname.text.toString()
            val partyContact = layout.bookingContact.text.toString()
            val partySize = parseInt(layout.bookedpartyamount.text.toString())
            val bookingTime = layout.textView15.text.toString()
            val day: Int = layout.bookingDate.getDayOfMonth()
            val month: Int =layout.bookingDate.getMonth()
            val year: Int = layout.bookingDate.getYear()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat("dd-MM-yyyy")

            val formatedDate = sdf.format(calendar.getTime())
            val date: Date = sdf.parse(formatedDate)


                app.bookingsStore.create(BookingModel(partyName= partyName , partyAmount = partySize,partyContact = partyContact, bookingDate = date, bookingTime= bookingTime))
            print(partyName)
        }
        }
    }

