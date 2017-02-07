package com.softage.epurchase.repository;

import com.softage.epurchase.domain.Vendor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Vendor entity.
 */
@SuppressWarnings("unused")
public interface VendorRepository extends JpaRepository<Vendor,Long> {

    @Query("select distinct vendor from Vendor vendor left join fetch vendor.items")
    List<Vendor> findAllWithEagerRelationships();

    @Query("select vendor from Vendor vendor left join fetch vendor.items where vendor.id =:id")
    Vendor findOneWithEagerRelationships(@Param("id") Long id);

}
