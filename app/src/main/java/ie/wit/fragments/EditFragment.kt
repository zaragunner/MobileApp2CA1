package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.fragment_edit.view.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {

    lateinit var app: BookingApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BookingApp
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var root =  inflater.inflate(R.layout.fragment_edit, container, false)
        root.editpartyname.setText(app.chosenBooking.partyName)
        root.editbookedpartyamount.setText(app.chosenBooking.partyAmount.toString())
        root.editbookingContact.setText(app.chosenBooking.partyContact)
        root.edittextView15.setText(app.chosenBooking.bookingTime)


//        val bookedDate = app.chosenBooking.bookingDate
//        val date = LocalDate.parse(bookedDate, DateTimeFormatter.ISO_DATE)

//        root.editbookingDate.setValue(app.chosenBooking.bookingDate)
        setDeleteButtonListener(root)
        setUpdateButtonListener(root)


        return root
    }


    fun setDeleteButtonListener(layout: View) {
        layout.deleteBtn.setOnClickListener {
            var booking = app.chosenBooking


            app.bookingsStore.delete(booking)

            val fragment = BookingListener.MyBookingsFragment()
            val ft = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.homeFrame, fragment);
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }





    fun setUpdateButtonListener(layout : View){
        layout.updateBtn.setOnClickListener {
            var booking = app.chosenBooking

            booking.partyName = layout.editpartyname.text.toString()
            booking.partyContact = layout.editbookingContact.text.toString()
            booking.partyAmount = parseInt(layout.editbookedpartyamount.text.toString())
            booking.bookingTime = layout.edittextView15.text.toString()

            val day: Int = layout.editbookingDate.getDayOfMonth()
            val month: Int = layout.editbookingDate.getMonth()
            val year: Int = layout.editbookingDate.getYear()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat("dd-MM-yyyy")

            val formatedDate = sdf.format(calendar.getTime())
            val date: Date = sdf.parse(formatedDate)
            val date1 = date.toString()

            booking.bookingDate = date1

            if (layout.editbookingContact.text.isEmpty() ||
                layout.editpartyname.text.isEmpty() ||
                layout.editbookedpartyamount.text.isEmpty() ||
                layout.edittextView15.text.isEmpty()
            ) {
                Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT)
                    .show();
            } else {
                app.bookingsStore.update(
                    BookingModel(
                        partyName = booking.partyName,
                        partyAmount = booking.partyAmount,
                        partyContact = booking.partyContact,
                        bookingDate = booking.bookingDate,
                        bookingTime = booking.bookingTime
                    )
                )
                val fragment = BookingListener.MyBookingsFragment()
                val ft = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.homeFrame, fragment);
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()


            }

        }
    }


}
