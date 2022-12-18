package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
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
