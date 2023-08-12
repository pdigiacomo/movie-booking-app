package dgcplg.moviebooking.service;

import dgcplg.moviebooking.api.MovieApiDelegate;
import dgcplg.moviebooking.controller.MovieController;
import dgcplg.moviebooking.controller.dto.MovieListDTO;
import dgcplg.moviebooking.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.SUPPORTS,
        timeout = 30)
public class MovieServiceImpl implements MovieApiDelegate {

    private final MovieController movieController;

    @Autowired
    public MovieServiceImpl(MovieController movieController) {
        this.movieController = movieController;
    }

    @Override
    @Transactional(
//            rollbackFor = ,
//            noRollbackFor = ,
//            rollbackForClassName = ,
//            noRollbackForClassName = ,
            readOnly = true)
    public ResponseEntity<MovieList> getAllMovies(Integer offset) {
        MovieListDTO movieListDTO;
        if (offset == null || offset < 1) {
            movieListDTO = movieController.getAllMovies(0);
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