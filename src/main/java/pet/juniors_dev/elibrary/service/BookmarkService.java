package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.BookmarkDto;
import pet.juniors_dev.elibrary.dto.form.BookmarkRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Bookmark;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.NotFoundException;
import pet.juniors_dev.elibrary.exception.NotPermittedException;
import pet.juniors_dev.elibrary.mapper.BookMapper;
import pet.juniors_dev.elibrary.mapper.BookmarkMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.repository.BookmarkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void deleteBookmark(Long bookmarkId, User user) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundException("Bookmark with id " + bookmarkId + " not found!"));
        if (!bookmark.getUser().getId().equals(user.getId()))
            throw new NotPermittedException("You can't delete others' bookmarks!");
        bookmarkRepository.delete(bookmark);
    }

    public List<BookDto> getBookmarksByUser(User user){
        var bookmarks = bookmarkRepository.findAllByUser(user);
        return bookmarks.stream()
                .map(Bookmark::getBook)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
