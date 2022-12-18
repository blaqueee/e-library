package pet.juniors_dev.elibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.BookmarkDto;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Bookmark;
import pet.juniors_dev.elibrary.entity.User;

@Component
@RequiredArgsConstructor
public class BookmarkMapper {
    private final BookMapper bookMapper;
    private final UserMapper userMapper;
    public Bookmark toEntity(Book book, User user){
        return Bookmark.builder()
                .book(book)
                .user(user)
                .build();
    }

    public BookmarkDto toDto(Bookmark bookmark){
        return BookmarkDto.builder()
                .id(bookmark.getId())
                .book(bookMapper.toDto(bookmark.getBook()))
                .user(userMapper.toUserDto(bookmark.getUser()))
                .build();
    }
}
