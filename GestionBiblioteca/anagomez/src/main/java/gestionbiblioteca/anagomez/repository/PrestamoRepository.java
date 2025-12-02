package gestionbiblioteca.anagomez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Saber si un libro está actualmente prestado
    boolean existsByLibroIdAndEstado(Long libroId, Prestamo.Estado estado);

    // Obtener el préstamo ACTIVO de un libro
    Prestamo findByLibroIdAndEstado(Long libroId, Prestamo.Estado estado);

    // Contar préstamos activos de un socio
    long countBySocioIdAndEstado(Long socioId, Prestamo.Estado estado);
}
