package tecnologia.sea.clientapi.domain.client;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O CEP é obrigatório.")
  private String cep;

  @NotBlank(message = "O logradouro é obrigatório.")
  private String streetAddress;

  @NotBlank(message = "O bairro é obrigatório.")
  private String neighborhood;

  @NotBlank(message = "A cidade é obrigatória.")
  private String city;

  @NotBlank(message = "A UF é obrigatória.")
  @Column(length = 2)
  private String state;

  private String complement;

  @OneToOne
  @JoinColumn(name = "client_id")
  @JsonBackReference
  private Client client;
}
