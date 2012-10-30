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
}
