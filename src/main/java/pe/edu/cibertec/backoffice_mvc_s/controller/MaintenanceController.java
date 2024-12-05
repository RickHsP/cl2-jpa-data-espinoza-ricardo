package pe.edu.cibertec.backoffice_mvc_s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmActualizarDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetallesDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmRegistrarDto;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class    MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films = maintenanceService.getAllFilms();
        model.addAttribute("films", films);
        return "maintenance";

    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        FilmDetallesDto filmDetailDto = maintenanceService.getFilmById(id);
        model.addAttribute("filmDetailDto", filmDetailDto);
        return "maintenance-detail";

    }

    //controlador actualizar
    @GetMapping("/actualizar/{id}")
    public String actualizarForm(@PathVariable("id") Integer filmId, Model model) {
        FilmDetallesDto filmDetallesDto = maintenanceService.getFilmById(filmId);


        FilmActualizarDto filmActualizarDto = mapToFilmActualizarDto(filmDetallesDto);

        model.addAttribute("filmActualizarDto", filmActualizarDto);
        return "maintenance-actualizar";
    }

    @PostMapping("/actualizar")
    public String actualizarFilm(@ModelAttribute("filmActualizarDto") FilmActualizarDto filmActualizarDto, Model model) {
        FilmDetallesDto actualizarFilm = maintenanceService.actualizarFilm(filmActualizarDto);
        model.addAttribute("filmDetallesDto", actualizarFilm);
        return "redirect:/maintenance/start";
    }


    private FilmActualizarDto mapToFilmActualizarDto(FilmDetallesDto filmDetallesDto) {
        return new FilmActualizarDto(
                filmDetallesDto.filmId(),
                filmDetallesDto.title(),
                filmDetallesDto.description(),
                filmDetallesDto.releaseYear(),
                filmDetallesDto.rentalDuration(),
                filmDetallesDto.rentalRate(),
                filmDetallesDto.length(),
                filmDetallesDto.replacementCost(),
                filmDetallesDto.rating(),
                filmDetallesDto.specialFeatures(),
                filmDetallesDto.lastUpdate()
        );
    }

    //controlador registrar
    @GetMapping("/registrar")
    public String showCreateForm(Model model) {
        FilmRegistrarDto filmRegistrarDto = initializeFilmRegistrarDto();
        model.addAttribute("filmRegistrarDto", filmRegistrarDto);
        return "maintenance-registrar";
    }
    private FilmRegistrarDto initializeFilmRegistrarDto() {
        return new FilmRegistrarDto(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @PostMapping("/registrar")
    public String registrarFilm(@ModelAttribute FilmRegistrarDto filmRegistrarDto) {
        processFilmRegistrar(filmRegistrarDto);
        return "redirect:/maintenance/start";
    }

    private void processFilmRegistrar(FilmRegistrarDto filmRegistrarDto) {
        maintenanceService.registrarFilm(filmRegistrarDto);
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarFilm(@PathVariable Integer id) {
        maintenanceService.eliminarFilmPorId(id);
        return "redirect:/maintenance/start";
    }

}
