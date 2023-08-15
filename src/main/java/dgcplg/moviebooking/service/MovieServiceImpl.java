package dgcplg.moviebooking.service;

import dgcplg.moviebooking.api.MovieApiDelegate;
import dgcplg.moviebooking.controller.MovieController;
import dgcplg.moviebooking.controller.dto.MovieListDTO;
import dgcplg.moviebooking.exception.CodeEnumToHttpStatusConverter;
import dgcplg.moviebooking.exception.ExceptionToCodeEnumConverter;
import dgcplg.moviebooking.model.Error;
import dgcplg.moviebooking.model.ErrorPayload;
import dgcplg.moviebooking.model.Payload;
import dgcplg.moviebooking.model.SearchPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieApiDelegate {

    private final MovieController movieController;
    private final ExceptionToCodeEnumConverter exc2Code;
    private final CodeEnumToHttpStatusConverter code2Http;
    private final DateTimeFormatter formatter;

    @Autowired
    public MovieServiceImpl(MovieController movieController, ExceptionToCodeEnumConverter exc2Code, CodeEnumToHttpStatusConverter code2Http, DateTimeFormatter formatter) {
        this.movieController = movieController;
        this.exc2Code = exc2Code;
        this.code2Http = code2Http;
        this.formatter = formatter;
    }

    @Override
    public ResponseEntity<Payload> getAllMovies(Integer offset) {
        try {
            MovieListDTO movieListDTO;
            if (offset == null || offset < 1) {
                movieListDTO = movieController.getAllMovies();
            } else {
                movieListDTO = movieController.getAllMovies(offset);
            }
            Payload movieList = new SearchPayload(
                    SearchPayload.StatusEnum.SUCCESS,
                    movieListDTO.getRecordCount(),
                    SearchPayload.TypeEnum.MOVIE,
                    movieListDTO.getMovieList());
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        } catch (RuntimeException e) {
            Error.CodeEnum errorCode = exc2Code.getCodeFor(e);
            Payload error = new ErrorPayload(
                    ErrorPayload.StatusEnum.ERROR,
                    1L,
                    ErrorPayload.TypeEnum.ERROR,
                    Collections.singletonList(new Error(errorCode, e.getMessage()))
            ).relationId(UUID.randomUUID().toString()).timestamp(OffsetDateTime.now().format(this.formatter));
            return new ResponseEntity<>(error, code2Http.getHttpFor(errorCode));
        }
    }
}