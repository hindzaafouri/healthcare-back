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
//@JsonIgnoreProperties("answers")
public class Thread implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idThread ;
    private String titleThread ;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(columnDefinition = "int default 0")
    private int votes ;
    @Enumerated(EnumType.STRING)
    private Topic topicThread ;
    private String questionThread ;

    @Column(columnDefinition = "int default 0")
    private boolean status ;
    private LocalDateTime createdAt ;
    private String coverPhotoThread ;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "thread")
    private Set<Answer> answers ;

    @ManyToOne
    User user ;
}
