package com.softage.epurchase.repository.search;

import com.softage.epurchase.domain.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Vendor entity.
 */
public interface VendorSearchRepository extends ElasticsearchRepository<Vendor, Long> {
}
