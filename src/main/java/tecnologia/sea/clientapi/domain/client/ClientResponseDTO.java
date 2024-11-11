package tecnologia.sea.clientapi.domain.client;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO {
  private boolean success;
  private String message;
  private Client client;
  private List<Client> clients;

  public ClientResponseDTO(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ClientResponseDTO(boolean success, String message, Client client) {
    this.success = success;
    this.message = message;
    if (client != null) {
      this.client = formatClient(client);
    }
  }

  public ClientResponseDTO(boolean success, String message, List<Client> clients) {
    this.success = success;
    this.message = message;
    if (clients != null && !clients.isEmpty()) {
      this.clients = clients.stream()
          .map(this::formatClient)
          .collect(Collectors.toList());
    }
  }

  private Client formatClient(Client client) {
    if (client != null) {
      if (client.getCpf() != null) {
        client.setCpf(formatCpf(client.getCpf()));
      }
      if (client.getAddress() != null && client.getAddress().getCep() != null) {
        client.setAddress(formatAddress(client.getAddress()));
      }
      if (client.getPhones() != null && !client.getPhones().isEmpty()) {
        client.setPhones(client.getPhones().stream()
            .map(this::formatPhone)
            .collect(Collectors.toSet()));
      }
    }
    return client;
  }

  private String formatCpf(String cpf) {
    if (cpf != null && cpf.matches("\\d{11}")) {
      return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    return cpf;
  }

  private Address formatAddress(Address address) {
    if (address != null && address.getCep() != null && address.getCep().matches("\\d{8}")) {
      address.setCep(address.getCep().replaceAll("(\\d{5})(\\d{3})", "$1-$2"));
    }
    return address;
  }

  private Phone formatPhone(Phone phone) {
    if (phone != null && phone.getNumber() != null) {
      String number = phone.getNumber();
      switch (phone.getType()) {
        case "CELULAR":
          if (number.matches("\\d{11}")) {
            phone.setNumber(number.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3"));
          }
          break;
        case "RESIDENCIAL":
        case "COMERCIAL":
          if (number.matches("\\d{10}")) {
            phone.setNumber(number.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3"));
          }
          break;
        default:
          break;
      }
    }
    return phone;
  }
}
