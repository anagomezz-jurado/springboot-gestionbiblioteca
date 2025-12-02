package gestionbiblioteca.anagomez.excepciones;

// Excepción para cuando un socio supera el límite de préstamos activos

public class MaxPrestamosException extends RuntimeException {
    public MaxPrestamosException(String mensaje) {
        super(mensaje);
    }
}
