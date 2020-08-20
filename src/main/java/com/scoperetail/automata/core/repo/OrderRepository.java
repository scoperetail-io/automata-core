package com.scoperetail.automata.core.repo;

import com.scoperetail.automata.core.model.StateEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

/** @author scoperetail */
@Repository
public interface OrderRepository extends JpaRepository<StateEntity, Long> {

  StateEntity findByKey(final String key);

  @Query(name = "GET_ORDER_ENTITY_TO_ERASE")
  List<String> getOrdersToErase(
      @Param("pivoteDateTime") Timestamp pivoteDateTime,
      @Param("validStateNameForDelete") List<String> validStateNameForDelete,
      Pageable pageable);

  @Query(name = "DELETE_ORDER_ENTITY")
  @Modifying
  @Transactional
  Integer deleteOrders(@Param("orderIdList") List<String> orderIdList);
}
