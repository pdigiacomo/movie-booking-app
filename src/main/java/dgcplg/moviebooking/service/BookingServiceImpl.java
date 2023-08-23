package dgcplg.moviebooking.service;

import dgcplg.moviebooking.api.BookingApiDelegate;
import dgcplg.moviebooking.controller.BookingController;
import dgcplg.moviebooking.controller.dto.BookingDTO;
import dgcplg.moviebooking.exception.CodeEnumToHttpStatusConverter;
import dgcplg.moviebooking.exception.ExceptionToCodeEnumConverter;
import dgcplg.moviebooking.model.Error;
import dgcplg.moviebooking.model.*;
import dgcplg.moviebooking.model.Error.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingApiDelegate {
    private final BookingController bookingController;
    private final ExceptionToCodeEnumConverter exc2Code;
    private final CodeEnumToHttpStatusConverter code2Http;
    private final DateTimeFormatter formatter;

    @Autowired
    public BookingServiceImpl(BookingController bookingController, ExceptionToCodeEnumConverter exc2Code, CodeEnumToHttpStatusConverter code2Http, DateTimeFormatter formatter) {
        this.bookingController = bookingController;
        this.exc2Code = exc2Code;
        this.code2Http = code2Http;
        this.formatter = formatter;
    }

    @Override
    public ResponseEntity<Payload> bookMovieSeats(Booking booking) {
        BookingDTO bookingDTO;
        try {

            bookingDTO = bookingController.bookMovieSeats(
                    booking.getMovieId(), booking.getUserId(), booking.getnSeatsBooked());
            Payload bookingResponse = new LookupPayload(
                    new Metadata()
                            .timestamp(OffsetDateTime.now().format(this.formatter))
                            .relationId(UUID.randomUUID().toString()),
                    new Booking(
                            bookingDTO.getMovieId(),
                            bookingDTO.getUserId(),
                            bookingDTO.getnSeatsBooked()
                    ).bookingId(bookingDTO.getBookingId()));
            return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            CodeEnum errorCode = exc2Code.getCodeFor(e);
            Payload error = new ErrorPayload(
                    ErrorPayload.StatusEnum.ERROR,
                    1L,
                    ErrorPayload.TypeEnum.ERROR,
                    Collections.singletonList(new Error(errorCode, e.getMessage()))
            ).relationId(UUID.randomUUID().toString()).timestamp(OffsetDateTime.now().format(this.formatter));
            return new ResponseEntity<>(error, code2Http.getHttpFor(errorCode));
        }
    }

    @Override
    public ResponseEntity<Payload> cancelBooking(Long bookingId) {
        try {
            bookingController.cancelBooking(bookingId);
        } catch (RuntimeException e) {
            CodeEnum errorCode = exc2Code.getCodeFor(e);
            Payload error = new ErrorPayload(
                    ErrorPayload.StatusEnum.ERROR,
                    1L,
                    ErrorPayload.TypeEnum.ERROR,
                    Collections.singletonList(new Error(errorCode, e.getMessage()))
            ).relationId(UUID.randomUUID().toString()).timestamp(OffsetDateTime.now().format(this.formatter));
            return new ResponseEntity<>(error, code2Http.getHttpFor(errorCode));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}