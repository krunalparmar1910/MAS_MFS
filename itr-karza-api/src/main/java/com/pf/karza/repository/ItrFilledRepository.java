package com.pf.karza.repository;

import com.pf.karza.model.entity.FillingHistory;
import com.pf.karza.model.entity.itrfilled.ItrFilled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItrFilledRepository extends JpaRepository<ItrFilled, Long> {
}
