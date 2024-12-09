package com.pf.mas.repository;

import com.pf.mas.model.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    /**
     * Get only single reports linked to the entity id, added clause for client order id
     * is not null otherwise query result returns the consolidated report for the entity id as well.
     * @param entityId
     * @return
     */
    List<ClientOrder> findByEntityIdAndClientOrderIdIsNotNull(String entityId);

    ClientOrder findByEntityIdAndClientOrderIdIsNull(String entityId);

    ClientOrder findByClientOrderId(String clientOrderId);

    ClientOrder findByEntityIdAndClientOrderId(String entityId, String clientOrderId);
}
