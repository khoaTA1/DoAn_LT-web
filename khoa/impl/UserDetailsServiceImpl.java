package PKG.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import PKG.entity.User;
import PKG.repository.PassRepo;
import PKG.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UserRepo userrepo;
	
	@Autowired
	private PassRepo passrepo;

    public UserDetailsServiceImpl(UserRepo userrepo) {
        this.userrepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user!"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(passrepo.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy mật khẩu")).getHashedPasswd())
                .authorities(user.getRole().split(","))
                .build();
    }
}