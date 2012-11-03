package util_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.Graph;
import util.GraphNodeIsDuplicateException;
import util.GraphNodeNotFoundException;
import util.GraphEdgeIsDuplicateException;
import util.GraphEdgeNotFoundException;

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
        addNodeOrFail("a");
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
        addNodesOrFail(strings);
        assertTrue(graph.toString().equals("a: \n" + "b: \n") ||
                   graph.toString().equals("b: \n" + "a: \n"));
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testAddEdgeNonexistantNode()
        throws GraphNodeNotFoundException,
               GraphEdgeIsDuplicateException
    {
        graph.addEdge("a", "b");
    }

    @Test
    public void testGraphWithSingleEdgeToString() {
        String[] strings = { "a", "b" };
        addNodesOrFail(strings);
        addEdgeOrFail("a", "b");
        String s = graph.toString();
        assertTrue(s.equals("a: b (1.0)\n" + "b: a (1.0)\n") ||
                   s.equals("b: a (1.0)\n" + "a: b (1.0)\n"));
    }

    @Test(expected=util.GraphNodeNotFoundException.class)
    public void testAddEdgeWeightedNonexistantNode()
        throws GraphNodeNotFoundException,
               GraphEdgeIsDuplicateException
    {
        graph.addEdge("a", "b", 42.0);
    }

    @Test
    public void testGraphWithSingleWeightedEdgeToString() {
        String[] strings = { "a", "b" };
        addNodesOrFail(strings);
        addEdgeOrFail("a", "b", 42.0);
        String s = graph.toString();
        assertTrue(s.equals("a: b (42.0)\n" + "b: a (42.0)\n") ||
                   s.equals("b: a (42.0)\n" + "a: b (42.0)\n"));
    }

    @Test public void testGraphWithMultipleEdgesToString() {
        String[] strings = { "a", "b", "c" };
        addNodesOrFail(strings);
        String[] origins = { "a", "a" };
        String[] apices  = { "b", "c" };
        addEdgesOrFail(origins, apices);
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
        addNodesOrFail(strings);
        addEdgeOrFail("a", "b", 42.0);
        addEdgeOrFail("a", "c", 42.0);
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
        addNodeOrFail("a");
        graph.findPath("a", "b");
    }

    @Test
    public void testFindPath() throws GraphNodeNotFoundException {
        populateGraph();
        Object[] pathElements = null;
        pathElements = graph.findPath("a", "d");

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
    public void testFindPathSetOfGoalsEmpty() throws GraphNodeNotFoundException
    {
        addNodeOrFail("a");

        Object[] pathElements = null;
        pathElements = graph.findPath("a", new HashSet<String>());

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
        addNodeOrFail("a");
        Set<String> goals = new HashSet<String>();
        goals.add("b");
        graph.findPath("a", goals);
    }

    @Test
    public void testFindPathSetOfGoals() throws GraphNodeNotFoundException {
        populateGraph();
        Object[] pathElements = null;
        Set<String> goals = new HashSet<String>();
        goals.add("a");
        goals.add("d");
        pathElements = graph.findPath("a", goals);

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

    @Test(expected=util.GraphEdgeNotFoundException.class)
    public void testRemoveEdge()
        throws GraphNodeNotFoundException,
               GraphEdgeNotFoundException
    {
        addNodeOrFail("a");
        addNodeOrFail("b");
        graph.removeEdge("a", "b");
    }

    private void populateGraph() {
        String[] strings = {  "a", "b1", "b2", "c1", "c2",  "d" };
        addNodesOrFail(strings);

        String[] origins = {  "a",  "a",  "a", "b2", "b2", "c2" };
        String[] apices  = { "b1", "b2", "c2", "c1", "c2",  "d" };
        addEdgesOrFail(origins, apices);
    }

    private void
    addNodeOrFail(String s) {
        String[] strings = { s };
        addNodesOrFail(strings);
    }

    private void
    addNodesOrFail(String[] strings) {
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
    addEdgeOrFail(String origin, String apex) {
        String[] origins = { origin };
        String[] apices = { apex };
        addEdgesOrFail(origins, apices);
    }

    private void
    addEdgesOrFail(String[] origins, String[] apices) {
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

    private void
    addEdgeOrFail(String origin, String apex, Double weight) {
        String[] origins = { origin };
        String[] apices = { apex };
        Double[] weights = { weight };
        addEdgesOrFail(origins, apices, weights);
    }

    private void
    addEdgesOrFail(String[] origins, String[] apices, Double[] weights) {
        for (int i = 0; i < origins.length; i++) {
            try {
                graph.addEdge(origins[i], apices[i], weights[i]);
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
