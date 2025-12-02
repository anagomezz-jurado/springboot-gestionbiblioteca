package gestionbiblioteca.anagomez.controller;

import gestionbiblioteca.anagomez.repository.PrestamoRepository;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstadisticasController {

    private final PrestamoRepository prestamoRepository;

    private final PrestamoService prestamoService;
    private final BibliotecaService bibliotecaService;

    public EstadisticasController(PrestamoService prestamoService, BibliotecaService bibliotecaService,
            PrestamoRepository prestamoRepository) {
        this.prestamoService = prestamoService;
        this.bibliotecaService = bibliotecaService;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/estadisticas")
    public String verEstadisticas(Model model) {

        model.addAttribute("librosMasPrestados", prestamoService.librosMasPrestados());
        model.addAttribute("prestamosPorFecha", prestamoRepository.prestamosPorFecha());
        model.addAttribute("conteoPrestamos", prestamoService.resumenPrestamos());

        return "estadisticas";
    }
}
