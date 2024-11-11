package tecnologia.sea.clientapi.domain.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome de usuário é obrigatório.")
  @Column(unique = true)
  private String username;

  @NotBlank(message = "A senha é obrigatória.")
  private String password;

  @NotBlank(message = "O papel do usuário é obrigatório.")
  private String role;
}
