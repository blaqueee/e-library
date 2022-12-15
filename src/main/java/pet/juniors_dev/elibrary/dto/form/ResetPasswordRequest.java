package pet.juniors_dev.elibrary.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @JsonProperty("password")
    @NotBlank
    private String password;

    @NotBlank
    @JsonProperty("repeated_password")
    private String repeatedPassword;
}
