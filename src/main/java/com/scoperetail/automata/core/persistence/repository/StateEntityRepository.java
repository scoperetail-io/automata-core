package com.scoperetail.automata.core.persistence.repository;

/*-
 * *****
 * automata-core
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

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
