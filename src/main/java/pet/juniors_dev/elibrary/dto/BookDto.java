package pet.juniors_dev.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("author")
    private String author;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("creator")
    private UserDto creator;
}
