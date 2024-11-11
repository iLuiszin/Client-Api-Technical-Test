package tecnologia.sea.clientapi.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
  private boolean success;
  private String message;
  private String username;
  private String role;

  public UserResponseDTO(boolean success, String message, User user) {
    this.success = success;
    this.message = message;
    if (user != null) {
      this.username = user.getUsername();
      this.role = user.getRole();
    }
  }
}
