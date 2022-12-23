package pet.juniors_dev.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GenreDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
