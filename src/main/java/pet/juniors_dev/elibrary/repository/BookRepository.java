package pet.juniors_dev.elibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pet.juniors_dev.elibrary.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b from Book b WHERE b.countOfReviews > :value ORDER BY b.rating DESC ")
    Page<Book> findAllByOrderByRatingDesc(int value, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.name LIKE %:value% OR b.author LIKE %:value% ORDER BY b.name")
    Page<Book> findByNameAndAuthor(String value, Pageable pageable);

    @Query("SELECT b FROM Book b INNER JOIN b.genres g WHERE g.name = :genre ORDER BY b.name")
    Page<Book> findBooksByGenre(String genre, Pageable pageable);
}