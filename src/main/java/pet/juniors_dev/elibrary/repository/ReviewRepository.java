package pet.juniors_dev.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countByBook(Book book);
}