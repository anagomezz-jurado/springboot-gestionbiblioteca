package gestionbiblioteca.anagomez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Búsqueda por título o autor, ignorando mayúsculas/minúsculas
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);

}