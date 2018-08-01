package com.niche.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.niche.jhipster.service.ZonalService;
import com.niche.jhipster.web.rest.errors.BadRequestAlertException;
import com.niche.jhipster.web.rest.util.HeaderUtil;
import com.niche.jhipster.service.dto.ZonalDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Zonal.
 */
@RestController
@RequestMapping("/api")
public class ZonalResource {

    private final Logger log = LoggerFactory.getLogger(ZonalResource.class);

    private static final String ENTITY_NAME = "zonal";

    private final ZonalService zonalService;

    public ZonalResource(ZonalService zonalService) {
        this.zonalService = zonalService;
    }

    /**
     * POST  /zonals : Create a new zonal.
     *
     * @param zonalDTO the zonalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zonalDTO, or with status 400 (Bad Request) if the zonal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zonals")
    @Timed
    public ResponseEntity<ZonalDTO> createZonal(@Valid @RequestBody ZonalDTO zonalDTO) throws URISyntaxException {
        log.debug("REST request to save Zonal : {}", zonalDTO);
        if (zonalDTO.getId() != null) {
            throw new BadRequestAlertException("A new zonal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZonalDTO result = zonalService.save(zonalDTO);
        return ResponseEntity.created(new URI("/api/zonals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zonals : Updates an existing zonal.
     *
     * @param zonalDTO the zonalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zonalDTO,
     * or with status 400 (Bad Request) if the zonalDTO is not valid,
     * or with status 500 (Internal Server Error) if the zonalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zonals")
    @Timed
    public ResponseEntity<ZonalDTO> updateZonal(@Valid @RequestBody ZonalDTO zonalDTO) throws URISyntaxException {
        log.debug("REST request to update Zonal : {}", zonalDTO);
        if (zonalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZonalDTO result = zonalService.save(zonalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zonalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zonals : get all the zonals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zonals in body
     */
    @GetMapping("/zonals")
    @Timed
    public List<ZonalDTO> getAllZonals() {
        log.debug("REST request to get all Zonals");
        return zonalService.findAll();
    }

    /**
     * GET  /zonals/:id : get the "id" zonal.
     *
     * @param id the id of the zonalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zonalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zonals/{id}")
    @Timed
    public ResponseEntity<ZonalDTO> getZonal(@PathVariable Long id) {
        log.debug("REST request to get Zonal : {}", id);
        Optional<ZonalDTO> zonalDTO = zonalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zonalDTO);
    }

    /**
     * DELETE  /zonals/:id : delete the "id" zonal.
     *
     * @param id the id of the zonalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zonals/{id}")
    @Timed
    public ResponseEntity<Void> deleteZonal(@PathVariable Long id) {
        log.debug("REST request to delete Zonal : {}", id);
        zonalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
