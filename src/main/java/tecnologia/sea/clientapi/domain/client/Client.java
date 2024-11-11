package tecnologia.sea.clientapi.domain.client;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "client")
@Table(name = "client")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome é obrigatório.")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
  @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "O nome deve conter apenas letras, espaços e números.")
  private String name;

  @NotBlank(message = "O CPF é obrigatório.")
  @Column(unique = true, length = 11)
  private String cpf;

  @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private Address address;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Phone> phones;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<EmailAddress> emails;
}
