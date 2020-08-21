package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/** @author scoperetail */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  Event findByKey(final String key);

  List<Event> findByKeySortByCreateTS(@Param("key") final String key);

  @Query(name = "DELETE_PENDING_EVENTS")
  @Modifying
  @Transactional
  Integer deletePendingEvents(@Param("keyList") List<String> keyList);
}
