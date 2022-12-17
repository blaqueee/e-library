package pet.juniors_dev.elibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.juniors_dev.elibrary.entity.Comment;
import pet.juniors_dev.elibrary.entity.Review;
import pet.juniors_dev.elibrary.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String authors;

    @NotEmpty
    private String language;

    @NotEmpty
    private int year;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private List<Review> reviews;

    private List<Comment> comments = new java.util.ArrayList<>();

    private User downloadedUser;
}
