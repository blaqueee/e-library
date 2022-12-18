package pet.juniors_dev.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    @JsonProperty("comment")
    private String comment;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("commentator")
    private UserDto commentator;

    @JsonProperty("book")
    private BookDto book;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
