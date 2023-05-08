package tn.esprit.healthcare.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@JsonIgnoreProperties({"thread"})
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnswer ;
    private String answer ;
    @Column(columnDefinition = "int default 0")
    private int votes ;

    @ManyToOne

    Thread thread ;

    private LocalDateTime createdAt ;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "answer")

    private Set<Comment> comments ;

    @ManyToOne
    User user ;

}
