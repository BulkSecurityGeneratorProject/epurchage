package com.softage.epurchase.repository;

import com.softage.epurchase.domain.ReqItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReqItem entity.
 */
@SuppressWarnings("unused")
public interface ReqItemRepository extends JpaRepository<ReqItem,Long> {

}
