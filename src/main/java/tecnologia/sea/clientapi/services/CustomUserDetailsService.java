package tecnologia.sea.clientapi.services;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tecnologia.sea.clientapi.domain.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null || username.trim().isEmpty()) {
      throw new UsernameNotFoundException("Nome de usuário não pode estar vazio.");
    }

    Optional<tecnologia.sea.clientapi.domain.user.User> optionalUser = userRepository.findByUsername(username);

    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }

    tecnologia.sea.clientapi.domain.user.User user = optionalUser.get();

    return new User(
        user.getUsername(),
        user.getPassword(),
        Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
  }
}
