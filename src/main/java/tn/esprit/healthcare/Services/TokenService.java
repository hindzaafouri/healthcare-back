package tn.esprit.healthcare.Services;

import com.auth0.jwt.JWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Payload.JwtLogin;
import tn.esprit.healthcare.Payload.JwtProperties;
import tn.esprit.healthcare.Payload.LoginResponse;
import tn.esprit.healthcare.Payload.UserPrincipal;
import tn.esprit.healthcare.Repositories.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class TokenService {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserRepository userRepository;
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;
    @Autowired
    public TokenService(UserRepository userRepository,AuthenticationManager authenticationManager,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService=userService;
        this.userRepository=userRepository;

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    private String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();


        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public LoginResponse login(JwtLogin jwtLogin) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtLogin.getEmail(),
                jwtLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl userDetails = userService.loadUserByUsername(jwtLogin.getEmail());
        String newGeneratedToken = generateToken(authenticate);

        User user = userRepository.findByemail(jwtLogin.getEmail());
        String token = generateToken(authenticate);
        return new LoginResponse(user,jwtLogin.getEmail(),token);

    }
}
