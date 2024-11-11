package tecnologia.sea.clientapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tecnologia.sea.clientapi.domain.client.Client;
import tecnologia.sea.clientapi.domain.client.ClientRepository;
import tecnologia.sea.clientapi.domain.client.ClientResponseDTO;
import tecnologia.sea.clientapi.domain.client.EmailAddress;
import tecnologia.sea.clientapi.domain.client.Phone;
import tecnologia.sea.clientapi.domain.client.Address;
import tecnologia.sea.clientapi.domain.client.ViaCepResponse;
import tecnologia.sea.clientapi.services.ViaCepService;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ViaCepService viaCepService;

  @PostMapping("/create")
  public ResponseEntity<ClientResponseDTO> createClient(@RequestBody Client client) {
    if (!client.getName().matches("^[a-zA-Z0-9\\s]+$")) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false,
          "O nome do cliente deve conter apenas letras, espaços e números."));
    }

    if (client.getName().length() < 3 || client.getName().length() > 100) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false,
          "O nome do cliente deve ter entre 3 e 100 caracteres."));
    }

    if (client.getAddress() == null || client.getAddress().getCep() == null) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false, "O endereço com CEP é obrigatório."));
    }

    if (client.getEmails() == null || client.getEmails().isEmpty()) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false, "O cliente deve ter pelo menos um email."));
    }

    for (EmailAddress email : client.getEmails()) {
      if (!email.getEmailAddress()
          .matches("^[a-zA-Z0-9\\s]+(\\.[a-zA-Z0-9\\s]+)*@[a-zA-Z0-9\\s]+(\\.[a-zA-Z0-9\\s]+)*(\\.[a-zA-Z]{2,})$")) {
        return ResponseEntity.badRequest().body(new ClientResponseDTO(false, "Email inválido."));
      }
    }

    String cep = client.getAddress().getCep();

    ViaCepResponse endereco = viaCepService.consultaCep(cep);
    if (endereco.getErro() != null) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false, "CEP inválido."));
    }

    Address clientAddress = client.getAddress();
    clientAddress.setCep(cep);
    clientAddress.setStreetAddress(endereco.getLogradouro());
    clientAddress.setNeighborhood(endereco.getBairro());
    clientAddress.setCity(endereco.getLocalidade());
    clientAddress.setState(endereco.getUf());
    clientAddress.setComplement(endereco.getComplemento());
    clientAddress.setClient(client);

    for (EmailAddress email : client.getEmails()) {
      email.setClient(client);
    }

    for (Phone phone : client.getPhones()) {
      phone.setClient(client);
    }

    if (clientRepository.findByCpf(client.getCpf()).isPresent()) {
      return ResponseEntity.badRequest().body(new ClientResponseDTO(false, "Cliente com este CPF já existe."));
    }

    clientRepository.save(client);
    return ResponseEntity.ok(new ClientResponseDTO(true, "Cliente cadastrado com sucesso.", client));
  }

  @GetMapping("/cpf/{cpf}")
  public ResponseEntity<ClientResponseDTO> getClientByCpf(@PathVariable String cpf) {
    return clientRepository.findByCpf(cpf)
        .map(client -> ResponseEntity.ok(new ClientResponseDTO(true, "Cliente encontrado.", client)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ClientResponseDTO(false, "Cliente não encontrado.")));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
    return clientRepository.findById(id)
        .map(client -> ResponseEntity.ok(new ClientResponseDTO(true, "Cliente encontrado.", client)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ClientResponseDTO(false, "Cliente não encontrado.")));
  }

  @GetMapping
  public ResponseEntity<ClientResponseDTO> getAllClients() {
    List<Client> allClients = clientRepository.findAll();
    return ResponseEntity.ok(new ClientResponseDTO(true, "Clientes encontrados.", allClients));
  }
}
