package PKG.service;

import java.util.Optional;

import PKG.entity.Password;

public interface PasswdService {

	<S extends Password> S save(S entity);

	Optional<Password> findByUserId(long userid);

}
