package dgcplg.moviebooking.controller;

import dgcplg.moviebooking.controller.dto.BookingDTO;

public interface BookingController {
    BookingDTO bookMovieSeats(long movieId, long userId, int nSeatsBooked);
    void cancelBooking(long bookingId);
}
