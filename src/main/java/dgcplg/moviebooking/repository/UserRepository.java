package dgcplg.moviebooking.repository;

import dgcplg.moviebooking.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}