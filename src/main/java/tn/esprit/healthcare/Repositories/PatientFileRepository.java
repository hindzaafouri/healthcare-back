package tn.esprit.healthcare.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.PatientFile;

import java.util.List;

@Repository
public interface PatientFileRepository extends JpaRepository<PatientFile,Long> {

    @Query("SELECT p FROM PatientFile p WHERE p.state LIKE ?1%")
    List<PatientFile> findByStatusStartsWith(String state);

    @Query("SELECT count(*) FROM PatientFile p WHERE p.state LIKE ?1%")
    int nombredepatients(String state);

}
