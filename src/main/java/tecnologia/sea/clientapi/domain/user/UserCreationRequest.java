package tecnologia.sea.clientapi.domain.user;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {

  @NotBlank(message = "O nome de usuário é obrigatório.")
  private String username;

  @NotBlank(message = "A senha é obrigatória.")
  private String password;

  @NotBlank(message = "O papel do usuário é obrigatório.")
  private String role;
}
