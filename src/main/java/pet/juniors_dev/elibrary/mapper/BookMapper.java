package pet.juniors_dev.elibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.BookDto;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.dto.form.BookRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.NotFoundException;
import pet.juniors_dev.elibrary.repository.GenreRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final UserMapper userMapper;
    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    public Book toEntity(BookRequest book, User user, String imageUrl, FileDto bookUrl) {
        return Book.builder()
                .name(book.getName())
                .description(book.getDescription())
                .genres(Set.of(
                        genreRepository.findById(book.getGenreId())
                        .orElseThrow(NotFoundException::new)))
                .author(book.getAuthor())
                .year(book.getYear())
                .imageUrl(imageUrl)
                .bookUrl(bookUrl.getFileUrl())
                .bookDownloadUrl(bookUrl.getDownloadUrl())
                .rating(BigDecimal.ZERO)
                .creator(user)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .genres(book.getGenres().stream()
                        .map(genreMapper::toDto)
                        .collect(Collectors.toSet()))
                .author(book.getAuthor())
                .year(book.getYear())
                .imageUrl(book.getImageUrl())
                .bookUrl(book.getBookUrl())
                .bookDownloadUrl(book.getBookDownloadUrl())
                .rating(book.getRating())
                .creator(userMapper.toUserDto(book.getCreator()))
                .createdAt(book.getCreatedAt())
                .build();
    }
}
