package pet.juniors_dev.elibrary.dto.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    @NotBlank
    private String email;
}
