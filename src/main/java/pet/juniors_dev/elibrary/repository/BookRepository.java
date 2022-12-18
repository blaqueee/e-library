package pet.juniors_dev.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.juniors_dev.elibrary.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}