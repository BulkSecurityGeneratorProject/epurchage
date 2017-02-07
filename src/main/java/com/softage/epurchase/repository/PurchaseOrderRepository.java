package com.softage.epurchase.repository;

import com.softage.epurchase.domain.PurchaseOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

}
