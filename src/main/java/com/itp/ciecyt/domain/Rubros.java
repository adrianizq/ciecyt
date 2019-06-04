package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rubros.
 */
@Entity
@Table(name = "rubros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rubros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rubro")
    private String rubro;

    @OneToMany(mappedBy = "rubros")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PresupuestoValor> presupuestoValors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public Rubros rubro(String rubro) {
        this.rubro = rubro;
        return this;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public Set<PresupuestoValor> getPresupuestoValors() {
        return presupuestoValors;
    }

    public Rubros presupuestoValors(Set<PresupuestoValor> presupuestoValors) {
        this.presupuestoValors = presupuestoValors;
        return this;
    }

    public Rubros addPresupuestoValor(PresupuestoValor presupuestoValor) {
        this.presupuestoValors.add(presupuestoValor);
        presupuestoValor.setRubros(this);
        return this;
    }

    public Rubros removePresupuestoValor(PresupuestoValor presupuestoValor) {
        this.presupuestoValors.remove(presupuestoValor);
        presupuestoValor.setRubros(null);
        return this;
    }

    public void setPresupuestoValors(Set<PresupuestoValor> presupuestoValors) {
        this.presupuestoValors = presupuestoValors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rubros)) {
            return false;
        }
        return id != null && id.equals(((Rubros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rubros{" +
            "id=" + getId() +
            ", rubro='" + getRubro() + "'" +
            "}";
    }
}
