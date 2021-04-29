package ie.wit.api

import com.google.gson.GsonBuilder
import ie.wit.main.BookingApp
import ie.wit.models.BookingModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface BookingService {
    @GET("/bookings")
    fun getall(): Call<List<BookingModel>>

    @GET("/bookings/{id}")
    fun get(@Path("id") id: String): Call<BookingModel>

    @DELETE("/bookings/{id}")
    fun delete(@Path("id") id: String): Call<BookingWrapper>

    @POST("/bookings")
    //@Headers("Content-Type: application/json")
    fun post(@Body booking: BookingModel
        /*,@Header("Authorization") token : String*/)
            : Call<BookingWrapper>

    @PUT("/bookings/{id}")
    fun put(@Path("id") id: String,
            @Body booking: BookingModel
    ): Call<BookingWrapper>

    companion object {

        val serviceURL = "https://donationweb-hdip-server.herokuapp.com"

        fun create() : BookingService {

            val gson = GsonBuilder().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(BookingService::class.java)
        }
    }
}