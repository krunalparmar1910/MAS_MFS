package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.FilingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilingDetailRepository extends JpaRepository<FilingDetail, Long> {
    @Query(value = "select fd from FilingDetail fd where fd.clientOrder.id = ?1 and (fd.returnType = ?2 or fd.fieldGroup.fieldGroupName LIKE %?2%)")
    List<FilingDetail> findGST3RBFilingsByClientOrderId(Long clientOrderId, String returnType);
}
