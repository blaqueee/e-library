package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.ReviewDto;
import pet.juniors_dev.elibrary.dto.form.ReviewRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> findByBook(@RequestParam("id") Long id) {
        return ResponseEntity.ok(reviewService.findByBook(id));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewRequest review,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.addReview(review, user));
    }

    @DeleteMapping
    public ResponseEntity<?> removeReview(@RequestParam("id") Long id,
                                          @AuthenticationPrincipal User user) {
        reviewService.delete(id, user);
        return ResponseEntity.ok("Review successfully deleted!");
    }
}
