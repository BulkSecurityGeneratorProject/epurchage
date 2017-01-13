package com.softage.epurchase.repository.search;

import com.softage.epurchase.domain.Requisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Requisition entity.
 */
public interface RequisitionSearchRepository extends ElasticsearchRepository<Requisition, Long> {
}
