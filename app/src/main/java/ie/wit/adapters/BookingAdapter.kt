package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.BookingModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_booking.view.*


import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

interface BookingListener {
    fun onBookingClick(booking : BookingModel)
}

class BookingAdapter   constructor(var bookings: ArrayList<BookingModel>,
                      private val listener: BookingListener, listall : Boolean)
    : RecyclerView.Adapter<BookingAdapter.MainHolder>() , AnkoLogger {


    val listAll = listall

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
        holder.bind(booking,listener,listAll)

    }

    override fun getItemCount(): Int = bookings.size


    fun removeAt(position: Int) {
      bookings.removeAt(position)
        notifyItemRemoved(position)
    }


    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        fun bind(booking: BookingModel, listener: BookingListener, listAll: Boolean) {
            itemView.tag = booking
            itemView.bookedName.text = booking.partyName
            itemView.bookedContact.text = booking.partyContact
            itemView.bookedSize.text = booking.partyAmount.toString()
            itemView.bookedTime.text = booking.bookingTime
            itemView.bookedDate.text = booking.bookingDate
            if(booking.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)
            itemView.setOnClickListener { listener.onBookingClick(booking) }
            if (!listAll)
                itemView.setOnClickListener {
                    listener.onBookingClick(booking)
                }

            if(!booking.profilepic.isEmpty()) {
                Picasso.get().load(booking.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)
        }
        }
    }
