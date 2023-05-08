package tn.esprit.healthcare.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@JsonIgnoreProperties("answer")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment ;
    private String comment ;

    @ManyToOne
    Answer answer ;

    @ManyToOne
    User user ;
}
