package gestionbiblioteca.anagomez.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado; // ACTIVO o DEVUELTO

    @Column
    private LocalDate fechaLimite; // NUEVO

    @Column
    private Long diasRetraso; // NUEVO

    @Column
    private Long penalizacion; // NUEVO

    public enum Estado {
        DISPONIBLE, ACTIVO, DEVUELTO, RETRASADO
    }

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

    // getters y setters

}