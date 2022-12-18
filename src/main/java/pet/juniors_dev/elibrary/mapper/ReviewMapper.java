package pet.juniors_dev.elibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.ReviewDto;
import pet.juniors_dev.elibrary.dto.form.ReviewRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Review;
import pet.juniors_dev.elibrary.entity.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReviewMapper {
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public Review toEntity(ReviewRequest reviewRequest, User user, Book book) {
        return Review.builder()
                .comment(reviewRequest.getComment())
                .rating(reviewRequest.getRating())
                .commentator(user)
                .book(book)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .comment(review.getComment())
                .rating(review.getRating())
                .commentator(userMapper.toUserDto(review.getCommentator()))
                .book(bookMapper.toDto(review.getBook()))
                .createdAt(review.getCreatedAt())
                .build();
    }
}
