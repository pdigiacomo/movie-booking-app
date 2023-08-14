package dgcplg.moviebooking.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name="booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Column(nullable = false, name = "n_seats_booked")
    private int nSeatsBooked;

    public BookingEntity() {}

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getnSeatsBooked() {
        return nSeatsBooked;
    }

    public void setnSeatsBooked(int nSeatsBooked) {
        this.nSeatsBooked = nSeatsBooked;
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "bookingId=" + bookingId +
                ", movie=" + movie +
                ", user=" + user +
                ", nSeatsBooked=" + nSeatsBooked +
                '}';
    }
}