package util_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.Graph;
import util.GraphNodeIsDuplicateException;

/**
 * A JUnit test fixture for util.Graph.
 * @author Andrew Allen Barkley
 */
public class GraphTest {

    Graph<Integer> graph;

    @Before
    public void initTests() {
        graph = new Graph<Integer>();
    }

    @Test
    public void testEmptyGraphToString() {
        assertEquals("", graph.toString());
    }

    @Test
    public void testSingleNodeToString() {
        try {
            graph.addNode(42);
        }
        catch(GraphNodeIsDuplicateException e) {
            fail();
        }
        assertEquals("42: \n", graph.toString());
    }

    @Test(expected=util.GraphNodeIsDuplicateException.class)
    public void testAddDuplicateNodeThrowsGraphNodeIsDuplicateException()
        throws GraphNodeIsDuplicateException {
        graph.addNode(42);
        graph.addNode(42);
    }

    @Test
    public void testMultipleNodesToString() {
        try {
            graph.addNode(42);
            graph.addNode(43);
        }
        catch(GraphNodeIsDuplicateException e) {
            fail();
        }
        assertTrue(graph.toString().equals("42: \n" + "43: \n") ||
                   graph.toString().equals("43: \n" + "42: \n"));
    }
}
