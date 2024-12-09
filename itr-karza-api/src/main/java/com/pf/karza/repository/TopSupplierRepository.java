package com.pf.karza.repository;

import com.pf.karza.model.entity.TopSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopSupplierRepository extends JpaRepository<TopSupplier, Long> {
}
