package com.niche.jhipster.service.impl;

import com.niche.jhipster.service.ZonalService;
import com.niche.jhipster.domain.Zonal;
import com.niche.jhipster.repository.ZonalRepository;
import com.niche.jhipster.service.dto.ZonalDTO;
import com.niche.jhipster.service.mapper.ZonalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Zonal.
 */
@Service
@Transactional
public class ZonalServiceImpl implements ZonalService {

    private final Logger log = LoggerFactory.getLogger(ZonalServiceImpl.class);

    private final ZonalRepository zonalRepository;

    private final ZonalMapper zonalMapper;

    public ZonalServiceImpl(ZonalRepository zonalRepository, ZonalMapper zonalMapper) {
        this.zonalRepository = zonalRepository;
        this.zonalMapper = zonalMapper;
    }

    /**
     * Save a zonal.
     *
     * @param zonalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ZonalDTO save(ZonalDTO zonalDTO) {
        log.debug("Request to save Zonal : {}", zonalDTO);
        Zonal zonal = zonalMapper.toEntity(zonalDTO);
        zonal = zonalRepository.save(zonal);
        return zonalMapper.toDto(zonal);
    }

    /**
     * Get all the zonals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZonalDTO> findAll() {
        log.debug("Request to get all Zonals");
        return zonalRepository.findAll().stream()
            .map(zonalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one zonal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZonalDTO> findOne(Long id) {
        log.debug("Request to get Zonal : {}", id);
        return zonalRepository.findById(id)
            .map(zonalMapper::toDto);
    }

    /**
     * Delete the zonal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zonal : {}", id);
        zonalRepository.deleteById(id);
    }
}
