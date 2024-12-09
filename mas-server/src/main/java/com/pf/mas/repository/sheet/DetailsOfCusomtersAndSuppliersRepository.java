package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.DetailsOfCustomersAndSuppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsOfCusomtersAndSuppliersRepository extends JpaRepository<DetailsOfCustomersAndSuppliers, Long> {
}
