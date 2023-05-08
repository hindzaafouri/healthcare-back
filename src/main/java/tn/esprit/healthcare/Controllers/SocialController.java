package tn.esprit.healthcare.Controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;
import tn.esprit.healthcare.Entities.Role;
import tn.esprit.healthcare.Payload.JwtLogin;
import tn.esprit.healthcare.Payload.LoginResponse;
import tn.esprit.healthcare.Payload.TokenDto;
import tn.esprit.healthcare.Services.AuthoritiesService;
import tn.esprit.healthcare.Services.TokenService;
import tn.esprit.healthcare.Services.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// http://localhost:8080
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/social")
// http://localhost:8080/social
public class SocialController {

    @Value("google.id")
    private String idClient;

    private UserService userService;
    private AuthoritiesService authoritiesService;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SocialController(UserService userService, AuthoritiesService authoritiesService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authoritiesService = authoritiesService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    //http://localhost:8080/social/google
    @PostMapping("/google")
    public LoginResponse loginWithGoogle(@RequestBody TokenDto tokenDto) throws IOException {
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport,factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDto.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        return login(payload.getEmail());
    }

    //http://localhost:8080/social/facebook
    @PostMapping("/facebook")
    public LoginResponse loginWithFacebook(@RequestBody TokenDto tokenDto){
        Facebook facebook = new FacebookTemplate(tokenDto.getToken());
        String [] data = {"email","name","picture"};
        User userFacebook = facebook.fetchObject("me",User.class,data);
        return login(userFacebook.getEmail());

    }

    private LoginResponse login(String email){
        boolean result = userService.ifEmailExist(email); // t   // f
        if(!result){
            tn.esprit.healthcare.Entities.User user = new tn.esprit.healthcare.Entities.User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("farouk"));
            user.setActive(1);
            List<Role> authorities = authoritiesService.getAuthorities();
            Set<Role> roles = user.getUserRoles();
            Role newRole = new Role();
            newRole.setRoleName("Patient");
            newRole.setUser(user);
            roles.add(newRole); // add the new role to the set
            user.setUserRoles(roles); // update the roles set in the user object
          //  user.getUserRoles().add(authorities.get(0));
            userService.addUser(user);
        }
        JwtLogin jwtLogin = new JwtLogin();
        jwtLogin.setEmail(email);
        jwtLogin.setPassword("farouk");
        return tokenService.login(jwtLogin);
    }
}
