package gestionbiblioteca.anagomez.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID del préstamo

    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false) // FK hacia socio
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false) // FK hacia libro
    private Libro libro;

    private LocalDateTime fechaInicio; // Fecha de préstamo
    private LocalDateTime fechaFin; // Fecha de devolución

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado; // ACTIVO, DEVUELTO, DISPONIBLE, RETRASADO

    @Column
    private LocalDate fechaLimite; // Fecha límite de devolución
    @Column
    private Long diasRetraso; // Días de retraso
    @Column
    private Long penalizacion; // Penalización monetaria o puntos

    public enum Estado {
        DISPONIBLE, ACTIVO, DEVUELTO, RETRASADO
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Long getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(Long diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public Long getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(Long penalizacion) {
        this.penalizacion = penalizacion;
    }
}
