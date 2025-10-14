package PKG.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import PKG.entity.Password;
import PKG.entity.User;
import PKG.repository.PassRepo;
import PKG.repository.UserRepo;
import PKG.service.AccountAuthService;

@Service
public class AccountAuthServiceImpl implements AccountAuthService{

    private final AuthenticationManager authenticationManager;
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private PassRepo passrepo;
	
	@Autowired
	private PasswordEncoder passencoder;
	
	@Autowired
	private UserDetailsService userDetailsServ;

    AccountAuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    } 
	
	// Đăng ký tài khoản mới: 
	// Truyền vào username, email và password
    @Override
	public boolean registerUser(String username, String password, String email) {
    	try {
    		// Mã hóa mật khẩu
            String hashedPassword = passencoder.encode(password);
            
            User newUser = new User();
            newUser.setUserName(username);
            newUser.setEmail(email);
            newUser.setRole("USER");
            userrepo.save(newUser);
             
            // Lấy id của user mới tạo
            Long userid = userrepo.findByUserName(username).get().getId();
            
            // Lưu thông tin mật khẩu vào bảng passwords
            Password passwordEntity = new Password();
            passwordEntity.setUserId(userid);
            passwordEntity.setHashedPasswd(hashedPassword);
            
            passrepo.save(passwordEntity);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
        
    	return true;
    }
    
    @Override
	public Authentication loginUser(String username, String password) {
    	UserDetails userdetails = userDetailsServ.loadUserByUsername(username);
    	
    	if (passencoder.matches(password, userdetails.getPassword())) {
    		// token xác thực
    		UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(userdetails, password, userdetails.getAuthorities());
    		
    		return authenticationManager.authenticate(authenToken);
    	} else {
    		throw new RuntimeException("Chứng chỉ không hợp lệ");
    	}
    }
    
    // cập nhật mật khẩu
    
	@Override
	public boolean changePass(String username, String password) {
    	try {
    		// Mã hóa mật khẩu
            String hashedPassword = passencoder.encode(password);
             
            // Lấy id của user
            Long userid = userrepo.findByUserName(username).get().getId();
            
            // Lưu thông tin mật khẩu vào bảng passwords
            Password passwordEntity = new Password();
            passwordEntity.setUserId(userid);
            passwordEntity.setHashedPasswd(hashedPassword);
            
            passrepo.save(passwordEntity);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
        
    	return true;
    }
}
