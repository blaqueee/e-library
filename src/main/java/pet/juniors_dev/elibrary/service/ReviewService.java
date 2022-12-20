package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.juniors_dev.elibrary.dto.ReviewDto;
import pet.juniors_dev.elibrary.dto.form.ReviewRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Review;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.NotFoundException;
import pet.juniors_dev.elibrary.mapper.ReviewMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.repository.ReviewRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDto addReview(ReviewRequest reviewRequest, User user) {
        Optional<Book> bookOptional = bookRepository.findById(reviewRequest.getBookId());
        if (bookOptional.isEmpty())
            throw new NotFoundException("Book with id " + reviewRequest.getBookId() + " not found!");
        Book book = bookOptional.get();
        Review review = reviewMapper.toEntity(reviewRequest, user, book);
        Review savedReview = reviewRepository.save(review);
        book.setRating(getNewRating(book, reviewRequest.getRating()));
        book.setCountOfReviews(book.getCountOfReviews() + 1);
        bookRepository.save(book);
        return reviewMapper.toDto(savedReview);
    }

    private BigDecimal getNewRating(Book book, Integer newRating) {
        Integer reviewAmount = book.getCountOfReviews();
        double totalSum = ((reviewAmount - 1) * book.getRating().doubleValue()) + newRating;
        return BigDecimal.valueOf(totalSum / reviewAmount);
    }
}

