package pet.juniors_dev.elibrary.dto.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEditRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private MultipartFile avatar;
}
