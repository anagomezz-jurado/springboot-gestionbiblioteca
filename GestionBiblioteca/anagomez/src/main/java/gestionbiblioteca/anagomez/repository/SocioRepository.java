package gestionbiblioteca.anagomez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestionbiblioteca.anagomez.modelo.Socio;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
}