package com.softage.epurchase.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.softage.epurchase.domain.Vendor;

import com.softage.epurchase.repository.VendorRepository;
import com.softage.epurchase.repository.search.VendorSearchRepository;
import com.softage.epurchase.web.rest.util.HeaderUtil;
import com.softage.epurchase.service.dto.VendorDTO;
import com.softage.epurchase.service.mapper.VendorMapper;

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
 * REST controller for managing Vendor.
 */
@RestController
@RequestMapping("/api")
public class VendorResource {

    private final Logger log = LoggerFactory.getLogger(VendorResource.class);
        
    @Inject
    private VendorRepository vendorRepository;

    @Inject
    private VendorMapper vendorMapper;

    @Inject
    private VendorSearchRepository vendorSearchRepository;

    /**
     * POST  /vendors : Create a new vendor.
     *
     * @param vendorDTO the vendorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendorDTO, or with status 400 (Bad Request) if the vendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendors")
    @Timed
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendorDTO);
        if (vendorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vendor", "idexists", "A new vendor cannot already have an ID")).body(null);
        }
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor = vendorRepository.save(vendor);
        VendorDTO result = vendorMapper.vendorToVendorDTO(vendor);
        vendorSearchRepository.save(vendor);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vendor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendors : Updates an existing vendor.
     *
     * @param vendorDTO the vendorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendorDTO,
     * or with status 400 (Bad Request) if the vendorDTO is not valid,
     * or with status 500 (Internal Server Error) if the vendorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendors")
    @Timed
    public ResponseEntity<VendorDTO> updateVendor(@RequestBody VendorDTO vendorDTO) throws URISyntaxException {
        log.debug("REST request to update Vendor : {}", vendorDTO);
        if (vendorDTO.getId() == null) {
            return createVendor(vendorDTO);
        }
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor = vendorRepository.save(vendor);
        VendorDTO result = vendorMapper.vendorToVendorDTO(vendor);
        vendorSearchRepository.save(vendor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vendor", vendorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendors : get all the vendors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vendors in body
     */
    @GetMapping("/vendors")
    @Timed
    public List<VendorDTO> getAllVendors() {
        log.debug("REST request to get all Vendors");
        List<Vendor> vendors = vendorRepository.findAllWithEagerRelationships();
        return vendorMapper.vendorsToVendorDTOs(vendors);
    }

    /**
     * GET  /vendors/:id : get the "id" vendor.
     *
     * @param id the id of the vendorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Long id) {
        log.debug("REST request to get Vendor : {}", id);
        Vendor vendor = vendorRepository.findOneWithEagerRelationships(id);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        return Optional.ofNullable(vendorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vendors/:id : delete the "id" vendor.
     *
     * @param id the id of the vendorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        log.debug("REST request to delete Vendor : {}", id);
        vendorRepository.delete(id);
        vendorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vendor", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vendors?query=:query : search for the vendor corresponding
     * to the query.
     *
     * @param query the query of the vendor search 
     * @return the result of the search
     */
    @GetMapping("/_search/vendors")
    @Timed
    public List<VendorDTO> searchVendors(@RequestParam String query) {
        log.debug("REST request to search Vendors for query {}", query);
        return StreamSupport
            .stream(vendorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vendorMapper::vendorToVendorDTO)
            .collect(Collectors.toList());
    }


}
