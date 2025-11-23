package gestionbiblioteca.anagomez.service;

import java.util.List;
import org.springframework.stereotype.Service;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.repository.PrestamoRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public void guardarPrestamo(Prestamo prestamo) {
        prestamoRepository.save(prestamo);
    }

    public Prestamo obtenerPrestamoPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public void eliminarPrestamo(Long id) {
        prestamoRepository.deleteById(id);
    }

}
