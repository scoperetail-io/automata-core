package com.scoperetail.automata.core.helper;

import com.scoperetail.automata.core.fsm.FSM;
import com.scoperetail.automata.core.fsm.State;
import com.scoperetail.automata.core.fsm.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/** @author scoperetail */
public class GraphTheoryHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(GraphTheoryHelper.class);

  private GraphTheoryHelper() {}

  /*
   * checkConnectivity works on BFS algorithm
   * S1--->S2--->S3---->S4---->S7
   *       |            ^
   *       |            |
   *       v            |
   *       S5-----------S6
   * It starts with node which indicates START state and visits all possible
   * nodes, if all nodes are reachable, it is considered a connected graph
   * which indicates that automata will be able to cover all states which are defined
   *
   */
  public static boolean checkConnectivity(FSM fsm) {
    Graph g = new Graph();
    for (State state : fsm.getStates().values()) {
      for (Transition t : state.getTransitions().values()) {
        g.addEdge(state.getName(), t.getToState(), t.getOnEvent());
      }
    }
    boolean isConnected = g.breadthFirstSearch(fsm.getStartState());
    if (isConnected) {
      Map<String, Set<String>> allPaths =
          g.findAllPathBetweenNodes(fsm.getStartState(), fsm.getEndStates());
      Iterator<Entry<String, Set<String>>> i = allPaths.entrySet().iterator();
      while (i.hasNext()) {
        Entry<String, Set<String>> entry = i.next();
        String key = entry.getKey();
        // Order will be reversed to store future events in the order they should be applied
        List<String> list = new ArrayList<>(entry.getValue());
        Collections.reverse(list);
        LOGGER.info("State: {} :: Future Events:{}", key, list);
        fsm.getStates().get(key).getFutureEvents().addAll(list);
      }
    }
    return isConnected;
  }

  /**
   * @author scoperetail
   *     <p>This class is used to crete a placeholder object for every event and target state
   *     corresponding the event In graph theory it represents the directional edge and
   *     corresponding vertex/node ex. S1-------e1-------->S2 toVertex=S2 onEvent=e1
   */
  private static class EdgeAndNode {
    String toVertex;
    String onEvent;

    public EdgeAndNode(String toVertex, String onEvent) {
      this.toVertex = toVertex;
      this.onEvent = onEvent;
    }

    public String getToVertex() {
      return toVertex;
    }

    public String getOnEvent() {
      return onEvent;
    }
  }

  @SuppressWarnings("squid:S3824")
  private static class Graph {
    private final Map<String, LinkedList<EdgeAndNode>> adjecency = new HashMap<>();
    private final Map<String, Set<String>> futureEventsFromVertex = new HashMap<>();
    // Add an edge into the graph
    /*
     * For any automata we will pass every set of source,target and event
     * In terms of graph theory we are forming an equivalent of Adjacency Matrix
     */
    void addEdge(String from, String to, String onEvent) {
      LinkedList<EdgeAndNode> fromVertex = this.adjecency.get(from);
      if (fromVertex == null) {
        fromVertex = new LinkedList<>();
        this.adjecency.put(from, fromVertex);
      }
      fromVertex.add(new EdgeAndNode(to, onEvent));
      // Below code ensure that nodes which have no transition also get added to map
      LinkedList<EdgeAndNode> toVertex = this.adjecency.get(to);
      if (toVertex == null) {
        toVertex = new LinkedList<>();
        this.adjecency.put(to, toVertex);
      }

      Set<String> futureEventsForFrom = this.futureEventsFromVertex.get(from);
      if (futureEventsForFrom == null) {
        futureEventsForFrom = new LinkedHashSet<>();
      }
      this.futureEventsFromVertex.put(from, futureEventsForFrom);
      Set<String> futureEventsForTo = this.futureEventsFromVertex.get(to);
      if (futureEventsForTo == null) {
        futureEventsForTo = new LinkedHashSet<>();
      }
      this.futureEventsFromVertex.put(to, futureEventsForTo);
    }

    /*
     * This method uses standard Breadth First Traversal to mark all the nodes which are
     * connected. This will help us validating an automata with unreachable states
     */
    // BFS traversal from a given source s
    boolean breadthFirstSearch(String start) {
      // Mark all the vertices as not visited(By default
      // set as false)
      Map<String, Boolean> visited = new HashMap<>();
      this.adjecency.forEach((k, v) -> visited.put(k, false));
      // Create a queue for BFS
      LinkedList<String> queue = new LinkedList<>();

      // Mark the current node as visited and enqueue it
      visited.put(start, true);
      queue.add(start);

      while (!queue.isEmpty()) {
        // Dequeue a vertex from queue and print it
        start = queue.poll();

        // Get all adjacent vertices of the dequeued vertex s
        // If a adjacent has not been visited, then mark it
        // visited and enqueue it
        Iterator<EdgeAndNode> it = this.adjecency.get(start).listIterator();
        while (it.hasNext()) {
          String node = it.next().getToVertex();
          if (!visited.get(node)) {
            visited.put(node, true);
            queue.add(node);
          }
        }
      }
      // We do an AND operation, if every node is visited return true
      boolean isConnected = true;
      for (boolean b : visited.values()) {
        isConnected = isConnected && b;
      }
      return isConnected;
    }

    /*
     * This method provides ability to find all future events eligible w.r.t. any given state
     * Ex S1----e1----->S2----e2----->S3----e3----->S4----e4----->S5
     * S1: e1,e2,e3,e4
     * S2: e2,e3,e4
     * S3: e3,e4
     * S4: e4
     * S5:
     *
     * In a cyclic graph every event is valid event for every state, however since we are passing
     * start and end state, no future event will calculated for the end state
     * If automata is on end state, no further event will be eligible to make transition
     */
    private Map<String, Set<String>> findAllPathBetweenNodes(String start, List<String> ends) {
      // Mark all the vertices as not visited(By default
      // set as false)
      Map<String, Boolean> visited = new HashMap<>();
      this.adjecency.forEach(
          (k, v) -> {
            visited.put(k, false);
          });
      // List for visited path
      List<String> pathList = new ArrayList<>();
      List<String> pathEdges = new ArrayList<>();
      // Add start node to path
      pathList.add(start);

      for (String end : ends) {
        getAllPathUtil(start, end, visited, pathList, pathEdges);
      }
      // futureEventsFromVertex is now updated as the recursion is over
      return this.futureEventsFromVertex;
    }

    /*
     * This is a utility method to allow recursion, it is used by findAllPathBetweenNodes
     */
    private void getAllPathUtil(
        String start,
        String end,
        Map<String, Boolean> visited,
        List<String> pathList,
        List<String> pathEdges) {
      // mark this current node as visited
      visited.put(start, true);

      if (start.equals(end)) {
        // This indicates the traversal is complete
      }

      Iterator<EdgeAndNode> it = this.adjecency.get(start).listIterator();
      while (it.hasNext()) {
        EdgeAndNode ean = it.next();
        String node = ean.getToVertex();
        String edge = ean.getOnEvent();
        if (!visited.get(node)) {
          // store current node to path
          pathList.add(node);
          pathEdges.add(edge);
          // start recursion for all forward paths from here to end
          getAllPathUtil(node, end, visited, pathList, pathEdges);
          // Except the current node, all previous nodes will be affected by this edge
          for (String e : pathList) {
            if (!node.equals(e)) {
              this.futureEventsFromVertex.get(e).add(edge);
            }
          }
          // remove this node from traversal path as a new branch will start
          // beyond this point from the previous node
          pathList.remove(node);
          pathEdges.remove(edge);
        }
      }
      // this will allow traversal for alternate path
      // if end node remains marked as visited it traversal will not happen on
      // alternate paths
      visited.put(start, false);
    }
  }
}
