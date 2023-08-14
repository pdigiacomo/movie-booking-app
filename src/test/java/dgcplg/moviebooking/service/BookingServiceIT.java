package dgcplg.moviebooking.service;

import dgcplg.moviebooking.OpenApiGeneratorApplication;
import dgcplg.moviebooking.model.Booking;
import dgcplg.moviebooking.model.BookingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * IT class for e2e-testing of the {@link dgcplg.moviebooking.service.BookingServiceImpl BookingServiceImpl} service class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OpenApiGeneratorApplication.class)
public class BookingServiceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void bookMovieSeats_ok() {
        Booking booking = new Booking(1L, 1L, 5);
        ResponseEntity<BookingResponse> bookingResponseEntity = testRestTemplate.postForEntity(
                "/v1/booking",
                booking,
                BookingResponse.class
        );
        assert bookingResponseEntity != null;
        assert bookingResponseEntity.getStatusCode().isSameCodeAs(HttpStatus.CREATED);
        assert bookingResponseEntity.hasBody();
        BookingResponse bookingResponse = bookingResponseEntity.getBody();
        assert bookingResponse != null;
        assert bookingResponse.getData() != null;
        Booking newBooking = bookingResponse.getData();
        assert newBooking.getBookingId() != null;
    }

    @Test
    public void cancelBooking_ok() {
        ResponseEntity<Void> cancellationResponse = testRestTemplate.exchange(
                "/v1/booking/1", HttpMethod.DELETE, HttpEntity.EMPTY,Void.class);
        assert cancellationResponse != null;
        assert ! cancellationResponse.hasBody();
        assert cancellationResponse.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT);
    }
}