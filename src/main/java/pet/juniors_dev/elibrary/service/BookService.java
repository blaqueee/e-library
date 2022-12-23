package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.*;
import pet.juniors_dev.elibrary.mapper.BookMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.utility.CloudStorageUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CloudStorageUtil cloudStorage;
    private final BookMapper bookMapper;

    public BookDto addBook(BookRequest bookRequest, User user) throws FileException, FileWriteException, GCPFileUploadException {
        checkImage(bookRequest.getImage());
        checkBook(bookRequest.getBook());
        Book book = uploadBook(bookRequest, user);
        book.setCountOfReviews(0);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public List<BookDto> getTop() {
        var books = bookRepository.findAllByOrderByRatingDesc(10);
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getByGenre(String genre) {
        var books = bookRepository.findBooksByGenre(genre);
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findByNameAndAuthor(String value) {
        var books = bookRepository.findByNameAndAuthor(value);
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findNewest() {
        var books = bookRepository.findFirst20ByOrderByCreatedAtDesc();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findAll() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private Book uploadBook(BookRequest bookRequest, User user) throws FileException, FileWriteException, GCPFileUploadException {
        FileDto image = cloudStorage.uploadFile(bookRequest.getImage());
        FileDto book = cloudStorage.uploadFile(bookRequest.getBook());
        return bookMapper.toEntity(bookRequest, user, image.getFileUrl(), book);
    }

    private void checkImage(MultipartFile file) throws FileException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Set<String> extensions = Set.of("jpg", "jpeg", "img", "png", "svg");
        if (!extensions.contains(extension))
            throw new FileException("Photo's extension is not correct!");
    }

    private void checkBook(MultipartFile file) throws FileException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"pdf".equals(extension))
            throw new FileException("Book's extension is not correct!");
    }

    public void delete(Long bookId, User user) throws NotFoundException, NotPermittedException {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found!"));
        if (!book.getCreator().getId().equals(user.getId()))
            throw new NotPermittedException("You can't remove others' books!");
        bookRepository.delete(book);
    }
}
