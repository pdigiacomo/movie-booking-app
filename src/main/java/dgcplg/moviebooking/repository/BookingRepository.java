package dgcplg.moviebooking.repository;

import dgcplg.moviebooking.repository.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {}