package gestionbiblioteca.anagomez.modelo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del socio

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre; // Nombre del socio

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false, unique = true)
    private String email; // Email único del socio

    private LocalDate finPenalizacion; // Fecha hasta la que tiene penalización

    @JsonIgnore // Evita bucles al serializar préstamos
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos; // Lista de préstamos del socio

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFinPenalizacion() {
        return finPenalizacion;
    }

    public void setFinPenalizacion(LocalDate finPenalizacion) {
        this.finPenalizacion = finPenalizacion;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
