package tn.esprit.healthcare.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PatientFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patientfile")
    private Long id_patientfile;
    private String username;


    private int consutation_nbr ;
    @NotBlank
    @Size(max = 50)
    private String content;
    @NotBlank
    @Size(max = 50)
    private String state ;

    public Long getId_patientfile() {
        return id_patientfile;
    }

    public void setId_patientfile(Long id_patientfile) {
        this.id_patientfile = id_patientfile;
    }

    public int getConsutation_nbr() {
        return consutation_nbr;
    }

    public void setConsutation_nbr(int consutation_nbr) {
        this.consutation_nbr = consutation_nbr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
