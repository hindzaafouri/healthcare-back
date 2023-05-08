package tn.esprit.healthcare.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Payload.UserPrincipal;
import tn.esprit.healthcare.Repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByemail(username);
        return UserDetailsImpl.build(user);}

    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    public void deletePatient(Long id) {
        userRepository.deleteById(id);
    }
    public boolean ifEmailExist(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public int getUserActive(String email){
        return userRepository.getActive(email);
    }

    @Transactional
    public String getPasswordByEmail(String email){
        return userRepository.getPasswordByEmail(email);
    }
    public User getPatient(Long id) {
        return userRepository.findPatientById(id);
    }


    public User getUserByMail(String mail){
        return this.userRepository.findByemail(mail);
    }
    public void editUser(User user){
        this.userRepository.save(user);
    }

    public List<User> fetchSkieurList() {
        try {
            return userRepository.findAllPatients();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching users: " + ex.getMessage(), ex);
        }
    }
    public List<User> fetchDoctorList() {
        try {
            return userRepository.findAllDoctors();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching users: " + ex.getMessage(), ex);
        }
    }


    public List<User> fetchDoctorsList() {
        try {
            return userRepository.findAllDoctors();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching users: " + ex.getMessage(), ex);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    public User updateUser(User user) {
        Long id = user.getId();
        User std = userRepository.findById(id).get();
        std.setUsername(user.getUsername());
        std.setEmail(user.getEmail());
        std.setPhone_number(user.getPhone_number());
        std.setActive(user.getActive());

        return userRepository.save(std);
    }
}
