package pet.juniors_dev.elibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final UserMapper userMapper;

    public Book toEntity(BookRequest book, User user, String imageUrl, FileDto bookUrl) {
        return Book.builder()
                .name(book.getName())
                .description(book.getDescription())
                .author(book.getAuthor())
                .year(book.getYear())
                .imageUrl(imageUrl)
                .bookUrl(bookUrl.getFileUrl())
                .bookDownloadUrl(bookUrl.getDownloadUrl())
                .creator(user)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .bookUrl(book.getBookUrl())
                .bookDownloadUrl(book.getBookDownloadUrl())
                .year(book.getYear())
                .creator(userMapper.toUserDto(book.getCreator()))
                .createdAt(book.getCreatedAt())
                .build();
    }
}
