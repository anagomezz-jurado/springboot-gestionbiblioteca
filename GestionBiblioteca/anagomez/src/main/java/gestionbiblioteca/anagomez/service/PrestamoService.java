package gestionbiblioteca.anagomez.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.repository.LibroRepository;
import gestionbiblioteca.anagomez.repository.PrestamoRepository;
import gestionbiblioteca.anagomez.repository.SocioRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final SocioRepository socioRepo;
    private final LibroRepository libroRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, SocioRepository socioRepo, LibroRepository libroRepo) {
        this.prestamoRepo = prestamoRepo;
        this.socioRepo = socioRepo;
        this.libroRepo = libroRepo;
    }

    /*
     * ============================================================
     * VALIDAR NUEVO PRESTAMO
     * ============================================================
     */
    public boolean puedePrestar(Socio socio) {

        long activos = prestamoRepo.countBySocioIdAndEstado(socio.getId(), Prestamo.Estado.ACTIVO);
        if (activos >= 3)
            return false;

        if (socio.getFinPenalizacion() != null &&
                socio.getFinPenalizacion().isAfter(LocalDate.now()))
            return false;

        return true;
    }

    /*
     * ============================================================
     * CREAR PRÉSTAMO
     * ============================================================
     */
    public Prestamo crearPrestamo(Long libroId, Long socioId) {

        Libro libro = libroRepo.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Socio socio = socioRepo.findById(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

        Prestamo p = new Prestamo();
        p.setLibro(libro);
        p.setSocio(socio);

        // Fecha inicio = ahora
        p.setFechaInicio(LocalDateTime.now());

        // Fecha límite = 2 días
        p.setFechaLimite(LocalDate.now().plusDays(2));

        // Estado
        p.setEstado(Prestamo.Estado.ACTIVO);

        return prestamoRepo.save(p);
    }

    /*
     * ============================================================
     * DEVOLVER PRÉSTAMO
     * ============================================================
     */
    public Prestamo devolverPrestamo(Long id) {

        Prestamo p = prestamoRepo.findById(id).orElseThrow();
        p.setFechaFin(LocalDateTime.now());
        p.setEstado(Prestamo.Estado.DEVUELTO);

        LocalDate hoy = LocalDate.now();
        long retraso = ChronoUnit.DAYS.between(p.getFechaLimite(), hoy);

        if (retraso > 0) {
            long penalizacionDias = retraso * 2;

            Socio socio = p.getSocio();
            socio.setFinPenalizacion(hoy.plusDays(penalizacionDias));
            socioRepo.save(socio);

            p.setDiasRetraso(retraso);
            p.setPenalizacion(penalizacionDias);
        }

        return prestamoRepo.save(p);
    }

    /*
     * ============================================================
     * CRUD
     * ============================================================
     */
    public List<Prestamo> listar() {
        return prestamoRepo.findAll();
    }

    public Prestamo obtener(Long id) {
        return prestamoRepo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        prestamoRepo.deleteById(id);
    }
}
