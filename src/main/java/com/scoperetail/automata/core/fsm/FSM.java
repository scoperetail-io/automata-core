package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.model.Event;
import com.scoperetail.automata.core.model.RejectedEvent;
import com.scoperetail.automata.core.model.StateEntity;
import com.scoperetail.automata.core.model.SuccessEvent;
import com.scoperetail.automata.core.service.EventService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/** @author scoperetail */
@Slf4j
public class FSM {
  private String name;
  private Object automata;
  private Class<?> clazz;
  private FSMService serviceImpl;
  private EventService eventService;
  private Map<String, State> states = new HashMap<>();
  private Map<String, Event> events = new HashMap<>();
  private List<String> endStates;
  private String startState;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getAutomata() {
    return automata;
  }

  public void setAutomata(Object automata) {
    this.automata = automata;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Map<String, State> getStates() {
    return states;
  }

  public void setStates(Map<String, State> states) {
    this.states = states;
  }

  public Map<String, Event> getEvents() {
    return events;
  }

  public void setEvents(Map<String, Event> events) {
    this.events = events;
  }

  public FSMService getServiceImpl() {
    return serviceImpl;
  }

  public void setServiceImpl(FSMService serviceImpl) {
    this.serviceImpl = serviceImpl;
  }

  public List<String> getEndStates() {
    return endStates;
  }

  public void setEndStates(List<String> endStates) {
    this.endStates = endStates;
  }

  public void addEndState(String endState) {
    if (this.endStates == null) {
      this.endStates = new ArrayList<>();
    }
    this.endStates.add(endState);
  }

  public String getStartState() {
    return startState;
  }

  public void setStartState(String startState) {
    this.startState = startState;
  }

  public EventService getEventService() {
    return eventService;
  }

  public void setEventService(EventService eventService) {
    this.eventService = eventService;
  }
  /**
   * @param stateful
   * @param onEvent
   */
  public void onEvent(StateEntity stateful, Event onEvent) {
    if (onEvent.getAutomataName() != null
        && onEvent.getAutomataName() != ""
        && stateful.getAutomataType() != null
        && !onEvent.getAutomataName().equals(stateful.getAutomataType())) {
      // discard this event due to order state and event type mismatch
      saveToRejected(onEvent);
      return;
    }

    boolean eventIsProcessed = processEvent(stateful, onEvent);
    // TODO remove unnecessary abstract class stateful

    // stateful entity at this point is:
    // 1. a new event which is just persisted as start state
    // 2. a new event which has already made first transition
    // 3. old event which is still stuck in state
    // 4. old event which has made n transitions
    if (eventIsProcessed) {
      applyParkedEvents(stateful);
    }
  }

  // For maintaining cohesiveness of event processing
  // Allowing commented code to make reverting back easier
  @SuppressWarnings({"squid:S3776", "squid:CommentedOutCodeLine"})
  private boolean processEvent(StateEntity stateful, Event onEvent) {
    boolean retVal = false;
    log.trace("FSM:{} will processEvent event: {} on state: {}", this.getName(), onEvent, stateful);
    Long id = stateful.getId();
    if (id == null || this.getServiceImpl().findEntity(id) == null) {
      // this is new entity
      stateful.setStateName(this.getStartState());
      Timestamp ts = new Timestamp(System.currentTimeMillis());
      stateful.setCreateTS(ts);
      stateful.setUpdateTS(ts);
      this.getServiceImpl().saveEntity(stateful);
    }
    State currentState = getState(stateful.getStateName());

    if (currentState == null) {
      log.trace("FSM:{} state, event will be discarded: {}", this.getName(), onEvent);
      // Discard the event and save it in rejected bucket
      // this is a rogue event, state of order has no transition defined for this event
      saveToRejected(onEvent);
      retVal = true;
    } else if (currentState.isValidEvent(onEvent)) {
      log.trace("FSM:{} found valid event", this.getName());
      // check if conditions are met
      if (checkPreCondition(currentState.getPreCondition(onEvent), stateful, onEvent)) {
        log.trace("FSM:{} precondition success", this.getName());
        if (checkPreAction(currentState.getPreAction(onEvent), stateful, onEvent)) {
          log.trace("FSM:{} preaction success", this.getName());
          String toState = currentState.getToState(onEvent);
          if (toState == null) {
            log.error("FSM:{} next state for: {} not found", this.getName(), stateful);
            return false;
          }
          // If the entity reaches the end state, mark it for soft delete
          State s = getState(toState);
          if (s != null) {
            boolean isEndState = s.isEndState();
            if (isEndState) {
              stateful.setDeleteTS(new Timestamp(System.currentTimeMillis()));
            }
          }
          stateful.setStateName(toState);

          log.trace("FSM:{} state for: {} updated to: {}", this.getName(), stateful, toState);
          stateful.setUpdateTS(new Timestamp(System.currentTimeMillis()));
          this.getServiceImpl().updateState(stateful.getId(), toState);
          // If this was a pending message, move it to success
          if (onEvent.getId() > 0) {
            // if this is old persisted event which should be moved to success bucket
            log.trace(
                "FSM:{} state event will be moved to success list after processed event: {}",
                this.getName(),
                onEvent);
            // copy this to success event bucket
            onEvent.setDeleteTS(new Timestamp(System.currentTimeMillis()));
            this.eventService.save(SuccessEvent.of(onEvent));
            // For performance reason, we are doing a soft delete in place of actual delete
            // delete timestamp is populated for purpose of soft delete
            // this.eventService.deleteById(onEvent.getId());
            this.eventService.save(onEvent);
            retVal = true;
          } else {
            log.trace(
                "FSM:{} event will be saved to success list after processed event: {}",
                this.getName(),
                onEvent);
            onEvent.setCreateTS(new Timestamp(System.currentTimeMillis()));
            onEvent.setDeleteTS(new Timestamp(System.currentTimeMillis()));
            this.eventService.save(SuccessEvent.of(onEvent));
            retVal = true;
          }
        }
      }
    } else {
      if (currentState.isFutureEvent(onEvent)) {
        // Persist the event for a retry after next state transition
        if (onEvent.getId() <= 0) {
          log.trace("FSM:{} state, a future event will be parked: {}", this.getName(), onEvent);
          onEvent.setCreateTS(new Timestamp(System.currentTimeMillis()));
          onEvent.setRetryCount(onEvent.getRetryCount() + 1);
          this.eventService.save(onEvent);
          log.trace(
              "FSM:{} state, a future event successfully parked: {}", this.getName(), onEvent);
        } else {
          this.eventService.incrementRetry(onEvent);
          log.trace("FSM:{} state, parked event still pending: {}", this.getName(), onEvent);
        }
      } else {
        if ((onEvent.getId() > 0)) {
          // this is coming from persistence
          // copy to rejected bucket
          onEvent.setDeleteTS(new Timestamp(System.currentTimeMillis()));
          this.eventService.save(RejectedEvent.of(onEvent));
          // delete this event
          // For performance reason, we are doing a soft delete in place of actual delete
          // delete timestamp is populated for purpose of soft delete
          this.eventService.save(onEvent);
          // this.eventService.deleteById(onEvent.getId());
        } else {
          log.trace("FSM:{} state, event will be discarded: {}", this.getName(), onEvent);
          // Discard the event and save it in rejected bucket
          saveToRejected(onEvent);
        }
      }
    }
    return retVal;
  }

  private void saveToRejected(Event onEvent) {
    onEvent.setDeleteTS(new Timestamp(System.currentTimeMillis()));
    RejectedEvent rejectedEvent = RejectedEvent.of(onEvent);
    this.eventService.save(rejectedEvent);
  }

  private boolean checkPreCondition(
      Method preConditionMethod, StateEntity stateful, Event onEvent) {
    if (preConditionMethod != null) {
      try {
        return (Boolean) preConditionMethod.invoke(this.automata, stateful, onEvent);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        log.error("Error in checkPreCondition", e);
        return false;
      }
    } else {
      return true;
    }
  }

  private boolean checkPreAction(Method preActionMethod, StateEntity stateful, Event onEvent) {
    if (preActionMethod != null) {
      try {
        return (Boolean) preActionMethod.invoke(this.automata, stateful, onEvent);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        log.error("Error in checkPreAction", e);
        return false;
      }
    } else {
      return true;
    }
  }

  private State getState(String stateName) {
    return this.states.get(stateName);
  }

  private void applyParkedEvents(StateEntity stateEntity) {

    List<Event> pendingEvents = findEligibleParkedEvents(stateEntity.getKey());
    if (pendingEvents.size() > 1) {
      // We know that Future Events are stored in a LinkedHashSet, its safe to typecast
      pendingEvents =
          sortByEventOrdering(
              pendingEvents,
              (LinkedHashSet<String>)
                  (this.getState(stateEntity.getStateName())).getFutureEvents());
    }
    for (Event e : pendingEvents) {
      processEvent(stateEntity, e);
    }
  }

  private List<Event> findEligibleParkedEvents(final String key) {
    return this.eventService.findByKeySortByCreateTS(key);
  }

  /**
   * @param events
   * @param ordering
   * @return list of events sorted by the order they should be applied
   */
  public List<Event> sortByEventOrdering(List<Event> events, LinkedHashSet<String> ordering) {
    Comparator<Event> c =
        new Comparator<Event>() {
          final List<String> list = new ArrayList<>(ordering);

          @Override
          public int compare(Event o1, Event o2) {
            if (Objects.equals(o1, o2)) {
              return 0;
            } else if (list.indexOf(o1.getEventName()) > list.indexOf(o2.getEventName())) {
              return 1;
            } else {
              return -1;
            }
          }
        };

    Collections.sort(events, c);
    return events;
  }
}
