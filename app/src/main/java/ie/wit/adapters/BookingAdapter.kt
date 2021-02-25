package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.BookingModel


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
        val donation = bookings[holder.adapterPosition]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = bookings.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(booking: BookingModel) {
//            itemView.paymentamount.text = booking.amount.toString()
//            itemView.paymentmethod.text = booking.paymentmethod
//            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}