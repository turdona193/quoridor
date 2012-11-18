package util_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import util.Graph;

/**
 * A JUnit test fixture for util.Graph.
 *
 * @author  <a href="mailto:barkle36@gmail.com">Andrew Allen Barkley</a>
 * @version 2012-11-13
 */
public class GraphTest {

    Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new Graph<Integer>();
    }

    @Test
    public void testContains() {
        graph.addNode(1);
        assertTrue(graph.contains(1));
    }

    @Test
    public void testContainsEdge() {
        addNodes(new int[] {1, 2});
        assertFalse(graph.containsEdge(1, 2));
        assertFalse(graph.containsEdge(2, 1));
        graph.addEdge(1, 2);
        assertTrue(graph.containsEdge(1, 2));
        assertTrue(graph.containsEdge(2, 1));
    }

    @Test
    public void testClone() {
        assertTrue(graph.equals(graph));
        addNodes(new int[] { 1, 2, 3 });
        addEdges(new int[] { 1, 1 },
                 new int[] { 2, 3 });
        Graph<Integer> clone = graph.clone();
        assertTrue(graph.equals(clone));
        graph.removeEdge(1, 2);
        assertTrue(!graph.equals(clone));
    }

    @Test
    public void testFindPathSetOfGoals() {
        Set<Integer> goalSet = new HashSet<Integer>();
        List<Integer> expectedPath = new ArrayList<Integer>();

        graph.addNode(1);
        goalSet.add(1);
        expectedPath.add(1);
        assertEquals(expectedPath, graph.findPath(1, goalSet));
        goalSet.remove(1);

        graph.addNode(2);
        goalSet.add(2);
        graph.addEdge(1, 2);
        expectedPath.add(2);
        graph.addNode(3);
        goalSet.add(3);
        assertEquals(expectedPath, graph.findPath(1, goalSet));
    }

    private void addNodes(int[] ints) {
        for (int i: ints) {
            graph.addNode(i);
        }
    }

    private void addEdges(int[] origins, int[] apices) {
        for (int i = 0; i < origins.length; i++) {
            graph.addEdge(origins[i], apices[i]);
        }
    }
}
