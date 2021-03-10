package ie.wit.models;

interface BookingStore {
    fun findAll() : List<BookingModel>
    fun findById(id: Long) : BookingModel?
    fun create(booking: BookingModel)
    fun update(bookingModel: BookingModel)
    fun delete(booking: BookingModel)
}