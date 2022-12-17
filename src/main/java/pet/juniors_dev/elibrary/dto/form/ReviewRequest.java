package pet.juniors_dev.elibrary.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private Integer rating;
}
