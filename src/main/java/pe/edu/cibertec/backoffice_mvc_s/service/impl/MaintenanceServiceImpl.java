package pe.edu.cibertec.backoffice_mvc_s.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmActualizarDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetallesDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmRegistrarDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Film;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.repository.FilmRepository;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;
//metodo listar
    @Override
    public List<FilmDto> getAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalRate());
            films.add(filmDto);
        });

        return films;
    }
//metodo detalle
    @Override
    public FilmDetallesDto getFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetallesDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getLanguage().getName(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);

    }

    //metodo registrar
    @Override
    public FilmDetallesDto registrarFilm(FilmRegistrarDto filmRegistrarDto) {
        Film film = new Film();
        Language language = new Language();

        setFilmAttributes(film, filmRegistrarDto);
        setLanguageAttributes(language, filmRegistrarDto);

        film.setLanguage(language);

        filmRepository.save(film);

        return mapToFilmDetallesDto(film);
    }

    private void setFilmAttributes(Film film, FilmRegistrarDto filmRegistrarDto) {
        film.setTitle(filmRegistrarDto.title());
        film.setDescription(filmRegistrarDto.description());
        film.setReleaseYear(filmRegistrarDto.releaseYear());
        film.setRentalDuration(filmRegistrarDto.rentalDuration());
        film.setRentalRate(filmRegistrarDto.rentalRate());
        film.setLength(filmRegistrarDto.length());
        film.setReplacementCost(filmRegistrarDto.replacementCost());
        film.setRating(filmRegistrarDto.rating());
        film.setSpecialFeatures(filmRegistrarDto.specialFeatures());
        film.setLastUpdate(filmRegistrarDto.lastUpdate());
    }

    private void setLanguageAttributes(Language language, FilmRegistrarDto filmRegistrarDto) {
        language.setLanguageId(filmRegistrarDto.languageId());
        language.setName(filmRegistrarDto.languageName());
    }

    private FilmDetallesDto mapToFilmDetallesDto(Film film) {
        return new FilmDetallesDto(film.getFilmId(), film.getTitle(), film.getDescription(), film.getReleaseYear(), film.getLanguage().getName(), film.getRentalDuration(),
                film.getRentalRate(), film.getLength(), film.getReplacementCost(), film.getRating(), film.getSpecialFeatures(), film.getLastUpdate());
    }


    //metodo actualizar
    @Override
    public FilmDetallesDto actualizarFilm(FilmActualizarDto filmActualizarDto) {
        Film film = filmRepository.findById(filmActualizarDto.filmId())
                .orElseThrow(() -> new RuntimeException("No hay pelicula con Id " + filmActualizarDto.filmId()));

        updateField(filmActualizarDto.title(), film::setTitle);
        updateField(filmActualizarDto.description(), film::setDescription);
        updateField(filmActualizarDto.releaseYear(), film::setReleaseYear);
        updateField(filmActualizarDto.rentalDuration(), film::setRentalDuration);
        updateField(filmActualizarDto.rentalRate(), film::setRentalRate);
        updateField(filmActualizarDto.length(), film::setLength);
        updateField(filmActualizarDto.replacementCost(), film::setReplacementCost);
        updateField(filmActualizarDto.rating(), film::setRating);
        updateField(filmActualizarDto.specialFeatures(), film::setSpecialFeatures);

        film.setLastUpdate(new java.util.Date());

        filmRepository.save(film);

        return new FilmDetallesDto(film.getFilmId(), film.getTitle(), film.getDescription(), film.getReleaseYear(), film.getLanguage().getName(), film.getRentalDuration(),
                film.getRentalRate(), film.getLength(), film.getReplacementCost(), film.getRating(), film.getSpecialFeatures(), film.getLastUpdate()
        );
    }



    private <T> void updateField(T field, Consumer<T> setter) {
        if (field != null) {
            setter.accept(field);
        }
    }

    @Override
    public void eliminarFilmPorId(int id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se encontró la película con ID: " + id);
        }

    }


}
