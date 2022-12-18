package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.BookmarkDto;
import pet.juniors_dev.elibrary.dto.form.BookmarkRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Bookmark;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.NotFoundException;
import pet.juniors_dev.elibrary.mapper.BookMapper;
import pet.juniors_dev.elibrary.mapper.BookmarkMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.repository.BookmarkRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookRepository bookRepository;
    private final BookmarkMapper bookmarkMapper;
    private final BookMapper bookMapper;

    public BookmarkDto addBookmark(BookmarkRequest bookmarkRequest, User user) throws NotFoundException {
        long bookId = bookmarkRequest.getBookId();
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            throw new NotFoundException("Book with id " + bookId + " not found!");
        Bookmark bookmark = bookmarkRepository.save(bookmarkMapper.toEntity(bookOptional.get(), user));
        return bookmarkMapper.toDto(bookmark);
    }

    public void deleteBookmark(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            throw new NotFoundException("Book with id " + bookId + " not found!");
        bookmarkRepository.deleteByBook(bookOptional.get());
    }

    public Page<BookDto> getBookmarksByUser(User user, Pageable pageable){
        var bookmarks = bookmarkRepository.findAllByUser(user, pageable);
        return bookmarks.map(Bookmark::getBook)
                .map(bookMapper::toDto);
    }
}
