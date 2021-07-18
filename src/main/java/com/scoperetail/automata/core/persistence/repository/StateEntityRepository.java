package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.mapper.StateEventHistoryMapper;
import com.scoperetail.automata.core.persistence.entity.StateEntity;
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
public interface StateEntityRepository extends JpaRepository<StateEntity, Long> {

  StateEntity findByEntityId(final String entityId);

  @Query(name = "STATE.EVENT.HISTORY")
  List<StateEventHistoryMapper> getStateEventHistory(@Param("entityId") Long entityId);

  @Query(name = "GET_STATE_ENTITY_TO_ERASE")
  List<Integer> getEntityIdToErase(
      @Param("pivoteDateTime") Timestamp pivoteDateTime,
      @Param("validStateNameForDelete") List<String> validStateNameForDelete,
      Pageable pageable);

  @Query(name = "DELETE_STATE_ENTITY")
  @Modifying
  @Transactional
  Integer deleteStateEntity(@Param("entityIdList") List<Integer> entityIdList);
}
