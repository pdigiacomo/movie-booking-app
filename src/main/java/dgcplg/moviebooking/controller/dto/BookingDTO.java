package dgcplg.moviebooking.controller.dto;

public class BookingDTO {
    private long bookingId;
    private long movieId;
    private long userId;
    private int nSeatsBooked;

    public BookingDTO(long bookingId, long movieId, long userId, int nSeatsBooked) {
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.userId = userId;
        this.nSeatsBooked = nSeatsBooked;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getnSeatsBooked() {
        return nSeatsBooked;
    }

    public void setnSeatsBooked(int nSeatsBooked) {
        this.nSeatsBooked = nSeatsBooked;
    }
}
