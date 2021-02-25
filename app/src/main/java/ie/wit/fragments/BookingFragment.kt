package ie.wit.fragments

import android.os.Bundle
import android.os.ParcelFileDescriptor.open
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.wit.R
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import kotlinx.android.synthetic.main.fragment_booking.*
import kotlinx.android.synthetic.main.fragment_booking.view.*
import org.jetbrains.anko.toast
import java.io.IOException
import java.io.InputStream
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.DatagramChannel.open

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookingFragment : Fragment() {

    lateinit var app: BookingApp

    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as BookingApp
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_booking, container, false)
        activity?.title = getString(R.string.action_book)



        return root;
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            BookingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: View) {
        layout.bookButton.setOnClickListener {

            }
        }



      }


