package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.adapters.BookingAdapter
import ie.wit.adapters.BookingListener
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_mybookings.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class VipFragment : MyBookingsFragment(),
    BookingListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_mybookings, container, false)
        activity?.title = "VIP Bookings"

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            VipFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllVipBookings()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllVipBookings()
    }

    fun getAllVipBookings() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All VIP Bookings from Firebase")
        val bookingsList = ArrayList<BookingModel>()
        app.database.child("bookings")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Booking error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val booking = it.getValue<BookingModel>(BookingModel::class.java)
                        if (booking?.isfavourite!!.equals(true)) {
                            bookingsList.add(booking!!)
                            root.recyclerView.adapter =
                                BookingAdapter(bookingsList, this@VipFragment, listall = false)
                            root.recyclerView.adapter?.notifyDataSetChanged()
                            checkSwipeRefresh()

                            app.database.child("bookings").removeEventListener(this)
                        }
                    }
                }
            })
    }
}