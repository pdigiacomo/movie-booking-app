package dgcplg.moviebooking.controller.impl;

import dgcplg.moviebooking.controller.BookingController;
import dgcplg.moviebooking.controller.dto.BookingDTO;
import dgcplg.moviebooking.repository.BookingRepository;
import dgcplg.moviebooking.repository.MovieRepository;
import dgcplg.moviebooking.repository.UserRepository;
import dgcplg.moviebooking.repository.entity.BookingEntity;
import dgcplg.moviebooking.repository.entity.MovieEntity;
import dgcplg.moviebooking.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class BookingControllerImpl implements BookingController {
    private final BookingRepository bookingRepository;

    private final MovieRepository movieRepository;

    private final UserRepository userRepository;

    @Autowired
    public BookingControllerImpl(BookingRepository bookingRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDTO bookMovieSeats(long movieId, long userId, int nSeatsBooked) {
        MovieEntity bookingMovie = movieRepository.getReferenceById(movieId);
        UserEntity bookingUser = userRepository.getReferenceById(userId);
        BookingEntity newBooking = new BookingEntity();
        newBooking.setMovie(bookingMovie);
        newBooking.setUser(bookingUser);
        newBooking.setnSeatsBooked(nSeatsBooked);
        BookingEntity persistedBooking = bookingRepository.save(newBooking);
        bookingMovie.setAvailableSeats(bookingMovie.getAvailableSeats() - nSeatsBooked);
        movieRepository.save(bookingMovie);
        return new BookingDTO(
                persistedBooking.getBookingId(),
                persistedBooking.getMovie().getMovieId(),
                persistedBooking.getUser().getUserId(),
                persistedBooking.getnSeatsBooked()
        );
    }

    @Override
    public void cancelBooking(long bookingId) {
        Optional<BookingEntity> cancelBookingOpt = bookingRepository.findById(bookingId);
        BookingEntity cancelBooking = cancelBookingOpt.get();
        MovieEntity cancelBookingMovie = cancelBooking.getMovie();
        cancelBookingMovie.setAvailableSeats(cancelBookingMovie.getAvailableSeats() + cancelBooking.getnSeatsBooked());
        movieRepository.save(cancelBookingMovie);
        bookingRepository.delete(cancelBooking);
    }
}
