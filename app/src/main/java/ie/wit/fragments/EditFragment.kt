package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_booking.view.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: BookingApp
    lateinit var loader: AlertDialog
    lateinit var root: View
    var editBooking: BookingModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BookingApp
        arguments?.let {
            editBooking = it.getParcelable("editbooking")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)
        info(editBooking!!.partyName)
        root.editpartyname.setText(editBooking!!.partyName)
        root.editbookedpartyamount.setText(editBooking!!.partyAmount.toString())
        root.editbookingContact.setText(editBooking!!.partyContact)
        root.edittextView15.setText(editBooking!!.bookingTime)

        root.updateBtn.setOnClickListener {
            showLoader(loader, "Updating Booking on Server...")
            updateBookingData()
            updateBooking(editBooking!!.uid, editBooking!!)
            updateUserBooking(
                app.auth.currentUser!!.uid,
                editBooking!!.uid, editBooking!!
            )
        }



        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(booking: BookingModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editbooking", booking)
                }
            }
    }


    fun updateBookingData() {
        editBooking!!.partyName = root.editpartyname.text.toString()
        editBooking!!.partyContact = root.editbookingContact.text.toString()
        editBooking!!.partyAmount = root.editbookedpartyamount.text.toString().toInt()
        editBooking!!.bookingTime = root.edittextView15.text.toString()
        val day: Int = root.editbookingDate.getDayOfMonth()
        val month: Int = root.editbookingDate.getMonth()
        val year: Int = root.editbookingDate.getYear()
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        val formatedDate = sdf.format(calendar.getTime())
        val date: Date = sdf.parse(formatedDate)
        val date1 = date.toString()

        editBooking!!.bookingDate = date1
    }

    fun updateUserBooking(userId: String, uid: String?, booking: BookingModel) {
        app.database.child("user-bookings").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(booking)
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.homeFrame, MyBookingsFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Booking error : ${error.message}")
                    }
                })
    }

    fun updateBooking(uid: String?, booking: BookingModel) {
        app.database.child("bookings").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(booking)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Booking error : ${error.message}")
                    }
                })
    }






}
