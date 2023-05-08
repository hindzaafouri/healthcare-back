package tn.esprit.healthcare.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.healthcare.Entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String email;
    private String token;

    private User user;

    public LoginResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public LoginResponse(User user,String email, String token) {
        this.user = user;
        this.token = token;
        this.email = email;
    }
}
