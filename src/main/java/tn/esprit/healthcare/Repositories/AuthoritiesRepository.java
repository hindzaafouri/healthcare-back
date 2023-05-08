package tn.esprit.healthcare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Role;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Role,Long> {
}
