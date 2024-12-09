package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.ProfileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileDetailRepository extends JpaRepository<ProfileDetail, Long> {
    List<ProfileDetail> findByClientOrderId(Long clientOrderId);
}
