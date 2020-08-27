package com.scoperetail.automata.core.persistence.repository;

import com.scoperetail.automata.core.persistence.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** @author scoperetail */
@Repository
public interface StateEntityRepository extends JpaRepository<StateEntity, Long> {

  StateEntity findByEntityId(final String entityId);
}
