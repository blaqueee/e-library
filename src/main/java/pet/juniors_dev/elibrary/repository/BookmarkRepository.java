package pet.juniors_dev.elibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Bookmark;
import pet.juniors_dev.elibrary.entity.User;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Transactional
    void deleteByBook(Book book);

    List<Bookmark> findAllByUser(User user);
}