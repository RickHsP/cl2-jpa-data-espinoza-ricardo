package pe.edu.cibertec.backoffice_mvc_s.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actor_id;
    private Integer film_id;
    private Date last_update;

}
