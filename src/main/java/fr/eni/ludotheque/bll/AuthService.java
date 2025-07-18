package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.User;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public interface AuthService {
    String login(String username, String password) throws AuthenticationException;
    User register(String username, String password) throws AuthenticationException;
    List<String> getCurrentUserRoles();
}
