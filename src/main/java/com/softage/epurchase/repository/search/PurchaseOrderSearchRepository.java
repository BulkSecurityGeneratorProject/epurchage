package com.softage.epurchase.repository.search;

import com.softage.epurchase.domain.PurchaseOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PurchaseOrder entity.
 */
public interface PurchaseOrderSearchRepository extends ElasticsearchRepository<PurchaseOrder, Long> {
}
