package ie.wit.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.BookingAdapter
import ie.wit.main.BookingApp
//import ie.wit.models.BookingMemStore
import ie.wit.models.BookingModel
import ie.wit.utils.SwipeToDeleteCallback
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.card_booking.*
import kotlinx.android.synthetic.main.fragment_mybookings.view.*
import kotlinx.android.synthetic.main.fragment_booking.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


interface BookingListener {
    fun onBookingClick(booking: BookingModel)

    class MyBookingsFragment : Fragment(), BookingListener, AnkoLogger {
        lateinit var app: BookingApp
        lateinit var loader : androidx.appcompat.app.AlertDialog
        lateinit var root: View


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            app = activity?.application as BookingApp
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            root = inflater.inflate(R.layout.fragment_mybookings, container, false)
            activity?.title = "My Bookings"
            root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
            setSwipeRefresh()


            val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = root.recyclerView.adapter as BookingAdapter
                    adapter.removeAt(viewHolder.adapterPosition)
                    deleteBooking((viewHolder.itemView.tag as BookingModel).uid)
                    deleteUserBooking(app.auth.currentUser!!.uid,
                        (viewHolder.itemView.tag as BookingModel).uid)
                }
            }
            val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
            itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)


            return root
        }
        fun setSwipeRefresh() {
            root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    root.swiperefresh.isRefreshing = true
                    getAllBookings(app.auth.currentUser!!.uid)
                }
            })
        }
        fun checkSwipeRefresh() {
            if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
        }


        fun deleteUserBooking(userId: String, uid: String?) {
            app.database.child("user-bookings").child(userId).child(uid!!)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.removeValue()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            info("Firebase Booking error : ${error.message}")
                        }
                    })
        }

        fun deleteBooking(uid: String?) {
            app.database.child("bookings").child(uid!!)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.ref.removeValue()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            info("Firebase Booking error : ${error.message}")
                        }
                    })
        }
        companion object {
            @JvmStatic
            fun newInstance() =
                MyBookingsFragment().apply {
                    arguments = Bundle().apply { }
                }


        }

        override fun onResume() {
            super.onResume()
            getAllBookings(app.auth.currentUser!!.uid)
        }

        override fun onBookingClick(booking: BookingModel) {
            TODO("Not yet implemented")
        }


        fun getAllBookings(userId: String?) {
            loader = createLoader(activity!!)
            showLoader(loader, "Downloading Bookings from Firebase")
            var bookingsList = ArrayList<BookingModel>()
            app.database.child("user-bookings").child(userId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Booking error : ${error.message}")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot!!.children
                        children.forEach {
                            val booking = it.getValue<BookingModel>(BookingModel::class.java!!)

                            bookingsList.add(booking!!)
                            root.recyclerView.adapter =
                                BookingAdapter(bookingsList, this@MyBookingsFragment)
                            root.recyclerView.adapter?.notifyDataSetChanged()
                            checkSwipeRefresh()
                            hideLoader(loader)
                            app.database.child("user-bookings").child(userId!!)
                                .removeEventListener(this)
                        }
                    }
                })
        }
    }
}



//        fun setSortBtnListener(layout: View) {
//            var sortedList = ArrayList<BookingModel>()
//            var bookings = BookingMemStore()
//            layout.sortBtn.setOnClickListener{
//                for (each in app.bookingsStore.findAll()){
//                    for (i in app.bookingsStore.findAll()){
//                    var id = bookings.bookings.get(i).id
//                    var foundBooking = app.bookingsStore.findById(id)
//                    if (foundBooking != null ){
//                        if(foundBooking.bookingDate > bookings.bookingDate)
//                        sortedList.add(foundBooking)
//
//                    }
//                    println(foundBooking)
//                    println(bookings)
//
//
//                }

//                sortedBy { date.toString()}
