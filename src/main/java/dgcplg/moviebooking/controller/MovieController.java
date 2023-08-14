package dgcplg.moviebooking.controller;

import dgcplg.moviebooking.controller.dto.MovieListDTO;

public interface MovieController {
    MovieListDTO getAllMovies();
    MovieListDTO getAllMovies(int page);
}