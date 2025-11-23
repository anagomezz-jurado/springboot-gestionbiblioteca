package gestionbiblioteca.anagomez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Cambiado socioId por id, que es el campo correcto en Socio
    Prestamo findByLibroIdAndSocioId(Long libroId, Long socioId);
}
