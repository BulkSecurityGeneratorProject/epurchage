package com.softage.epurchase.repository.search;

import com.softage.epurchase.domain.ReqItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ReqItem entity.
 */
public interface ReqItemSearchRepository extends ElasticsearchRepository<ReqItem, Long> {
}
