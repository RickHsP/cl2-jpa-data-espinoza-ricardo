package pe.edu.cibertec.backoffice_mvc_s.dto;

import java.util.Date;

public record FilmActualizarDto(Integer filmId,
                                String title,
                                String description,
                                Integer releaseYear,
                                Integer rentalDuration,
                                Double rentalRate,
                                Integer length,
                                Double replacementCost,
                                String rating,
                                String specialFeatures,
                                Date lastUpdate) {
}
