package pet.juniors_dev.elibrary.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    @JsonProperty("comment")
    @NotBlank
    private String comment;

    @JsonProperty("rating")
    @NotNull
    private Integer rating;

    @JsonProperty("book_id")
    @NotNull
    private Long bookId;
}
