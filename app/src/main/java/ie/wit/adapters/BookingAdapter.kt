package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.card_booking.view.*


class BookingAdapter constructor(private var bookings: List<BookingModel>)
    : RecyclerView.Adapter<BookingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_booking,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = bookings[holder.adapterPosition]
        holder.bind(book)
    }

    override fun getItemCount(): Int = bookings.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(booking: BookingModel) {
            itemView.bookedName.text = booking.partyName
            itemView.bookedSize.text = booking.partyAmount.toString()
            itemView.bookedTime.text=booking.bookingTime
            itemView.bookedDate.text=booking.bookingDate.toString()
        }
    }
}