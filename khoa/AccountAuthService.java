package PKG.service;

import org.springframework.security.core.Authentication;

public interface AccountAuthService {

	boolean registerUser(String username, String password, String email);

	Authentication loginUser(String username, String password);

	boolean changePass(String username, String password);

}
