package tn.esprit.healthcare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByemail(String email);

    public boolean existsByEmail(String email);

    @Query("select u.active from User u where u.email=?1")
    public int getActive(String email);

    @Query("select u.password from User u where u.email=?1")
    public String getPasswordByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName = 'Patient'")
    List<User> findAllPatients();

    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE u.id =:id AND r.roleName = 'Patient'")
    User findPatientById(@Param("id") Long id);

    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName = 'Doctor'")
    List<User> findAllDoctors();



    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Patient'")
    Long countByuserRoles();

    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Doctor'")
    Long countByuserRoles1();

    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Admin'")
    Long countByuserRoles2();



}

