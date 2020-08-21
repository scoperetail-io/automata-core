package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/** @author scoperetail */
@Repository
public interface RejectedEventRepository extends JpaRepository<RejectedEvent, Long> {

  @Query(name = "DELETE_REJECTED_EVENTS")
  @Modifying
  @Transactional
  Integer deleteRejectedEvents(@Param("keyList") List<String> keyList);
}
