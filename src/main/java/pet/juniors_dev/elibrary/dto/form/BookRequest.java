package pet.juniors_dev.elibrary.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("description")
    @NotBlank
    private String description;

    @JsonProperty("author")
    @NotBlank
    private String author;

    @JsonProperty("image")
    @NotNull
    private MultipartFile image;

    @JsonProperty("book")
    @NotNull
    private MultipartFile book;
}
