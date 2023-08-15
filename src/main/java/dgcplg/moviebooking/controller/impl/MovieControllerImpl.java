package dgcplg.moviebooking.controller.impl;

import dgcplg.moviebooking.controller.MovieController;
import dgcplg.moviebooking.controller.dto.MovieListDTO;
import dgcplg.moviebooking.model.Movie;
import dgcplg.moviebooking.model.SearchPayloadDataInner;
import dgcplg.moviebooking.repository.MovieRepository;
import dgcplg.moviebooking.repository.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class MovieControllerImpl implements MovieController {
    private final MovieRepository movieRepository;

    @Value("${dgcplg.moviebooking.movie.recordlimit}")
    private int recordLimit;
    @Value("${dgcplg.moviebooking.movie.chunksize}")
    private int chunkSize;

    @Autowired
    public MovieControllerImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieListDTO getAllMovies() {
        Stream<MovieEntity> movieStream;
        long recordCount = movieRepository.count();
        if (recordCount > this.recordLimit) {
            Pageable moviePageable = PageRequest.of(0, this.chunkSize);
            Page<MovieEntity> moviePage = movieRepository.findAll(moviePageable);
            movieStream = moviePage.get();
        } else {
            movieStream = movieRepository.findAll().stream();
        }
        List<SearchPayloadDataInner> movieList = movieStream
            .map(e -> (SearchPayloadDataInner) new Movie(
                    e.getMovieId(),
                    e.getTitle(),
                    e.getStartDateTime(),
                    e.getAvailableSeats()
        )).toList();
        return new MovieListDTO(movieList, recordCount);
    }

    @Override
    public MovieListDTO getAllMovies(int page) {
        Pageable moviePageable = PageRequest.of(page-1, this.chunkSize);
        Page<MovieEntity> moviePage = movieRepository.findAll(moviePageable);
        Stream<MovieEntity> movieStream = moviePage.get();
        List<SearchPayloadDataInner> movieList = movieStream
            .map(e -> (SearchPayloadDataInner) new Movie(
                    e.getMovieId(),
                    e.getTitle(),
                    e.getStartDateTime(),
                    e.getAvailableSeats()
            )).toList();
        return new MovieListDTO(movieList, moviePage.getTotalElements());
    }
}
