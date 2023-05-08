package tn.esprit.healthcare.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtSignup {
    private String username;
    private String email;
    private String phone_number;

    private String password;
    private List<String> roles;
}
