package dgcplg.moviebooking.repository;

import dgcplg.moviebooking.repository.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {}