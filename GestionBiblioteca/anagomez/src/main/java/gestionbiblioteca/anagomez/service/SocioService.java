package gestionbiblioteca.anagomez.service;

import java.util.List;
import org.springframework.stereotype.Service;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.repository.SocioRepository;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> listarSocios() {
        return socioRepository.findAll();
    }
}
