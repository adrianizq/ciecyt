package co.edu.itp.ciecyt.service.dto;



import java.time.LocalDate;
import java.util.Arrays;
import java.io.Serializable;
import javax.persistence.Lob;


/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.AdjuntoProyectoFase} entity.
 */
public class AdjuntoProyectoFaseDTO implements Serializable {

    private Long id;

    private String nombreAdjunto;

    private LocalDate fechaCreacion;

    private LocalDate fechaModificacion;

    private Integer estadoAdjunto;

    private String adjuntoProyectoFase;

    private String nombreArchivoOriginal;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Lob
    private byte[] archivo;

    private String archivoContentType;

    private Long proyectoFaseProyectoId;

    private String proyectoFaseProyectoTitulo;

    private Long adjuntoProyectoFaseFaseId;

    private String adjuntoProyectoFaseFaseFase;

   //Campo para el mapeo del archivo
    private String file;

    private String urlFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getEstadoAdjunto() {
        return estadoAdjunto;
    }

    public void setEstadoAdjunto(Integer estadoAdjunto) {
        this.estadoAdjunto = estadoAdjunto;
    }

    public String getAdjuntoProyectoFase() {
        return adjuntoProyectoFase;
    }

    public void setAdjuntoProyectoFase(String adjuntoProyectoFase) {
        this.adjuntoProyectoFase = adjuntoProyectoFase;
    }

    public String getNombreArchivoOriginal() {
        return nombreArchivoOriginal;
    }

    public void setNombreArchivoOriginal(String nombreArchivoOriginal) {
        this.nombreArchivoOriginal = nombreArchivoOriginal;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }


    public String getArchivoContentType() {
        return archivoContentType;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public Long getProyectoFaseProyectoId() {
        return proyectoFaseProyectoId;
    }

    public void setProyectoFaseProyectoId(Long proyectoId) {
        this.proyectoFaseProyectoId = proyectoId;
    }

    public String getProyectoFaseProyectoTitulo() {
        return proyectoFaseProyectoTitulo;
    }

    public void setProyectoFaseProyectoTitulo(String proyectoTitulo) {
        this.proyectoFaseProyectoTitulo = proyectoTitulo;
    }

    public Long getAdjuntoProyectoFaseFaseId() {
        return adjuntoProyectoFaseFaseId;
    }

    public void setAdjuntoProyectoFaseFaseId(Long fasesId) {
        this.adjuntoProyectoFaseFaseId = fasesId;
    }

    public String getAdjuntoProyectoFaseFaseFase() {
        return adjuntoProyectoFaseFaseFase;
    }

    public void setAdjuntoProyectoFaseFaseFase(String fasesFase) {
        this.adjuntoProyectoFaseFaseFase = fasesFase;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjuntoProyectoFaseDTO)) {
            return false;
        }

        return id != null && id.equals(((AdjuntoProyectoFaseDTO) o).id);
    }

   /* @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjuntoProyectoFaseDTO{" +
            "id=" + getId() +
            ", nombreAdjunto='" + getNombreAdjunto() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", estadoAdjunto=" + getEstadoAdjunto() +
            ", adjuntoProyectoFase='" + getAdjuntoProyectoFase() + "'" +
            ", nombreArchivoOriginal='" + getNombreArchivoOriginal() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            ", proyectoFaseProyectoId=" + getProyectoFaseProyectoId() +
            ", proyectoFaseProyectoTitulo='" + getProyectoFaseProyectoTitulo() + "'" +
            ", adjuntoProyectoFaseFaseId=" + getAdjuntoProyectoFaseFaseId() +
            ", adjuntoProyectoFaseFaseFase='" + getAdjuntoProyectoFaseFaseFase() + "'" +
            "}";
    }*/
    /*
   @Override
   public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("AdjuntoProyectoFaseDTO [id=");
       builder.append(id);
       builder.append(", nombreAdjunto=");
       builder.append(nombreAdjunto);
       builder.append(", fechaCreacion=");
       builder.append(fechaCreacion);
       builder.append(", fechaModificacion=");
       builder.append(fechaModificacion);
       builder.append(", estadoAdjunto=");
       builder.append(estadoAdjunto);
       builder.append(", adjuntoProyectoFase=");
       builder.append(adjuntoProyectoFase);
       builder.append(", nombreArchivoOriginal=");
       builder.append(nombreArchivoOriginal);
       builder.append(", fechaInicio=");
       builder.append(fechaInicio);
       builder.append(", fechaFin=");
       builder.append(fechaFin);
       builder.append(", archivo=");
       builder.append(Arrays.toString(archivo));
       builder.append(", archivoContentType=");
       builder.append(archivoContentType);

       builder.append(", proyectoFaseProyectoId=");
       builder.append(proyectoFaseProyectoId);
       builder.append(", proyectoFaseProyectoTitulo=");
       builder.append(proyectoFaseProyectoTitulo);
       builder.append(", adjuntoProyectoFaseFaseId=");
       builder.append(adjuntoProyectoFaseFaseId);

       builder.append(", adjuntoProyectoFaseFaseFase=");
       builder.append(adjuntoProyectoFaseFaseFase);



       builder.append(", file=");
       builder.append(file);
       builder.append(", urlFile=");
       builder.append(urlFile);
       builder.append("]");
       return builder.toString();
   }
*/



    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjuntoProyectoFaseDTO{" +
            "id=" + getId() +

            ", nombreAdjunto='" + getNombreAdjunto() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", estadoAdjunto=" + getEstadoAdjunto() +
            ", adjuntoProyectoFase='" + getAdjuntoProyectoFase() + "'" +
            ", nombreArchivoOriginal='" + getNombreArchivoOriginal() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            ", adjuntoProyectoFaseFaseFase='" + getAdjuntoProyectoFaseFaseFase() + "'" +
            ", proyectoFaseProyectoId=" + getProyectoFaseProyectoId() +
            ", proyectoFaseProyectoTitulo='" + getProyectoFaseProyectoTitulo() + "'" +
            ", adjuntoProyectoFaseFaseId=" + getAdjuntoProyectoFaseFaseId() +
            "}";
    }

}
