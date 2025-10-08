package PKG.service;

import org.springframework.security.core.Authentication;

public interface AccountAuthService {

	boolean registerUser(String username, String password);

	Authentication loginUser(String username, String password);

}
