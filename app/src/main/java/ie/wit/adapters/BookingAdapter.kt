package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.card_booking.view.*

interface BookingListener {
    fun onBookingClick(booking : BookingModel)
}

class BookingAdapter constructor(var bookings: ArrayList<BookingModel>,
                      private val listener: BookingListener)
    : RecyclerView.Adapter<BookingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_booking,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val booking = bookings[holder.adapterPosition]
        holder.bind(booking,listener)

    }

    override fun getItemCount(): Int = bookings.size


    fun removeAt(position: Int) {
      bookings.removeAt(position)
        notifyItemRemoved(position)
    }


    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(booking: BookingModel, listener: BookingListener) {
            itemView.tag = booking
            itemView.bookedName.text = booking.partyName
            itemView.bookedContact.text=booking.partyContact
            itemView.bookedSize.text = booking.partyAmount.toString()
            itemView.bookedTime.text=booking.bookingTime
            itemView.bookedDate.text=booking.bookingDate
            itemView.setOnClickListener { listener.onBookingClick(booking) }
        }
    }
}