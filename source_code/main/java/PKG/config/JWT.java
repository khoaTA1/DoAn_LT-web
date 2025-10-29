package PKG.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWT {
	private final String SECRET_KEY = "65e6a1070b0bdb0cf90898899c937db509149fb9e0307f0adcae06bd29b1a401";
	private final long EXPIRATION_TIME = 3600000;
	
	private SecretKey getSignInKey() {
		byte[] key = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(key);
	}
	
	public long getExpirTime() {
		return EXPIRATION_TIME;
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expir) {
		return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1800000)).signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}

	public String genToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, EXPIRATION_TIME);
	}
	
	public String genToken(UserDetails userDetails) {
		return genToken(new HashMap<>(), userDetails);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Date extractExpir(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpir(token).before(new Date());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String subject = extractUsername(token);
		return (subject.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
