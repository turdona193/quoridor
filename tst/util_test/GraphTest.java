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
        addNodeToGraphOrFailOnGraphNodeIsDuplicateException("a");
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
        String[] strings = { "a", "b" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
        assertTrue(graph.toString().equals("a: \n" + "b: \n") ||
                   graph.toString().equals("b: \n" + "a: \n"));
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testAddEdgeNonexistantNode()
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
        String[] strings = { "a", "b" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
        addEdgeToGraphOrFailOnGraphException("a", "b");
        String s = graph.toString();
        assertTrue(s.equals("a: b (1.0)\n" + "b: a (1.0)\n") ||
                   s.equals("b: a (1.0)\n" + "a: b (1.0)\n"));
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testAddEdgeWeightedNonexistantNode()
        throws GraphNodeNotFoundException
    {
        try {
            graph.addEdge("a", "b", 42.0);
        }
        catch(GraphEdgeIsDuplicateException e) {
            fail();
        }
    }

    @Test
    public void testGraphWithSingleWeightedEdgeToString() {
        String[] strings = { "a", "b" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
        try {
            graph.addEdge("a", "b", 42.0);
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
        String[] strings = { "a", "b", "c" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
        String[] origins = { "a", "a" };
        String[] apices  = { "b", "c" };
        addEdgesToGraphOrFailOnGraphException(origins, apices);
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
        String[] strings = { "a", "b", "c" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
        try {
            graph.addEdge("a", "b", 42.0);
            graph.addEdge("a", "c", 42.0);
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

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testFindPathNonexistantOriginNode()
        throws GraphNodeNotFoundException
    {
        graph.findPath("a", "b");
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testFindPathNonexistantApexNode()
        throws GraphNodeNotFoundException
    {
        addNodeToGraphOrFailOnGraphNodeIsDuplicateException("a");
        graph.findPath("a", "b");
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

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testFindPathSetOfGoalsNonexistantOriginNode()
        throws GraphNodeNotFoundException
    {
        graph.findPath("a", new HashSet<String>());
    }

    @Test
    public void testFindPathSetOfGoalsEmpty() {
        addNodeToGraphOrFailOnGraphNodeIsDuplicateException("a");

        Object[] pathElements = null;
        try {
            pathElements = graph.findPath("a", new HashSet<String>());
        }
        catch(GraphNodeNotFoundException e) {
            fail();
        }

        String  path        = (String) pathElements[0];
        Integer comparisons = (Integer)pathElements[1];
        Integer maneuvers   = (Integer)pathElements[2];
        Double  length      = (Double) pathElements[3];

        assertTrue(path.equals("none"));
        assertTrue(comparisons == 0);
        assertTrue(maneuvers == 0);
        assertTrue(length == Double.POSITIVE_INFINITY);
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testFindPathSetOfGoalsNonexistantApexNode()
        throws GraphNodeNotFoundException
    {
        addNodeToGraphOrFailOnGraphNodeIsDuplicateException("a");
        Set<String> goals = new HashSet<String>();
        goals.add("b");
        graph.findPath("a", goals);
    }

    @Test
    public void testFindPathSetOfGoals() {
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
        String[] strings = {  "a", "b1", "b2", "c1", "c2",  "d" };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);

        String[] origins = {  "a",  "a",  "a", "b2", "b2", "c2" };
        String[] apices  = { "b1", "b2", "c2", "c1", "c2",  "d" };
        addEdgesToGraphOrFailOnGraphException(origins, apices);
    }

    private void
    addNodeToGraphOrFailOnGraphNodeIsDuplicateException(String s) {
        String[] strings = { s };
        addNodesToGraphOrFailOnGraphNodeIsDuplicateException(strings);
    }

    private void
    addNodesToGraphOrFailOnGraphNodeIsDuplicateException(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            try {
                graph.addNode(strings[i]);
            }
            catch(GraphNodeIsDuplicateException e) {
                fail();
            }
        }
    }

    private void
    addEdgeToGraphOrFailOnGraphException(String origin, String apex) {
        String[] origins = { origin };
        String[] apices = { apex };
        addEdgesToGraphOrFailOnGraphException(origins, apices);
    }

    private void
    addEdgesToGraphOrFailOnGraphException(String[] origins, String[] apices) {
        for (int i = 0; i < origins.length; i++) {
            try {
                graph.addEdge(origins[i], apices[i]);
            }
            catch(GraphNodeNotFoundException e) {
                fail();
            }
            catch(GraphEdgeIsDuplicateException e) {
                fail();
            }
        }
    }
}
