package pet.juniors_dev.elibrary.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    @NotBlank
    private String description;

    @JsonProperty("genre_id")
    @NotNull
    private Long genreId;

    @JsonProperty("author")
    @NotBlank
    private String author;

    @JsonProperty("year")
    @NotNull
    private Integer year;

    @JsonProperty("image")
    @NotNull
    private MultipartFile image;

    @JsonProperty("book")
    @NotNull
    private MultipartFile book;
}
