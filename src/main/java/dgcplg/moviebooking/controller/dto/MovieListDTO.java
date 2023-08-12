package dgcplg.moviebooking.controller.dto;

import dgcplg.moviebooking.model.Movie;

import java.util.List;

public class MovieListDTO {
    private List<Movie> movieList;
    private long recordCount;

    public MovieListDTO() {}

    public MovieListDTO(List<Movie> movieList, long recordCount) {
        this.movieList = movieList;
        this.recordCount = recordCount;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
}
