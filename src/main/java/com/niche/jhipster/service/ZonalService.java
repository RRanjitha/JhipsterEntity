package com.niche.jhipster.service;

import com.niche.jhipster.service.dto.ZonalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Zonal.
 */
public interface ZonalService {

    /**
     * Save a zonal.
     *
     * @param zonalDTO the entity to save
     * @return the persisted entity
     */
    ZonalDTO save(ZonalDTO zonalDTO);

    /**
     * Get all the zonals.
     *
     * @return the list of entities
     */
    List<ZonalDTO> findAll();


    /**
     * Get the "id" zonal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ZonalDTO> findOne(Long id);

    /**
     * Delete the "id" zonal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
