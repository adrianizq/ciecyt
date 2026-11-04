package co.edu.itp.ciecyt.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Dia no habil configurado por la administracion para el calculo de plazos.
 */
@Entity
@Table(name = "festivo")
public class Festivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha", nullable = false, unique = true)
    private LocalDate fecha;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo != null ? activo : true;
    }
}
