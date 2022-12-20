package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.FileException;
import pet.juniors_dev.elibrary.exception.FileWriteException;
import pet.juniors_dev.elibrary.exception.GCPFileUploadException;
import pet.juniors_dev.elibrary.mapper.BookMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.utility.CloudStorageUtil;

import java.util.Set;

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

    public Page<BookDto> getTop(Pageable pageable) {
        //Надо куда-то в Properties вывести значение, который мы используем как минимальное количество "оценок" для топа книг
        var books = bookRepository.findAllByOrderByRatingDesc(10, pageable);
        return books.map(bookMapper::toDto);
    }

    public Page<BookDto> getByGenre(String genre, Pageable pageable) {
        var books = bookRepository.findBooksByGenre(genre, pageable);
        return books.map(bookMapper::toDto);
    }

    public Page<BookDto> findByNameAndAuthor(String value, Pageable pageable) {
        var books = bookRepository.findByNameAndAuthor(value, pageable);
        return books.map(bookMapper::toDto);
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
}
