package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/** @author scoperetail */
@Repository
public interface PendingEventRepository extends JpaRepository<PendingEvent, Long> {

  @Query(name = "FIND_BY_KEY_AND_SORT_BY_CREAT_TS")
  List<PendingEvent> findByKeySortByCreateTS(@Param("key") final String key);

  @Query(name = "DELETE_PENDING_EVENTS")
  @Modifying
  @Transactional
  Integer deletePendingEvents(@Param("keyList") List<String> keyList);
}
