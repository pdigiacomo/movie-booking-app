package dgcplg.moviebooking.controller.dto;

import dgcplg.moviebooking.model.SearchPayloadDataInner;

import java.util.List;

public class MovieListDTO {
    private List<SearchPayloadDataInner> movieList;
    private long recordCount;

    public MovieListDTO() {}

    public MovieListDTO(List<SearchPayloadDataInner> movieList, long recordCount) {
        this.movieList = movieList;
        this.recordCount = recordCount;
    }

    public List<SearchPayloadDataInner> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<SearchPayloadDataInner> movieList) {
        this.movieList = movieList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
}
