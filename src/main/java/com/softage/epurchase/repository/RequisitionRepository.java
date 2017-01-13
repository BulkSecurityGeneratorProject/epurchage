package com.softage.epurchase.repository;

import com.softage.epurchase.domain.Requisition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Requisition entity.
 */
@SuppressWarnings("unused")
public interface RequisitionRepository extends JpaRepository<Requisition,Long> {

}
