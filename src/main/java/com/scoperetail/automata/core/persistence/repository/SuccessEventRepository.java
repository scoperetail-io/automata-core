package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.entity.SuccessEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/** @author scoperetail */
@Repository
public interface SuccessEventRepository extends JpaRepository<SuccessEvent, Long> {

  @Query(name = "DELETE_SUCCESS_EVENTS")
  @Modifying
  @Transactional
  Integer deleteSuccessEvents(@Param("keyList") List<String> keyList);
}
