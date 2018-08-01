package com.niche.jhipster.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sector entity.
 */
public class SectorDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Instant date;

    private String content;

    private Long zonalId;

    private String zonalName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getZonalId() {
        return zonalId;
    }

    public void setZonalId(Long zonalId) {
        this.zonalId = zonalId;
    }

    public String getZonalName() {
        return zonalName;
    }

    public void setZonalName(String zonalName) {
        this.zonalName = zonalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SectorDTO sectorDTO = (SectorDTO) o;
        if (sectorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", content='" + getContent() + "'" +
            ", zonal=" + getZonalId() +
            ", zonal='" + getZonalName() + "'" +
            "}";
    }
}
