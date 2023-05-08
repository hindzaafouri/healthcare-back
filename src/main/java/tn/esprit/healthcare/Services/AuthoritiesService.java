package tn.esprit.healthcare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.healthcare.Entities.Role;
import tn.esprit.healthcare.Repositories.AuthoritiesRepository;

import java.util.List;

@Service
public class AuthoritiesService {

    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesService(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> getAuthorities(){
        return authoritiesRepository.findAll();
    }
}
