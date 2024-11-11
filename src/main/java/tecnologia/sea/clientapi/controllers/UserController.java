package tecnologia.sea.clientapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tecnologia.sea.clientapi.domain.user.User;
import tecnologia.sea.clientapi.domain.user.UserCreationRequest;
import tecnologia.sea.clientapi.domain.user.UserResponseDTO;
import tecnologia.sea.clientapi.services.UserService;
import tecnologia.sea.clientapi.domain.user.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/create")
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreationRequest request) {
    if (!request.getRole().equals("ROLE_ADMIN") && !request.getRole().equals("ROLE_USER")) {
      return ResponseEntity.badRequest()
          .body(new UserResponseDTO(false, "Role inválida. Use 'ROLE_ADMIN' ou 'ROLE_USER'.", null));
    }

    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      return ResponseEntity.badRequest()
          .body(new UserResponseDTO(false, "Usuário com esse nome já existe.", null));
    }

    if (!request.getUsername().matches("^[a-zA-Z0-9\\s]+$")) {
      return ResponseEntity.badRequest().body(new UserResponseDTO(false,
          "Nome de usuário deve conter apenas letras, espaços e números.", null));
    }

    if (!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
      return ResponseEntity.badRequest().body(new UserResponseDTO(false,
          "Senha deve conter no mínimo 8 caracteres, pelo menos uma letra maiúscula, pelo menos uma letra minúscula, pelo menos um número e pelo menos um caractere especial.",
          null));
    }

    User newUser = userService.createUser(request.getUsername(), request.getPassword(), request.getRole());
    return ResponseEntity.ok(new UserResponseDTO(true, "Usuário criado com sucesso", newUser));
  }
}
