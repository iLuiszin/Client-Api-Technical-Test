package tecnologia.sea.clientapi.domain.client;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phone")
@Getter
@Setter
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O número de telefone é obrigatório.")
  @Pattern(regexp = "^[0-9]{10,11}$", message = "O número de telefone deve ter entre 10 e 11 dígitos.")
  private String number;

  @NotBlank(message = "O tipo de telefone é obrigatório.")
  private String type;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonBackReference
  private Client client;
}
