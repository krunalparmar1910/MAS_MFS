
package com.pf.karza.repository;

import com.pf.karza.model.entity.keypersonsinfo.KeyPersonsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyPersonsInfoRepository extends JpaRepository<KeyPersonsInfo, Long> {
}
