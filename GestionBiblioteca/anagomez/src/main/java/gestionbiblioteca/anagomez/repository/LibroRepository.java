package gestionbiblioteca.anagomez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}