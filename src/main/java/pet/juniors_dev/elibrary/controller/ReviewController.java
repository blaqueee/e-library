package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.juniors_dev.elibrary.dto.ReviewDto;
import pet.juniors_dev.elibrary.dto.form.ReviewRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.service.ReviewService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewRequest review,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.addReview(review, user));
    }
}
