package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Fases} entity.
 */
public class FasesDTO implements Serializable {
    
    private Long id;

    private String fase;

    private Boolean notificable;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Boolean isNotificable() {
        return notificable;
    }

    public void setNotificable(Boolean notificable) {
        this.notificable = notificable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FasesDTO)) {
            return false;
        }

        return id != null && id.equals(((FasesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FasesDTO{" +
            "id=" + getId() +
            ", fase='" + getFase() + "'" +
            ", notificable='" + isNotificable() + "'" +
            "}";
    }
}
