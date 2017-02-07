package com.softage.epurchase.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softage.epurchase.domain.ReqItem;

import com.softage.epurchase.repository.ReqItemRepository;
import com.softage.epurchase.repository.search.ReqItemSearchRepository;
import com.softage.epurchase.web.rest.util.HeaderUtil;
import com.softage.epurchase.service.dto.ReqItemDTO;
import com.softage.epurchase.service.mapper.ReqItemMapper;

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
 * REST controller for managing ReqItem.
 */
@RestController
@RequestMapping("/api")
public class ReqItemResource {

    private final Logger log = LoggerFactory.getLogger(ReqItemResource.class);
        
    @Inject
    private ReqItemRepository reqItemRepository;

    @Inject
    private ReqItemMapper reqItemMapper;

    @Inject
    private ReqItemSearchRepository reqItemSearchRepository;

    /**
     * POST  /req-items : Create a new reqItem.
     *
     * @param reqItemDTO the reqItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reqItemDTO, or with status 400 (Bad Request) if the reqItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/req-items")
    @Timed
    public ResponseEntity<ReqItemDTO> createReqItem(@RequestBody ReqItemDTO reqItemDTO) throws URISyntaxException {
        log.debug("REST request to save ReqItem : {}", reqItemDTO);
        if (reqItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reqItem", "idexists", "A new reqItem cannot already have an ID")).body(null);
        }
        ReqItem reqItem = reqItemMapper.reqItemDTOToReqItem(reqItemDTO);
        reqItem = reqItemRepository.save(reqItem);
        ReqItemDTO result = reqItemMapper.reqItemToReqItemDTO(reqItem);
        reqItemSearchRepository.save(reqItem);
        return ResponseEntity.created(new URI("/api/req-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reqItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /req-items : Updates an existing reqItem.
     *
     * @param reqItemDTO the reqItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reqItemDTO,
     * or with status 400 (Bad Request) if the reqItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the reqItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/req-items")
    @Timed
    public ResponseEntity<ReqItemDTO> updateReqItem(@RequestBody ReqItemDTO reqItemDTO) throws URISyntaxException {
        log.debug("REST request to update ReqItem : {}", reqItemDTO);
        if (reqItemDTO.getId() == null) {
            return createReqItem(reqItemDTO);
        }
        ReqItem reqItem = reqItemMapper.reqItemDTOToReqItem(reqItemDTO);
        reqItem = reqItemRepository.save(reqItem);
        ReqItemDTO result = reqItemMapper.reqItemToReqItemDTO(reqItem);
        reqItemSearchRepository.save(reqItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reqItem", reqItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /req-items : get all the reqItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reqItems in body
     */
    @GetMapping("/req-items")
    @Timed
    public List<ReqItemDTO> getAllReqItems() {
        log.debug("REST request to get all ReqItems");
        List<ReqItem> reqItems = reqItemRepository.findAll();
        return reqItemMapper.reqItemsToReqItemDTOs(reqItems);
    }

    /**
     * GET  /req-items/:id : get the "id" reqItem.
     *
     * @param id the id of the reqItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reqItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/req-items/{id}")
    @Timed
    public ResponseEntity<ReqItemDTO> getReqItem(@PathVariable Long id) {
        log.debug("REST request to get ReqItem : {}", id);
        ReqItem reqItem = reqItemRepository.findOne(id);
        ReqItemDTO reqItemDTO = reqItemMapper.reqItemToReqItemDTO(reqItem);
        return Optional.ofNullable(reqItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /req-items/:id : delete the "id" reqItem.
     *
     * @param id the id of the reqItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/req-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteReqItem(@PathVariable Long id) {
        log.debug("REST request to delete ReqItem : {}", id);
        reqItemRepository.delete(id);
        reqItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reqItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/req-items?query=:query : search for the reqItem corresponding
     * to the query.
     *
     * @param query the query of the reqItem search 
     * @return the result of the search
     */
    @GetMapping("/_search/req-items")
    @Timed
    public List<ReqItemDTO> searchReqItems(@RequestParam String query) {
        log.debug("REST request to search ReqItems for query {}", query);
        return StreamSupport
            .stream(reqItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reqItemMapper::reqItemToReqItemDTO)
            .collect(Collectors.toList());
    }


}
