package ie.wit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.BookingAdapter
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.card_booking.*
import kotlinx.android.synthetic.main.fragment_mybookings.view.*
import kotlinx.android.synthetic.main.fragment_booking.view.*


interface BookingListener {
    fun onBookingClick(booking: BookingModel)

    class MyBookingsFragment : Fragment(), BookingListener {
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
            var root = inflater.inflate(R.layout.fragment_mybookings, container, false)

            root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
            root.recyclerView.adapter = BookingAdapter(app.bookingsStore.findAll())

            { item ->

                app.chosenBooking = item
                val fragment = EditFragment()
                val ft = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.homeFrame, fragment);
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
            setSortBtnListener(root)

            return root
        }
        fun setSortBtnListener(layout: View) {
            layout.sortBtn.setOnClickListener{
                app.bookingsStore.findAll().sortedBy { date.toString() }

//                sortedBy { date.toString()}

            }
        }


        companion object {
            @JvmStatic
            fun newInstance() =
                MyBookingsFragment().apply {
                    arguments = Bundle().apply { }
                }


        }

        override fun onBookingClick(booking: BookingModel) {
            TODO("Not yet implemented")
        }

    }
}