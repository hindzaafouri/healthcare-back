package tn.esprit.healthcare.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.healthcare.Entities.PatientDto;
import tn.esprit.healthcare.Entities.PatientFile;
import tn.esprit.healthcare.Services.EmailService;
import tn.esprit.healthcare.Services.IPatientFileServiceImpl;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("patients")
@CrossOrigin(value = "*")
public class PatientFileController {

   // IPatientFileService IPatientFileService;
   @Autowired
   IPatientFileServiceImpl iPatientFileServiceimpl;
 @Autowired
 private EmailService emailService;
    @GetMapping("/all")
    public List<PatientFile> getAll(){
        String body="test";
        emailService.sendMail("anaslamiri07@gmail.com","Le patient est encore malade",body);
        return iPatientFileServiceimpl.getAllPatientFile();

    }

    @GetMapping("{num}")

    public Optional<PatientFile> retrievePatientFile(@PathVariable Long num) {

        return  iPatientFileServiceimpl.getPatientFile(num);

    }
    @PostMapping("/add")
    public PatientFile addPatient(@RequestBody PatientFile patientFile, HttpServletResponse response) throws IOException {

        return iPatientFileServiceimpl.addPatientFile(patientFile,response);

    }

    @DeleteMapping("delete/{num}")
    public void removePatientFile(@PathVariable Long num){

        iPatientFileServiceimpl.removePatientFile(num);
    }
    @PutMapping("/update")
    public PatientFile updatePiste(@RequestBody PatientFile patientFile, HttpServletResponse response) throws IOException {

        return  iPatientFileServiceimpl.updatePatientFile(patientFile,response);

    }


    @GetMapping(value = "/findBystate/{state}")
    @ResponseBody
    public List<PatientFile> findByStatus(@PathVariable(name = "state")String state) {
        if (state.isEmpty()) {
            return null;
        }

        return iPatientFileServiceimpl.findByStatus(state);
    }

    @GetMapping(value = "/Allnumber")
    @ResponseBody
    public PatientDto nombredepatientsstatus() {

        return iPatientFileServiceimpl.nombreselonstatusDto();
    }

}
