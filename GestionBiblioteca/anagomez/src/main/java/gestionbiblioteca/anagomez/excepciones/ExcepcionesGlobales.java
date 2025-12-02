package gestionbiblioteca.anagomez.excepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Maneja errores de todos los controladores REST
public class ExcepcionesGlobales {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valida campos @Valid
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores); // 400 Bad Request
    }

    @ExceptionHandler(ResourceNotFoundException.class) // Cuando no existe un recurso
    public ResponseEntity<String> manejarNoEncontrado(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // 404
    }
}
