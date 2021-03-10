package ie.wit.models;

interface BookingStore {
    fun findAll() : List<BookingModel>
    fun findById(id: Long) : BookingModel?
    fun create(booking: BookingModel)
    //fun update(donation: DonationModel)
    fun delete(booking: BookingModel)
}