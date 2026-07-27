package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Ciclo} entity.
 */
public class CicloDTO implements Serializable {

    private Long id;

    private String ciclo;

    private Set<Long> modalidadIds = new HashSet<>();

    private Set<String> modalidadNombres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Set<Long> getModalidadIds() {
        return modalidadIds;
    }

    public void setModalidadIds(Set<Long> modalidadIds) {
        this.modalidadIds = modalidadIds;
    }

    public Set<String> getModalidadNombres() {
        return modalidadNombres;
    }

    public void setModalidadNombres(Set<String> modalidadNombres) {
        this.modalidadNombres = modalidadNombres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CicloDTO)) {
            return false;
        }

        return id != null && id.equals(((CicloDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CicloDTO{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            "}";
    }
}
