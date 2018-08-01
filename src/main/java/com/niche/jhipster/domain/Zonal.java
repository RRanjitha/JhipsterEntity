package com.niche.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Zonal.
 */
@Entity
@Table(name = "zonal")
public class Zonal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_date")
    private Instant date;

    @OneToMany(mappedBy = "zonal")
    private Set<Sector> zonalSectors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Zonal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDate() {
        return date;
    }

    public Zonal date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<Sector> getZonalSectors() {
        return zonalSectors;
    }

    public Zonal zonalSectors(Set<Sector> sectors) {
        this.zonalSectors = sectors;
        return this;
    }

    public Zonal addZonalSector(Sector sector) {
        this.zonalSectors.add(sector);
        sector.setZonal(this);
        return this;
    }

    public Zonal removeZonalSector(Sector sector) {
        this.zonalSectors.remove(sector);
        sector.setZonal(null);
        return this;
    }

    public void setZonalSectors(Set<Sector> sectors) {
        this.zonalSectors = sectors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Zonal zonal = (Zonal) o;
        if (zonal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zonal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Zonal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
