package ie.wit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import ie.wit.R
import ie.wit.fragments.AboutUsFragment
import ie.wit.fragments.BookingFragment
import ie.wit.fragments.MyBookingsFragment
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_booking.*
import kotlinx.android.synthetic.main.home.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class Home : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
var arr = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        ft = supportFragmentManager.beginTransaction()

        val fragment = BookingFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()
        read_json()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_book -> navigateTo(BookingFragment.newInstance())
            R.id.nav_mybookings -> navigateTo(MyBookingsFragment.newInstance())
            R.id.nav_aboutus -> navigateTo(AboutUsFragment.newInstance())

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
         else
            super.onBackPressed()
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_book -> toast("You Selected Book")
            R.id.action_mybookings -> toast("You Selected MyBookings")
            R.id.action_about -> toast("You selected About Us ")


        }
        return super.onOptionsItemSelected(item)
    }

    fun read_json(){
        var json: String? = null
        try{
            val inputStream: InputStream = assets.open("restaurant.json")
            json = inputStream.bufferedReader().use{it.readText()}

             var jsonarr = JSONArray(json)

           for (i in 0..jsonarr.length()-1)
           {
               var jsonobj = jsonarr.getJSONObject(i)
               arr.add(jsonobj.getString("name"))
           }

            var adpt = ArrayAdapter(this, android.R.layout.simple_list_item_1,arr)
            json_list.adapter = adpt
        }
        catch (e : IOException)
        {


        }
    }
}



