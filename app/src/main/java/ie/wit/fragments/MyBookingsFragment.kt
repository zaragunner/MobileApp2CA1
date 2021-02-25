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
import kotlinx.android.synthetic.main.fragment_mybookings.view.*
import kotlinx.android.synthetic.main.fragment_booking.view.*


class MyBookingsFragment : Fragment() {
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
        root.recyclerView.adapter = BookingAdapter(app.donationsStore.findAll())

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyBookingsFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}