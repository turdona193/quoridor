package util_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.Graph;
import util.GraphNodeIsDuplicateException;
import util.GraphNodeNotFoundException;
import util.GraphEdgeIsDuplicateException;

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
        throws GraphNodeIsDuplicateException {
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
}
