package pet.juniors_dev.elibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {
    private String fileUrl;
    private String downloadUrl;
}
