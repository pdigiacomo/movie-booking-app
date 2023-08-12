package dgcplg.moviebooking.repository.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="movie")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "start_datetime")
    private OffsetDateTime startDateTime;

    @Column(nullable = false, name = "available_seats")
    private int availableSeats;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", availableSeats=" + availableSeats +
                '}';
    }
}