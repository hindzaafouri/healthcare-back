package tn.esprit.healthcare.Services;

import org.springframework.stereotype.Service;
import tn.esprit.healthcare.Entities.PatientFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public interface IPatientFileService {
    List<PatientFile> getAllPatientFile();

    PatientFile addPatientFile(PatientFile u, HttpServletResponse response) throws IOException;

    void removePatientFile(Long id);

    Optional<PatientFile> getPatientFile(Long id);

    List<PatientFile> findByStatus(String status);
    PatientFile updatePatientFile(PatientFile u, HttpServletResponse response) throws IOException;
    int nombreselonstatus(String state);
}
