package gestionbiblioteca.anagomez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Saber si un libro está actualmente prestado (estado ACTIVO)
    boolean existsByLibroIdAndEstado(Long libroId, Prestamo.Estado estado);

    // Obtener el préstamo ACTIVO de un libro
    Prestamo findByLibroIdAndEstado(Long libroId, Prestamo.Estado estado);

    // Contar préstamos activos de un socio
    long countBySocioIdAndEstado(Long socioId, Prestamo.Estado estado);

    @Query("SELECT p.libro.titulo, COUNT(p)   FROM Prestamo p  GROUP BY p.libro.id  ORDER BY COUNT(p) DESC")
    List<Object[]> librosMasPrestados();

    @Query("SELECT DATE(p.fechaInicio), COUNT(p) FROM Prestamo p GROUP BY DATE(p.fechaInicio)")
    List<Object[]> prestamosPorFecha();

    long countByEstado(Prestamo.Estado estado);

}
