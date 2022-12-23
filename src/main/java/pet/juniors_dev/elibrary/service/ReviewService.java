package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.juniors_dev.elibrary.dto.ReviewDto;
import pet.juniors_dev.elibrary.dto.form.ReviewRequest;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.Review;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.NotFoundException;
import pet.juniors_dev.elibrary.exception.NotPermittedException;
import pet.juniors_dev.elibrary.mapper.ReviewMapper;
import pet.juniors_dev.elibrary.repository.BookRepository;
import pet.juniors_dev.elibrary.repository.ReviewRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void delete(Long id, User user) throws NotFoundException, NotPermittedException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found!"));
        if (!review.getCommentator().getId().equals(user.getId()))
            throw new NotPermittedException("You can't delete others' reviews!");
        reviewRepository.delete(review);
    }

    private BigDecimal getNewRating(Book book, Integer newRating) {
        double reviewAmount = book.getCountOfReviews().doubleValue();
        double totalSum = ((reviewAmount - 1) * book.getRating().doubleValue()) + newRating;
        if (reviewAmount == 0 || totalSum == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(totalSum / reviewAmount);
    }

    public List<ReviewDto> findByBook(Long bookId) throws NotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found!"));
        var reviews = reviewRepository.findAllByBook(book);
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }
}

