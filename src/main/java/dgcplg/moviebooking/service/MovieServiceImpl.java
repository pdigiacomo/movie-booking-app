package dgcplg.moviebooking.service;

import dgcplg.moviebooking.api.MovieApiDelegate;
import dgcplg.moviebooking.controller.MovieController;
import dgcplg.moviebooking.controller.dto.MovieListDTO;
import dgcplg.moviebooking.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieApiDelegate {

    private final MovieController movieController;

    @Autowired
    public MovieServiceImpl(MovieController movieController) {
        this.movieController = movieController;
    }

    @Override
    public ResponseEntity<MovieList> getAllMovies(Integer offset) {
        MovieListDTO movieListDTO;
        if (offset == null || offset < 1) {
            movieListDTO = movieController.getAllMovies();
        } else {
            movieListDTO = movieController.getAllMovies(offset);
        }
        MovieList movieList = new MovieList(
                MovieList.StatusEnum.SUCCESS,
                movieListDTO.getRecordCount(),
                "Movie",
                movieListDTO.getMovieList());
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }
}