package pet.juniors_dev.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.juniors_dev.elibrary.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Long, Genre> {
    Optional<Genre> findById(Long id);
}
