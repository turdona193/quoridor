package util_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.Graph;
import util.GraphNodeIsDuplicateException;
import util.GraphNodeNotFoundException;
import util.GraphEdgeIsDuplicateException;

import java.util.Set;
import java.util.HashSet;

/**
 * A JUnit test fixture for util.Graph.
 * @author Andrew Allen Barkley
 */
public class GraphTest {

    Graph<String> graph;

    @Before
    public void initTests() {
        graph = new Graph<String>();
    }

    @Test
    public void testEmptyGraphToString() {
        assertEquals("", graph.toString());
    }

    @Test
    public void testSingleNodeToString() {
        try {
            graph.addNode("a");
        }
        catch(GraphNodeIsDuplicateException e) {
            fail();
        }
        assertEquals("a: \n", graph.toString());
    }

    @Test(expected=util.GraphNodeIsDuplicateException.class)
    public void testAddDuplicateNodeThrowsGraphNodeIsDuplicateException()
        throws GraphNodeIsDuplicateException
    {
        graph.addNode("a");
        graph.addNode("a");
    }

    @Test
    public void testMultipleNodesToString() {
        try {
            graph.addNode("a");
            graph.addNode("b");
        }
        catch(GraphNodeIsDuplicateException e) {
            fail();
        }
        assertTrue(graph.toString().equals("a: \n" + "b: \n") ||
                   graph.toString().equals("b: \n" + "a: \n"));
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testGraphNodeNotFoundException()
        throws GraphNodeNotFoundException
    {
        try {
            graph.addEdge("a", "b");
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
    }

    @Test
    public void testGraphWithSingleEdgeToString() {
        try {
            graph.addNode("a");
            graph.addNode("b");
            graph.addEdge("a", "b");
        }
        catch (GraphNodeIsDuplicateException e) {
            fail();
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
        String s = graph.toString();
        assertTrue(s.equals("a: b (1.0)\n" + "b: a (1.0)\n") ||
                   s.equals("b: a (1.0)\n" + "a: b (1.0)\n"));
    }

    @Test
    public void testGraphWithSingleWeightedEdgeToString() {
        try {
            graph.addNode("a");
            graph.addNode("b");
            graph.addEdge("a", "b", 42.0);
        }
        catch (GraphNodeIsDuplicateException e) {
            fail();
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
        String s = graph.toString();
        assertTrue(s.equals("a: b (42.0)\n" + "b: a (42.0)\n") ||
                   s.equals("b: a (42.0)\n" + "a: b (42.0)\n"));
    }

    @Test public void testGraphWithMultipleEdgesToString() {
        try {
            graph.addNode("a");
            graph.addNode("b");
            graph.addNode("c");
            graph.addEdge("a", "b");
            graph.addEdge("a", "c");
        }
        catch (GraphNodeIsDuplicateException e) {
            fail();
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
        String s = graph.toString();
        assertTrue(s.equals("a: b (1.0), c (1.0)\n" +
                            "b: a (1.0)\n" +
                            "c: a (1.0)\n") ||

                   s.equals("a: b (1.0), c (1.0)\n" +
                            "c: a (1.0)\n" +
                            "b: a (1.0)\n") ||

                   s.equals("b: a (1.0)\n" +
                            "a: b (1.0), c (1.0)\n" +
                            "c: a (1.0)\n") ||

                   s.equals("c: a (1.0)\n" +
                            "a: b (1.0), c (1.0)\n" +
                            "b: a (1.0)\n") ||

                   s.equals("b: a (1.0)\n" +
                            "c: a (1.0)\n" +
                            "a: b (1.0), c (1.0)\n") ||

                   s.equals("c: a (1.0)\n" +
                            "b: a (1.0)\n" +
                            "a: b (1.0), c (1.0)\n") ||

                   s.equals("a: c (1.0), b (1.0)\n" +
                            "b: a (1.0)\n" +
                            "c: a (1.0)\n") ||

                   s.equals("a: c (1.0), b (1.0)\n" +
                            "c: a (1.0)\n" +
                            "b: a (1.0)\n") ||

                   s.equals("b: a (1.0)\n" +
                            "a: c (1.0), b (1.0)\n" +
                            "c: a (1.0)\n") ||

                   s.equals("c: a (1.0)\n" +
                            "a: c (1.0), b (1.0)\n" +
                            "b: a (1.0)\n") ||

                   s.equals("b: a (1.0)\n" +
                            "c: a (1.0)\n" +
                            "a: b (1.0), b (1.0)\n") ||

                   s.equals("c: a (1.0)\n" +
                            "b: a (1.0)\n" +
                            "a: c (1.0), b (1.0)\n"));
    }

    @Test public void testGraphWithMultipleWeightedEdgesToString() {
        try {
            graph.addNode("a");
            graph.addNode("b");
            graph.addNode("c");
            graph.addEdge("a", "b", 42.0);
            graph.addEdge("a", "c", 42.0);
        }
        catch (GraphNodeIsDuplicateException e) {
            fail();
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
        String s = graph.toString();
        assertTrue(s.equals("a: b (42.0), c (42.0)\n" +
                            "b: a (42.0)\n" +
                            "c: a (42.0)\n") ||

                   s.equals("a: b (42.0), c (42.0)\n" +
                            "c: a (42.0)\n" +
                            "b: a (42.0)\n") ||

                   s.equals("b: a (42.0)\n" +
                            "a: b (42.0), c (42.0)\n" +
                            "c: a (42.0)\n") ||

                   s.equals("c: a (42.0)\n" +
                            "a: b (42.0), c (42.0)\n" +
                            "b: a (42.0)\n") ||

                   s.equals("b: a (42.0)\n" +
                            "c: a (42.0)\n" +
                            "a: b (42.0), c (42.0)\n") ||

                   s.equals("c: a (42.0)\n" +
                            "b: a (42.0)\n" +
                            "a: b (42.0), c (42.0)\n") ||

                   s.equals("a: c (42.0), b (42.0)\n" +
                            "b: a (42.0)\n" +
                            "c: a (42.0)\n") ||

                   s.equals("a: c (42.0), b (42.0)\n" +
                            "c: a (42.0)\n" +
                            "b: a (42.0)\n") ||

                   s.equals("b: a (42.0)\n" +
                            "a: c (42.0), b (42.0)\n" +
                            "c: a (42.0)\n") ||

                   s.equals("c: a (42.0)\n" +
                            "a: c (42.0), b (42.0)\n" +
                            "b: a (42.0)\n") ||

                   s.equals("b: a (42.0)\n" +
                            "c: a (42.0)\n" +
                            "a: b (42.0), b (42.0)\n") ||

                   s.equals("c: a (42.0)\n" +
                            "b: a (42.0)\n" +
                            "a: c (42.0), b (42.0)\n"));
    }

    @Test
    public void testFindPath() {
        populateGraph();
        Object[] pathElements = null;
        try {
            pathElements = graph.findPath("a", "d");
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }

        String  path        = (String) pathElements[0];
        Integer comparisons = (Integer)pathElements[1];
        Integer maneuvers   = (Integer)pathElements[2];
        Double  length      = (Double) pathElements[3];

        assertTrue(path.equals("a (0.0)\n" +
                               "b2 (1.0)\n" +
                               "c2 (2.0)\n" +
                               "d (3.0)\n") ||
                   path.equals("a (0.0)\n" +
                               "c2 (1.0)\n" +
                               "d (2.0)\n"));
        assertTrue(comparisons > 2);
        assertTrue(maneuvers == 2 || maneuvers == 3);
        assertTrue(length == 2.0 || length == 3.0);
    }

    @Test
    public void testFindPathForASetOfGoals() {
        populateGraph();
        Object[] pathElements = null;
        Set<String> goals = new HashSet<String>();
        goals.add("a");
        goals.add("d");
        try {
            pathElements = graph.findPath("a", goals);
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }

        String  path        = (String) pathElements[0];
        Integer comparisons = (Integer)pathElements[1];
        Integer maneuvers   = (Integer)pathElements[2];
        Double  length      = (Double) pathElements[3];

        assertTrue(path.equals("a (0.0)\n" +
                               "a (0.0)\n"));
        assertTrue(comparisons > 0);
        assertTrue(maneuvers == 0);
        assertTrue(length == 0.0);
    }

    private void populateGraph() {
        try {
            graph.addNode("a");
            graph.addNode("b1");
            graph.addEdge("a", "b1");
            graph.addNode("b2");
            graph.addEdge("a", "b2");
            graph.addNode("c1");
            graph.addNode("c2");
            graph.addEdge("a", "c2");
            graph.addEdge("b2", "c1");
            graph.addEdge("b2", "c2");
            graph.addNode("d");
            graph.addEdge("c2", "d");
        }
        catch (GraphNodeIsDuplicateException e) {
            fail();
        }
        catch (GraphNodeNotFoundException e) {
            fail();
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
    }
}
