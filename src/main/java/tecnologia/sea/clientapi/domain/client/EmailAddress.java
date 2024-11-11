package tecnologia.sea.clientapi.domain.client;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email")
@Getter
@Setter
public class EmailAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "O e-mail deve ser válido.")
  @Column(unique = true)
  private String emailAddress;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonBackReference
  private Client client;
}
