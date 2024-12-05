package pe.edu.cibertec.backoffice_mvc_s.service;

import pe.edu.cibertec.backoffice_mvc_s.dto.FilmActualizarDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetallesDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmRegistrarDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> getAllFilms();
    FilmDetallesDto getFilmById(int id);
    FilmDetallesDto registrarFilm(FilmRegistrarDto filmRegistrarDto);
    FilmDetallesDto actualizarFilm(FilmActualizarDto filmActualizarDto);
    void eliminarFilmPorId(int id);

}
