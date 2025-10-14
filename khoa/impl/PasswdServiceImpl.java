package PKG.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKG.entity.Password;
import PKG.repository.PassRepo;
import PKG.service.PasswdService;

@Service
public class PasswdServiceImpl implements PasswdService{
	@Autowired
	private PassRepo passrepo;

	@Override
	public <S extends Password> S save(S entity) {
		return passrepo.save(entity);
	}

	@Override
	public Optional<Password> findByUserId(long userid) {
		return passrepo.findByUserId(userid);
	}
	
	
	
}
