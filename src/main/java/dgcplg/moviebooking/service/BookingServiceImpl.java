package dgcplg.moviebooking.service;

import dgcplg.moviebooking.api.BookingApiDelegate;
import dgcplg.moviebooking.controller.BookingController;
import dgcplg.moviebooking.controller.dto.BookingDTO;
import dgcplg.moviebooking.model.Booking;
import dgcplg.moviebooking.model.BookingResponse;
import dgcplg.moviebooking.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingApiDelegate {

    private final BookingController bookingController;

    @Autowired
    public BookingServiceImpl(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    @Override
    public ResponseEntity<BookingResponse> bookMovieSeats(Booking booking) {
        BookingDTO bookingDTO = bookingController.bookMovieSeats(
                booking.getMovieId(), booking.getUserId(), booking.getnSeatsBooked());
        DateTimeFormatter tsFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        BookingResponse bookingResponse = new BookingResponse(
                new Metadata()
                        .timestamp(OffsetDateTime.now().format(tsFormatter))
                        .relationId(UUID.randomUUID().toString()),
                new Booking(
                        bookingDTO.getMovieId(),
                        bookingDTO.getUserId(),
                        bookingDTO.getnSeatsBooked()
                ).bookingId(bookingDTO.getBookingId())
        );
        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> cancelBooking(Long bookingId) {
        bookingController.cancelBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}