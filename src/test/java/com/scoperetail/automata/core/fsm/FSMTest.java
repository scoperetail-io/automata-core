package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.AutomataCoreApplication;
import com.scoperetail.automata.core.exception.DisconnectedGraphException;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.fixtures.automata.*;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.StateEntity;
import com.scoperetail.automata.core.service.EventService;
import com.scoperetail.automata.core.service.StateEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {AutomataCoreApplication.class})
@ExtendWith(SpringExtension.class)
public class FSMTest {

  @Mock ApplicationContext applicationContext;

  @Mock FSMCollection fsmCollection;

  @Mock StateEntityService orderService;

  @Mock
  FiniteStateMachine finiteStateMachine;

  @Mock TestRushOrder testRushOrder;

  //  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void BasicTest() throws DisconnectedGraphException, StateAutomataException {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    FSMHarness.harnessFSM(fsmCollection, applicationContext, eventService, TestRushOrder.class);
    FSM fsm = fsmCollection.getFSMByName("TestRushOrder");
    assertTrue("TestRushOrder".equals(fsm.getName()));
    assertTrue("START".equals(fsm.getStartState()));
    assertTrue("END".equals(fsm.getEndStates().get(0)));
    assertTrue(fsm.getEvents().keySet().size() == 3);
    assertTrue(fsm.getStates().keySet().size() == 4);
  }

  @Test
  public void BasicTransitionTest() throws DisconnectedGraphException, StateAutomataException {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    FSMHarness.harnessFSM(fsmCollection, applicationContext, eventService, TestRushOrder.class);
    FSM fsm = fsmCollection.getFSMByName("TestRushOrder");
    assertNotNull(fsm);
    fsm.setAutomata(testRushOrder);
    fsm.setServiceImpl(orderService);
    // when(orderService.find(10000L)).thenReturn(null);
    fsm.onEvent(getEntity(), getEvent());
  }

  @Test
  public void TestComplexOrderTest() throws DisconnectedGraphException, StateAutomataException {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    FSMHarness.harnessFSM(fsmCollection, applicationContext, eventService, TestComplexOrder.class);
    FSM fsm = fsmCollection.getFSMByName("TestComplexOrder");
    assertTrue("TestComplexOrder".equals(fsm.getName()));
    assertTrue("S1".equals(fsm.getStartState()));
    assertTrue("S3".equals(fsm.getEndStates().get(0)));
    assertTrue(fsm.getEvents().keySet().size() == 7);
    assertTrue(fsm.getStates().keySet().size() == 6);
  }

  private PendingEvent getEvent() {
    PendingEvent event = new PendingEvent();
    event.setEventName("E1");
    event.setAutomataName("TestRushOrder");
    event.setKey("10000");
    return event;
  }

  private StateEntity getEntity() {
    StateEntity s = new StateEntity();
    s.setId(10000L);
    s.setKey("10000");
    s.setAutomataType("TestRushOrder");
    return s;
  }

  @Test
  public void disjointAutomataShouldErrorOut() {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    assertThrows(
        DisconnectedGraphException.class,
        () -> {
          FSMHarness.harnessFSM(
              fsmCollection, applicationContext, eventService, DisjointOrder.class);
        });
  }

  @Test
  public void invalidEventsAutomataShouldErrorOut() {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    assertThrows(
        StateAutomataException.class,
        () -> {
          FSMHarness.harnessFSM(
              fsmCollection, applicationContext, eventService, InvalidEventOrder.class);
        });
  }

  @Test
  public void invalidStatesAutomataShouldErrorOut() {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    assertThrows(
        StateAutomataException.class,
        () -> {
          FSMHarness.harnessFSM(
              fsmCollection, applicationContext, eventService, InvalidStatesOrder.class);
        });
  }

  @Test
  public void invalidStartAutomataShouldErrorOut()
      throws DisconnectedGraphException, StateAutomataException {
    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    assertThrows(
        StateAutomataException.class,
        () -> {
          FSMHarness.harnessFSM(
              fsmCollection, applicationContext, eventService, InvalidStatesOrder.class);
        });
  }

  @Test
  public void multiEndComplexOrder() throws DisconnectedGraphException, StateAutomataException {

    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    FSMHarness.harnessFSM(fsmCollection, applicationContext, eventService, TestMultiEndOrder.class);
    FSM fsm = fsmCollection.getFSMByName("TestMultiEndOrder");
    List<String> endStates = new ArrayList<>();
    // "S3","S7", "S8"
    endStates.add("S3");
    endStates.add("S7");
    endStates.add("S8");
    assertTrue("TestMultiEndOrder".equals(fsm.getName()));
    assertTrue("S1".equals(fsm.getStartState()));
    assertEquals(endStates, fsm.getEndStates());
    assertTrue(fsm.getEvents().keySet().size() == 9);
    assertTrue(fsm.getStates().keySet().size() == 8);
  }

  /*
                     S7
                     ^
                     |e8
                     |
       S1 ----e1---->S2-----e2------>S3
       |             |               ^
       |e3         e4|               |
       |             |               |e7
       v             v               |
       S4---e5----->S5-------e6---->S6----e9---->S8

   Corresponding adjacency matrix
   {S3=[],S7=[],S8=[], S4=[S5], S5=[S6], S6=[S3, S8], S1=[S2, S4], S2=[S3, S5, S7]}


  	    S3 : []
  S4 : [E5, E6, E7, E9]
  S5 : [E6, E7, E9]
  S6 : [E7, E9]
  S7 : []
  S8 : []
  S1 : [E5, E6, E7, E8, E9, E1, E2, E3, E4]
  S2 : [E6, E7, E8, E9, E2, E4]
  */
  @Test
  public void multiEndCheckFutureStates()
      throws DisconnectedGraphException, StateAutomataException {

    FSMCollection fsmCollection = new FSMCollection();
    EventService eventService = Mockito.mock(EventService.class);
    FSMHarness.harnessFSM(fsmCollection, applicationContext, eventService, TestMultiEndOrder.class);
    FSM fsm = fsmCollection.getFSMByName("TestMultiEndOrder");
    Set<String> s1Futures =
        getSetOfString(new String[] {"E5", "E6", "E7", "E8", "E9", "E1", "E2", "E3", "E4"});
    Set<String> s2Futures = getSetOfString(new String[] {"E6", "E7", "E8", "E9", "E2", "E4"});
    Set<String> s3Futures = getSetOfString(new String[] {});
    Set<String> s4Futures = getSetOfString(new String[] {"E5", "E6", "E7", "E9"});
    Set<String> s5Futures = getSetOfString(new String[] {"E6", "E7", "E9"});
    Set<String> s6Futures = getSetOfString(new String[] {"E7", "E9"});
    Set<String> s7Futures = getSetOfString(new String[] {});
    Set<String> s8Futures = getSetOfString(new String[] {});
    List<String> endStates = getListOfString(new String[] {"S3", "S7", "S8"});
    assertTrue("TestMultiEndOrder".equals(fsm.getName()));
    assertTrue("S1".equals(fsm.getStartState()));
    assertEquals(endStates, fsm.getEndStates());

    assertEquals(s1Futures, fsm.getStates().get("S1").getFutureEvents());
    assertEquals(s2Futures, fsm.getStates().get("S2").getFutureEvents());
    assertEquals(s3Futures, fsm.getStates().get("S3").getFutureEvents());
    assertEquals(s4Futures, fsm.getStates().get("S4").getFutureEvents());
    assertEquals(s5Futures, fsm.getStates().get("S5").getFutureEvents());
    assertEquals(s6Futures, fsm.getStates().get("S6").getFutureEvents());
    assertEquals(s7Futures, fsm.getStates().get("S7").getFutureEvents());
    assertEquals(s8Futures, fsm.getStates().get("S8").getFutureEvents());

    assertTrue(fsm.getEvents().keySet().size() == 9);
    assertTrue(fsm.getStates().keySet().size() == 8);
  }

  private List<String> getListOfString(String[] strings) {
    List<String> listOfString = new ArrayList<>();
    for (String s : strings) {
      listOfString.add(s);
    }
    return listOfString;
  }

  private Set<String> getSetOfString(String[] strings) {
    Set<String> setOfString = new HashSet<>();
    for (String s : strings) {
      setOfString.add(s);
    }
    return setOfString;
  }
}
