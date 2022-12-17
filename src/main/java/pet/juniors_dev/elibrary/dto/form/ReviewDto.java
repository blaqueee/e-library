package pet.juniors_dev.elibrary.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.juniors_dev.elibrary.entity.Book;
import pet.juniors_dev.elibrary.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String review;

    private LocalDateTime createdAt;

    @Min(value = 1)
    @Max(value = 10)
    private int rating;

    private User user;

    private Book book;
}
