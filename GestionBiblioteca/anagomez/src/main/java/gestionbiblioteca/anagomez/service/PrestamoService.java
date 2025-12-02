package gestionbiblioteca.anagomez.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import gestionbiblioteca.anagomez.excepciones.MaxPrestamosException;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.repository.LibroRepository;
import gestionbiblioteca.anagomez.repository.PrestamoRepository;
import gestionbiblioteca.anagomez.repository.SocioRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepo; // Repositorio de préstamos
    private final SocioRepository socioRepo; // Repositorio de socios
    private final LibroRepository libroRepo; // Repositorio de libros

    // Constructor con inyección de dependencias
    public PrestamoService(PrestamoRepository prestamoRepo, SocioRepository socioRepo, LibroRepository libroRepo) {
        this.prestamoRepo = prestamoRepo;
        this.socioRepo = socioRepo;
        this.libroRepo = libroRepo;
    }

    /* ========================== VALIDACIÓN ========================== */

    // Validar si un socio puede realizar un nuevo préstamo
    public boolean puedePrestar(Socio socio) {

        // 1️⃣ Verificar si tiene más de 3 préstamos activos
        long activos = prestamoRepo.countBySocioIdAndEstado(socio.getId(), Prestamo.Estado.ACTIVO);
        if (activos >= 3)
            return false;

        // 2️⃣ Verificar si tiene penalización activa
        if (socio.getFinPenalizacion() != null && socio.getFinPenalizacion().isAfter(LocalDate.now()))
            return false;

        return true; // Si pasa ambas condiciones, puede prestar
    }

    /* ========================== CREAR PRÉSTAMO ========================== */

    public Prestamo crearPrestamo(Long libroId, Long socioId) {

        Libro libro = libroRepo.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Socio socio = socioRepo.findById(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

        // Validación de máximo 3 préstamos
        if (!puedePrestar(socio)) {
            long activos = prestamoRepo.countBySocioIdAndEstado(socioId, Prestamo.Estado.ACTIVO);
            String msg = "";

            if (activos >= 3)
                msg += " El socio tiene 3 préstamos activos.\n";

            if (socio.getFinPenalizacion() != null && socio.getFinPenalizacion().isAfter(LocalDate.now()))
                msg += "Penalización activa hasta " + socio.getFinPenalizacion() + "\n";

            throw new MaxPrestamosException(msg);
        }

        Prestamo p = new Prestamo();
        p.setLibro(libro);
        p.setSocio(socio);
        p.setFechaInicio(LocalDateTime.now());
        p.setFechaLimite(LocalDate.now().plusDays(2));
        p.setEstado(Prestamo.Estado.ACTIVO);

        return prestamoRepo.save(p);
    }

    /* ========================== DEVOLVER PRÉSTAMO ========================== */

    public Prestamo devolverPrestamo(Long id) {

        Prestamo p = prestamoRepo.findById(id).orElseThrow();
        p.setFechaFin(LocalDateTime.now()); // Fecha de devolución
        p.setEstado(Prestamo.Estado.DEVUELTO); // Cambiar estado

        // Calcular retraso
        LocalDate hoy = LocalDate.now();
        long retraso = java.time.temporal.ChronoUnit.DAYS.between(p.getFechaLimite(), hoy);

        if (retraso > 0) { // Si hubo retraso
            long penalizacionDias = retraso * 2; // Penalización = 2 días por retraso
            Socio socio = p.getSocio();
            socio.setFinPenalizacion(hoy.plusDays(penalizacionDias)); // Actualizar penalización
            socioRepo.save(socio);

            p.setDiasRetraso(retraso); // Guardar días de retraso
            p.setPenalizacion(penalizacionDias); // Guardar penalización
        }

        return prestamoRepo.save(p); // Guardar cambios en BD
    }

    /* ========================== CONTADORES ========================== */

    // Contar préstamos activos de un socio
    public long countPrestamosActivosPorSocio(Long socioId) {
        return prestamoRepo.countBySocioIdAndEstado(socioId, Prestamo.Estado.ACTIVO);
    }

    /* ========================== CRUD ========================== */

    // Listar todos los préstamos
    public List<Prestamo> listar() {
        return prestamoRepo.findAll();
    }

    // Obtener préstamo por id
    public Prestamo obtener(Long id) {
        return prestamoRepo.findById(id).orElse(null);
    }

    // Eliminar préstamo
    public void eliminar(Long id) {
        prestamoRepo.deleteById(id);
    }

    public List<Object[]> librosMasPrestados() {
        return prestamoRepo.librosMasPrestados();
    }

    public Map<String, Long> resumenPrestamos() {
        Map<String, Long> resumen = new HashMap<>();
        resumen.put("activos", prestamoRepo.countByEstado(Prestamo.Estado.ACTIVO));
        resumen.put("devueltos", prestamoRepo.countByEstado(Prestamo.Estado.DEVUELTO));
        return resumen;
    }

}
