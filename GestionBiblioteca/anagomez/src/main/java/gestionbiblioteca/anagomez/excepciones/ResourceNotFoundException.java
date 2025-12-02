package gestionbiblioteca.anagomez.excepciones;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje); // Lanza un mensaje claro cuando un recurso no se encuentra
    }
}
