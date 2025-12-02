package gestionbiblioteca.anagomez.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity // Marca la clase como una entidad de JPA (tabla en la BD)
@Table(name = "libro") // Nombre de la tabla en la base de datos
public class Libro {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    @NotBlank(message = "El título es obligatorio") // Validación de campo obligatorio
    @Column(nullable = false) // No permite nulos en BD
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false)
    private String autor;

    @JsonIgnore // Evita bucles infinitos en JSON al serializar préstamos
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;
    // Relación 1:N: Un libro puede tener muchos préstamos

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
