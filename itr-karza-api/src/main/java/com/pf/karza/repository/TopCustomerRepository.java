package com.pf.karza.repository;

import com.pf.karza.model.entity.TopCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopCustomerRepository extends JpaRepository<TopCustomer, Long> {
}
