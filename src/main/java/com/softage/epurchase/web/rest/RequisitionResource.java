package com.softage.epurchase.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softage.epurchase.domain.Requisition;

import com.softage.epurchase.repository.RequisitionRepository;
import com.softage.epurchase.repository.search.RequisitionSearchRepository;
import com.softage.epurchase.web.rest.util.HeaderUtil;
import com.softage.epurchase.service.dto.RequisitionDTO;
import com.softage.epurchase.service.mapper.RequisitionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Requisition.
 */
@RestController
@RequestMapping("/api")
public class RequisitionResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionResource.class);
        
    @Inject
    private RequisitionRepository requisitionRepository;

    @Inject
    private RequisitionMapper requisitionMapper;

    @Inject
    private RequisitionSearchRepository requisitionSearchRepository;

    /**
     * POST  /requisitions : Create a new requisition.
     *
     * @param requisitionDTO the requisitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requisitionDTO, or with status 400 (Bad Request) if the requisition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requisitions")
    @Timed
    public ResponseEntity<RequisitionDTO> createRequisition(@RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to save Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("requisition", "idexists", "A new requisition cannot already have an ID")).body(null);
        }
        Requisition requisition = requisitionMapper.requisitionDTOToRequisition(requisitionDTO);
        requisition = requisitionRepository.save(requisition);
        RequisitionDTO result = requisitionMapper.requisitionToRequisitionDTO(requisition);
        requisitionSearchRepository.save(requisition);
        return ResponseEntity.created(new URI("/api/requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("requisition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requisitions : Updates an existing requisition.
     *
     * @param requisitionDTO the requisitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requisitionDTO,
     * or with status 400 (Bad Request) if the requisitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the requisitionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requisitions")
    @Timed
    public ResponseEntity<RequisitionDTO> updateRequisition(@RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to update Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() == null) {
            return createRequisition(requisitionDTO);
        }
        Requisition requisition = requisitionMapper.requisitionDTOToRequisition(requisitionDTO);
        requisition = requisitionRepository.save(requisition);
        RequisitionDTO result = requisitionMapper.requisitionToRequisitionDTO(requisition);
        requisitionSearchRepository.save(requisition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("requisition", requisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requisitions : get all the requisitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requisitions in body
     */
    @GetMapping("/requisitions")
    @Timed
    public List<RequisitionDTO> getAllRequisitions() {
        log.debug("REST request to get all Requisitions");
        List<Requisition> requisitions = requisitionRepository.findAll();
        return requisitionMapper.requisitionsToRequisitionDTOs(requisitions);
    }

    /**
     * GET  /requisitions/:id : get the "id" requisition.
     *
     * @param id the id of the requisitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requisitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/requisitions/{id}")
    @Timed
    public ResponseEntity<RequisitionDTO> getRequisition(@PathVariable Long id) {
        log.debug("REST request to get Requisition : {}", id);
        Requisition requisition = requisitionRepository.findOne(id);
        RequisitionDTO requisitionDTO = requisitionMapper.requisitionToRequisitionDTO(requisition);
        return Optional.ofNullable(requisitionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /requisitions/:id : delete the "id" requisition.
     *
     * @param id the id of the requisitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requisitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequisition(@PathVariable Long id) {
        log.debug("REST request to delete Requisition : {}", id);
        requisitionRepository.delete(id);
        requisitionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("requisition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/requisitions?query=:query : search for the requisition corresponding
     * to the query.
     *
     * @param query the query of the requisition search 
     * @return the result of the search
     */
    @GetMapping("/_search/requisitions")
    @Timed
    public List<RequisitionDTO> searchRequisitions(@RequestParam String query) {
        log.debug("REST request to search Requisitions for query {}", query);
        return StreamSupport
            .stream(requisitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(requisitionMapper::requisitionToRequisitionDTO)
            .collect(Collectors.toList());
    }


}
