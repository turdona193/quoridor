/*
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 only, as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 2 for
 * more details (a copy is included in the LICENSE file that accompanied this
 * code).
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this work; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package util;

import java.util.Set;
import java.util.HashSet;

import java.util.Comparator;

import java.lang.IllegalArgumentException;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Stack;
import java.util.LinkedList;

import java.util.Comparator;
import java.util.PriorityQueue;

import java.util.NavigableSet;
import java.util.TreeSet;

import java.util.Stack;

/**
 * {@code Graph} implements a graph, weighted and/or directed, with or without
 * replacement.  With replacement, duplicate edges replace existing ones.
 *
 * @author  <a href="mailto:barkle36@gmail.com">Andrew Allen Barkley</a>
 * @version 2012-11-11
 */
public class Graph<E> {
    /**
     * Constant storing the string indicating the default search algorithm for
     * the path searching methods to use.
     */
    private static final String DEFAULT_SEARCH = "depth-first";

    /**
     * Boolean storing the directed option value.  This is {@code true} if and
     * only if this graph is directed.
     */
    private boolean directed;

    /**
     * Boolean storing the replacement option value.  This is {@code true} if
     * and only if this graph accepts edges for replacement of existing edges.
     */
    private boolean replacement;

    /**
     * The set of nodes that compose this graph.
     */
    private Set<Node<E>> nodes;

    /**
     * Construct an empty, undirected {@code Graph} wihout replacement.
     */
    public Graph() {
        directed = false;
        replacement = false;
        nodes = new HashSet<Node<E>>();
    }

    /**
     * Construct an empty {@code Graph} with the specified set of options.  A
     * {@code Graph} may be directed and/or allow replacement, specified by
     * the Strings "directed" and "replacement"
     *
     * @param options
     *     the set of options for this Graph, which may include the strings
     *     "directed" and/or "replacement"
     */
    public Graph(Set<String> options) {
        if (options.contains("directed")) {
            directed = true;
        }
        else {
            directed = false;
        }

        if (options.contains("replacement")) {
            replacement = true;
        }
        else {
            replacement = false;
        }

        nodes = new HashSet<Node<E>>();
    }

    /**
     * Add a node with the specified element to this graph.
     *
     * @param  element
     *     the element to add to this graph
     *
     * @throws GraphNodeIsDuplicateException
     *     when replacement is off and this graph already contains a node with
     *     the specified element.
     */
    public void addNode(E element) throws GraphNodeIsDuplicateException {
        Node<E> node = new Node<E>(element);
        if (nodes.contains(node)) {
            if (!replacement) {
                throw new GraphNodeIsDuplicateException(element.toString());
            }
        }
        else {
            nodes.add(node);
        }
    }

    /**
     * Return the specified node from this graph.
     *
     * @param  target
     *     the specified node
     *
     * @throws GraphNodeNotFoundException
     *     when this graph does not contain the specified node
     */
    private Node<E> node(Node<E> target)
        throws GraphNodeNotFoundException {

        for (Node<E> n : nodes)
            if (n.equals(target)) {
                return n;
            }

        throw new GraphNodeNotFoundException(target.toString());
    }

    /**
     * Add an edge to this graph from the node for the origin element to the
     * node for the apex elements.  If this graph is not directed, edges are
     * added both from the node for the apex element to the node for the
     * origin element as well as from the the node for the apex element to the
     * node for the origin element.  If this graph is directed, only the first
     * edge as in above is created.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @throws GraphEdgeIsDuplicateException
     *     if this graph already contains an edge with the specified origin
     *     and apex elements
     */
    public void addEdge(E origin, E apex)
        throws GraphNodeNotFoundException,
               GraphEdgeIsDuplicateException {
        addEdge(origin, apex, 1d);
    }

    /**
     * Remove the edge with the specified origin and apex elements from this
     * graph.  If this graph is not directed, remove the corresponding edge
     * from this graph too.  If this graph is directed, only remove the first
     * edge.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @throws GraphEdgeNotFoundException
     *     if this graph does not contain an edge with the specified origin
     *     and apex elements
     */
    public void removeEdge(E origin, E apex)
        throws GraphNodeNotFoundException,
               GraphEdgeNotFoundException {
        removeEdge(origin, apex, 1d);
    }

    /**
     * Add an edge to this graph from the node for the origin element to the
     * node for the apex elements.  If this graph is not directed, edges are
     * added both from the node for the apex element to the node for the
     * origin element as well as from the the node for the apex element to the
     * node for the origin element.  If this graph is directed, only the first
     * edge as in above is created.  If replacement is enabled for this graph
     * and one of these edges exists in this graph, it is replaced with this
     * new one, effectively updating the cost of the edge.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @param weight
     *     the cost of traversing the edge
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain the node with the specified element
     *
     * @throws GraphEdgeNotFoundException
     *     if this graph does not contain an edge with the specified origin
     *     and apex elements
     */
    public void addEdge(E origin, E apex, double weight)
        throws GraphNodeNotFoundException,
               GraphEdgeIsDuplicateException {

        Node<E> nodeA = new Node<E>(origin);
        Node<E> nodeB = new Node<E>(apex);

        try {
            node(nodeA).addEdge(node(nodeA), node(nodeB), weight);
        }
        catch (GraphNodeNotFoundException e) {
            throw new GraphNodeNotFoundException(origin.toString());
        }

        if (!directed) {
            try {
                node(nodeB).addEdge(node(nodeB), node(nodeA), weight);
            }
            catch (GraphNodeNotFoundException e) {
                throw new GraphNodeNotFoundException(apex.toString());
            }
        }
    }

    /**
     * Remove the edge with the specified origin and apex elements and weight
     * from this graph.  If this graph is not directed, remove the
     * corresponding edge from this graph too.  If this graph is directed,
     * only remove the first edge.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @param weight
     *     the cost of traversing the edge
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @throws GraphEdgeNotFoundException
     *     if this graph does not contain an edge with the specified origin
     *     and apex elements
     */
    public void removeEdge(E origin, E apex, double weight)
        throws GraphNodeNotFoundException,
               GraphEdgeNotFoundException {

        Node<E> nodeA = new Node<E>(origin);
        Node<E> nodeB = new Node<E>(apex);

        try {
            node(nodeA).removeEdge(node(nodeA), node(nodeB), weight);
            if (!directed) {
                node(nodeB).removeEdge(node(nodeB), node(nodeA), weight);
            }
        }
        catch (GraphNodeNotFoundException e) {
            throw new GraphNodeNotFoundException();
        }
        catch (GraphEdgeNotFoundException e) {
            Edge<E> edge = new Edge<E>(nodeA, nodeB, weight);
            throw new GraphEdgeNotFoundException(edge.toString());
        }
    }

    /**
     * Returns a path from initial element to goal element.
     *
     * @param  initial
     *     the initial element
     *
     * @param  goal
     *     the goal element
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    public Object[] findPath(E initial, E goal)
        throws GraphNodeNotFoundException
    {
        try {
            return findPath(DEFAULT_SEARCH, initial, goal);
        }
        catch (IllegalArgumentException e) { // this will never run

        }
        return null;
    }

    /**
     * Returns a path from initial element to the 'closest' goal element.
     *
     * @param  initial
     *     the initial element
     *
     * @param  goals
     *     the set of goal elements to chooses from
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found (one case in which this
     *              happens is if goals is empty)</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    public Object[] findPath(E initial, Set<E> goals)
        throws GraphNodeNotFoundException
    {
        node(new Node(initial)); // if (!nodes.contains(node equiv. to initial))
                                 //    throw GraphNodeNotFoundException

        String   path        = "none";
        Integer  comparisons = 0;
        Integer  maneuvers   = 0;
        Double   length      = Double.POSITIVE_INFINITY;

        Object[] array       = { path, comparisons, maneuvers, length };

        String  shortestPath        = path;
        Integer shortestComparisons = comparisons;
        Integer shortestManeuvers   = maneuvers;
        Double  shortestLength      = length;

        for (E goal : goals) {
            try {
                array = findPath(initial, goal);
            }
            catch (IllegalArgumentException e) { }

            path        = (String) array[0];
            comparisons = (Integer)array[1];
            maneuvers   = (Integer)array[2];
            length      = (Double) array[3];

            shortestComparisons += comparisons;

            if (length < shortestLength) {
                shortestPath      = path;
                shortestManeuvers = maneuvers;
                shortestLength    = length;
            }
        }

        Object[] result = { shortestPath, shortestComparisons,
                            shortestManeuvers, shortestLength };
        return result;
    }

    /**
     * Returns a path from initial element to goal element.
     *
     * @param  algorithm
     *     the algorithm to use to search for the path; currently one of:
     *     breadth-first, depth-first, best-first
     *
     * @param  initial
     *     the initial element
     *
     * @param  goal
     *     the goal element
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @throws IllegalArgumentException
     *     if the specified 
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    public Object[] findPath(String algorithm, E initial, E goal)
        throws GraphNodeNotFoundException,
               IllegalArgumentException
    {
        if (initial.equals(goal)) {
            Object[] results = { initial + " (0.0)\n" + goal + " (0.0)\n", // path
                                 1,  // comparisons
                                 0,  // path maneuvers
                                 0d, // path cost
            };
            return results;
        }

        Node<E> initialNode = node(new Node<E>(initial));
        Node<E> goalNode = node(new Node<E>(goal));

        if (algorithm.equals("breadth-first") ||
                algorithm.equals("depth-first")) {
            return breadthOrDepthFirstPathSearch(
                    algorithm, initialNode, goalNode);
        }
        if (algorithm.equals("best-first")) {
            return uniformCostPathSearch(initialNode, goalNode);
        }

        throw new IllegalArgumentException(algorithm);

    }

    /**
     * Returns a path from initial node to goal node using breadth-first
     * search.
     *
     * @param  algorithm
     *     the algorithm to use to search for the path; currently one of:
     *     breadth-first, depth-first
     *
     * @param  initial
     *     the initial state
     *
     * @param  goal
     *     the goal state
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    private Object[] breadthOrDepthFirstPathSearch(
            String algorithm,
            Node<E> initial,
            Node<E> goal)
        throws GraphNodeNotFoundException
    {
        String   path          = "none";
        Integer  comparisons   = 1,
                 pathManeuvers = 0;
        Double   pathLength    = Double.POSITIVE_INFINITY;

        Frontier<SearchNode<Node<E>>> frontier = null;
        if (algorithm.equals("breadth-first")) {
            frontier = new QueueFrontier<SearchNode<Node<E>>>();
        }
        if (algorithm.equals("depth-first")) {
            frontier = new StackFrontier<SearchNode<Node<E>>>();
        }
        frontier.add(new SearchNode<Node<E>>(initial, null, 0d));

        NavigableSet<SearchNode<Node<E>>>
            explored = new TreeSet<SearchNode<Node<E>>>();

        while (true) {
            if (frontier.isEmpty()) {
                Object[] results = { path, comparisons,
                                     pathManeuvers, pathLength };
                return results;
            }
            SearchNode<Node<E>> n = frontier.remove();
            explored.add(n);
            for (Edge<E> e : n.state().edges()) {
                SearchNode<Node<E>> child = new SearchNode<Node<E>>(
                                      e.apex(), n, n.pathCost() + e.weight());
                if (!explored.contains(child) &&
                    !frontier.contains(child)) {
                    comparisons++;
                    if (child.state().equals(goal)) {
                        return searchResults(child, path, comparisons,
                                             pathManeuvers, pathLength);
                    }
                    frontier.add(child);
                }
            }
        }
    }

    /**
     * Returns a path from initial node to goal node using uniform-cost,
     * best-first search.
     *
     * @param  initial
     *     the initial state
     *
     * @param  goal
     *     the goal state
     *
     * @throws GraphNodeNotFoundException
     *     if this graph does not contain a node with the specified element
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    private Object[] uniformCostPathSearch(
            Node<E> initial,
            Node<E> goal)
        throws GraphNodeNotFoundException
    {
        String   path          = "none";
        Integer  comparisons   = 1,
                 pathManeuvers = 0;
        Double   pathLength    = Double.POSITIVE_INFINITY;

        Frontier<SearchNode<Node<E>>>
            frontier = new PriorityQueueFrontier<SearchNode<Node<E>>>(
                    11, new SearchNodeComparator());
        frontier.add(new SearchNode<Node<E>>(initial, null, 0d));

        NavigableSet<SearchNode<Node<E>>>
            explored = new TreeSet<SearchNode<Node<E>>>();

        while (true) {
            if (frontier.isEmpty()) {
                Object[] results = { path, comparisons,
                                     pathManeuvers, pathLength };
                return results;
            }
            SearchNode<Node<E>> n = frontier.remove();
            comparisons++;
            if (n.state().equals(goal)) {
                return searchResults(n, path, comparisons,
                                     pathManeuvers, pathLength);
            }
            explored.add(n);
            for (Edge<E> e : n.state().edges()) {
                SearchNode<Node<E>> child = new SearchNode<Node<E>>(
                        e.apex(), n, n.pathCost() + e.weight());
                if (!explored.contains(child) &&
                    !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
        }
    }

    /**
     * Returns the search results after a goal has been found in one of the
     * search methods.
     *
     * @param child
     *     the child which is the goal
     *
     * @param path
     *     the path from the initial state to the goal state
     *
     * @param comparisons
     *     the number of comparisons performed by the search
     *
     * @param pathManeuvers
     *     the number of maneuvers from the initial state to the goal state
     *
     * @param pathLength
     *     the length of the path from the initial state to the goal state
     *
     * @return
     *     the object array containing the search results where the elements
     *     in the array are as follows:
     *
     *         <blockquote>
     *         <dt>element 0</dt>
     *             <dd>a {@code String}, the list of strings representing path
     *             elements and distances from the initial state (one per
     *             line) from the initial state to the goal state, e.g.,
     *
     *                 <blockquote><pre>
     *                 stringForPathElement1 (42.0)
     *                 stringForPathElement2 (43.0)
     *                 </blockquote></pre>
     *
     *              or "none" if no path was found</dd>
     *
     *         <dt>element 1</dt>
     *             <dd>an {@code Integer}, the number of comparisons performed
     *             by the search</dd>
     *
     *         <dt>element 2</dt>
     *             <dd>an {@code Integer}, the number of maneuvers from the
     *             initial state to the goal state, or zero when no path was
     *             found</dd>
     *
     *         <dt>element 3</dt>
     *             <dd>a {@code Double}, the length of the path from the
     *             initial state to the goal state or Double.POSITIVE_INFINITY
     *             when no path was found</dd>
     *         </dl>
     *         </blockquote>
     */
    private Object[] searchResults(
            SearchNode<Node<E>> child, String path,
            int comparisons, int pathManeuvers, double pathLength)
    {
        pathLength = child.pathCost();
        Stack<SearchNode<Node<E>>>
            stack = new Stack<SearchNode<Node<E>>>();
        while (true) {
            stack.add(child);
            if (child.parent() == null) {
                break;
            }
            child = child.parent();
        }
        path = "";
        pathManeuvers = stack.size() - 1;
        while (!stack.isEmpty()) {
            SearchNode<Node<E>> sn = stack.pop();
            path += sn.state() +
                " (" + sn.pathCost() + ")\n";
        }
        Object[] results = { path, comparisons,
                             pathManeuvers, pathLength };
        return results;
    }

    /**
     * Returns a string representation of this graph.  This prints a listing
     * of each node, followed by a colon, followed by a comma seperated list
     * of all nodes connected to it by edges.
     *
     * @return
     *     a string representation of this graph
     */
    public String toString() {
        String s = "";

        for (Node<E> n : nodes) {
            s += n + ": ";
            String sep = "";
            for (Edge<E> e : n.edges()) {
                s += sep + e.apex() + " (" + e.weight() + ")";
                sep = ", ";
            }
            s += "\n";
        }

        return s;
    }
}

/**
 * This class implements a node in the search tree generated by a search for a
 * path in this graph.
 *
 * @param <T>
 *     the type of the state of this search tree node
 */
class SearchNode<T> implements Comparable<SearchNode<T>> {

    /**
     * The state in the state space to which this search tree node
     * corresponds.
     */
    private T state;

    /**
     * The node in the search tree that generated this node.
     */
    private SearchNode<T> parent;

    /**
     * The cost of the path from the initial state in the state space to the
     * state of this node.
     */
    private double pathCost;

    /**
     * Construct a new earch tree node with the specified state, parent, and
     * path-cost.  
     *
     * @param state
     *     the state in the state space to which this node corresponds
     *
     * @param parent
     *     the node in the search tree that generated this node
     *
     * @param pathCost
     *     the cost of the path from the initial state in the state space to
     *     this node
     */
    public SearchNode (T state, SearchNode<T> parent, double pathCost) {
        this.state = state;
        this.parent = parent;
        this.pathCost = pathCost;
    }

    /**
     * Returns the state in the state space to which this node corresponds.
     *
     * @return
     *     the state in the state space to which this node corresponds
     */
    public T state() {
        return state;
    }

    /**
     * Returns the node in the search tree that generated this node.
     *
     * @return
     *     the node in the search tree that generated this node
     */
    public SearchNode<T> parent() {
        return parent;
    }

    /**
     * Returns the cost of the path from the initial state in the state space
     * to this node.
     *
     * @return
     *     the cost of the path from the initial state in the state space to
     *     this node
     */
    public double pathCost() {
        return pathCost;
    }

    /**
     * Compares two search tree nodes by comparing the string representation
     * of their states.  The comparison of the nodes is delegated to the
     * {@code compareTo} method of strings.
     *
     * @param anotherSearchNode
     *     the {@code SearchNode} to compare this one to
     *
     * @return
     *     the value {@code 0} if the string representation of the state of
     *     the node argument is equal to the string representation of the
     *     state of this node; a value less than {@code 0} if the string
     *     representation of the state of this node is lexicographically less
     *     than the string representation of the state of the node argument;
     *     and a value greater than {@code 0} if the string representation of
     *     the state of this node is lexicographically greater than the string
     *     representation of the state of the node argument
     */
    public int compareTo(SearchNode<T> anotherSearchNode) {
        if (this == anotherSearchNode) {
            return 0;
        }
        return state.toString().compareTo(
                anotherSearchNode.state().toString());
    }

    /**
     * Compares this search tree node to the specified object. Returns {@code
     * true} if and only if the argument is not {@code null} and is a {@code
     * SearchNode} object that has a state that is equivalent to the state of
     * this object.
     *
     * @param anotherObject
     *     the object to compare this one to
     *
     * @return
     *     {@code true} if the given object represents a {@code SearchNode}
     *     equivalent to this search tree node, {@code false} otherwise.
     */
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject instanceof SearchNode) {
            SearchNode<T> anotherSearchNode = (SearchNode<T>)anotherObject;
            return (state.equals((anotherSearchNode).state()));
        }
        return false;
    }

    /**
     * Returns a hash code for this search tree node.  The hash code for a
     * {@code SearchNode} is delegated to the hash code of its corresponding
     * state: {@code state.hashCode()}.
     *
     * @return
     *     a hash code for this search tree node
     */
    public int hashCode() {
        return state.hashCode();
    }
}

/**
 * {@code SearchNodeComparator} implements a {@code SearchNode} {@code
 * Comparator} for use by the priority queue in the uniform-cost, best-first
 * search in {@code Graph}.  It simply implements compare to compare the path
 * cost of nodes.  This has the effect of ordering nodes in the priority queue
 * by the path cost of the node.
 */
class SearchNodeComparator implements Comparator {

    /**
     * Construct a new {@code SearchNodeComparator}.
     */
    public SearchNodeComparator() {

    }

    /**
     * Compare the specified objects according to path cost.
     *
     * @param o1 the first object
     * @param o2 the second object
     */
    public int compare(Object o1, Object o2) {
        if (o1 == o2) {
            return 0;
        }
        SearchNode n1 = (SearchNode)o1;
        SearchNode n2 = (SearchNode)o2;
        return (int)(n1.pathCost() - n2.pathCost());
    }
}

/**
 * {@code Node} implements a node for this graph.
 */
class Node<E> {

    /**
     * The element stored at this node.
     */
    private E element;

    /**
     * Indicates whether edges of this node are directed.  This is {@code
     * true} if and only if edges of this node are direct.
     */
    private boolean directed;

    /**
     * Indicates whether edges of this node are replaceable.  This is {@code
     * true if and only if edges of this node are replaceable.
     */
    private boolean replacement;

    /**
     * The list of edges connected to this node.
     */
    private Set<Edge<E>> edges;

    /**
     * Contructs a new, empty node.
     */
    public Node() {
        this.element = null;
        replacement = false;
        edges = new HashSet<Edge<E>>();
    }

    /**
     * Constructs a new node containing the specified element.
     *
     * @param element
     *     the element to store at this node
     */
    public Node(E element) {
        this.element = element;
        replacement = false;
        edges = new HashSet<Edge<E>>();
    }

    /**
     * Constructs a new node containing the specified element with the
     * specified options.
     *
     * @param e
     *     the element stored by this node
     *
     * @param options
     *     the set of strings of options specifying the behavior of this node
     */
    public Node(E element, Set<String> options) {
        this.element = element;
        if (options.contains("replacement")) {
            replacement = true;
        }
        edges = new HashSet<Edge<E>>();
    }

    /**
     * Returns the element stored by this node.
     *
     * @return
     *     the element stored at this node
     */
    public E element() {
        return element;
    }

    /**
     * Returns the list of edges associated with this node.  Hence, this node
     * is the origin of each of these edges.
     *
     * @return
     *      the list of edges associated with this node
     */
    public Set<Edge<E>> edges() {
        return edges;
    }

    /**
     * Adds an edge to this node using the specified nodes and weight.
     *
     * @param  origin
     *     the origin {@code Node}
     *
     * @param  apex
     *     the apex {@code Node}
     *
     * @throws GraphEdgeIsDupliacteException
     *     when replacement is {@code false} and an edge equivalent to the
     *     one specified by the origin and apex nodes already exists in the
     *     list of edges for this node
     */
    public void addEdge(Node<E> origin, Node<E> apex, double weight)
        throws GraphEdgeIsDuplicateException {

        Edge<E> edge = new Edge<E>(origin, apex, weight);

        if (edges.contains(edge)) {
            if (replacement) {
                edges.remove(edge);
                edges.add(edge);
            }
            else {
                throw new GraphEdgeIsDuplicateException(edge.toString());
            }
        }
        else {
            edges.add(edge);
        }
    }

    /**
     * Remove the edge with the specified origin and apex nodes and weight
     * from this graph.  If this graph is not directed, remove the
     * corresponding edge from this graph too.  If this graph is directed,
     * only remove the first edge.
     *
     * @param origin
     *     the origin {@code Node}
     *
     * @param apex
     *     the apex {@code Node}
     *
     * @param weight
     *     the cost of traversing the edge
     *
     * @throws GraphEdgeNotFoundException
     *     if this graph does not contain the edge with the specified origin
     *     and apex nodes and weight
     */
    public void removeEdge(Node<E> origin, Node<E> apex, double weight)
        throws GraphEdgeNotFoundException {

        Edge<E> edge = new Edge<E>(origin, apex, weight);

        if (edges.contains(edge)) {
            edges.remove(edge);
        }
        else {
            throw new GraphEdgeNotFoundException(edge.toString());
        }
    }

    /**
     * Compares this node to the specified object.  Returns {@code true} if
     * and only if the argument is not {@code null} and is a {@code Node} that
     * has an element equivalent to the element of this node.
     *
     * @param  anotherObject
     *     the object to compare this node to
     *
     * @return
     *     {@code true} if the given object represents a {@code Node}
     *     equivalent to this node, {@code false} otherwise.
     */
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject instanceof Node) {
            Node<E> anotherNode = (Node<E>)anotherObject;
            return element.equals(anotherNode.element());
        }
        return false;
    }

    /**
     * Returns a hash code value for this node.  The generation of the hash
     * code is delegated to the {@code hashCode} function of the element
     * stored at this node.
     *
     * @return
     *     a hash code value for this node
     */
    public int hashCode() {
        return element.hashCode();
    }

    /**
     * Returns a string representation of the element stored by this node.
     *
     * @return
     *     a String representation of the element stored by this node
     */
    public String toString() {
        return element.toString();
    }
}

/**
 * {@code Edge} implements an (weighted) edge of a graph.
 */
class Edge<E> {
    /**
     * The origin node of this edge.
     */
    private Node<E> origin;

    /**
     * The apex node of this edge.
     * */
    private Node<E> apex;

    /**
     * The cost of traversing this edge.
     */
    private double weight;

    /**
     * Construct a new edge as a vector with the specified origin and apex
     * nodes.  This constructor exists to create an unweighted graph.
     *
     * @param origin
     *     the origin node
     *
     * @param apex
     *     the apex node
     */
    public Edge(Node<E> origin, Node<E> apex) {
        this.origin = origin;
        this.apex = apex;
        this.weight = 1d;
    }

    /**
     * Construct a new, weighted edge with the specified origin and apex nodes
     * and weight.
     *
     * @param origin
     *     the origin node
     *
     * @param apex
     *     the apex node
     *
     * @param weight
     *     the cost of traversing this edge
     */
    public Edge(Node<E> origin, Node<E> apex, double weight) {
        this.origin = origin;
        this.apex = apex;
        this.weight = weight;
    }

    /**
     * Returns the origin node of this edge.
     *
     * @return
     *     returns the origin node of this edge
     */
    public Node<E> origin() {
        return origin;
    }

    /**
     * Returns the apex node of this edge.
     *
     * @param apex
     *     the apex node of this edge
     */
    public Node<E> apex() {
        return apex;
    }

    /**
     * Return the cost of traversing this edge.
     *
     * @param weight
     *     return the cost of traversing this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Compare this edge to the specified object.  Returns {@code true} if and
     * only if the argument is not {@code null} and is an {@code Edge} object
     * that has equivalent origin and apex nodes.
     *
     * @param  anotherObject
     *     the object to compare this edge to
     *
     * @return
     *     {@code true} if the given object represents an edge equivalent to
     *     this edge, {@code false} otherwise.
     */
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject instanceof Edge) {
            Edge<E> anotherEdge = (Edge<E>)anotherObject;
            if (origin.equals((anotherEdge).origin()) &&
                apex.equals((anotherEdge).apex()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a hash code value for this edge.  This hash code is generated
     * from the fields of this edge as
     *
     *     <blockquote><pre>
     *     1 * 11 + origin.hasCode() * 11 + apex.hashCode()
     *     </blockquote></pre>
     *
     * @return
     *     a hash code value for this edge
     */
    public int hashCode() {
        int hash = 1;
        hash = hash * 11 + origin.hashCode();
        hash = hash * 11 + apex.hashCode();
        return hash;
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return
     *     a String representation of this edge
     */
    public String toString() {
        if (weight != 1d) {
            return origin + " ----(" + weight + ")----> " + apex;
        }
        else {
            return origin + " ----> " + apex;
        }
    }
}

/**
 * The {@code Frontier} interface defines a set of classes that implement the
 * frontier of a search algorithm.
 *
 * @param E the type of element that is stored in this frontier
 */
interface Frontier<E> {

    /**
     * Add an element to the frontier.
     *
     * @param element the element to add this frontier
     */
    public void add(E element);

    /**
     * Remove an element from the frontier.
     *
     * @return an element from the frontier
     */
    public E remove();

    /**
     * Returns {@code ture} if and only if this frontier contains the specified
     * element.
     *
     * @param element the specified element
     */
    public boolean contains(E element);

    /**
     * Returns {@code true} if and only if this frontier is empty.
     */
    public boolean isEmpty();
}

/**
 * The {@code ListFrontier} class implement the frontier of a search algorithm
 * using a FIFO {@code Queue}.
 *
 * @param E the type of element that is stored in this frontier
 */
class QueueFrontier<E> implements Frontier<E> {

    /**
     * The queue for this frontier
     */
    private Queue<E> queue;

    /**
     * Construct a new, empty frontier.
     */
    public QueueFrontier() {
        queue = new LinkedList<E>();
    }

    /**
     * Add an element to the frontier.
     *
     * @param element the element to add this frontier
     */
    public void add(E element) {
        queue.add(element);
    }

    /**
     * Remove an element from the frontier.
     *
     * @return an element from the frontier
     */
    public E remove() {
        return queue.remove();
    }

    /**
     * Returns {@code ture} if and only if this frontier contains the specified
     * element.
     *
     * @param element the specified element
     */
    public boolean contains(E element) {
        return queue.contains(element);
    }

    /**
     * Returns {@code true} if and only if this frontier is empty.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * The {@code ListFrontier} class implement the frontier of a search algorithm
 * using a LIFO {@code Queue}.
 *
 * @param E the type of element that is stored in this frontier
 */
class StackFrontier<E> implements Frontier<E> {

    /**
     * The queue for this frontier
     */
    private Stack<E> queue;

    /**
     * Construct a new, empty frontier.
     */
    public StackFrontier() {
        queue = new Stack<E>();
    }

    /**
     * Add an element to the frontier.
     *
     * @param element the element to add this frontier
     */
    public void add(E element) {
        queue.push(element);
    }

    /**
     * Remove an element from the frontier.
     *
     * @return an element from the frontier
     */
    public E remove() {
        return queue.pop();
    }

    /**
     * Returns {@code ture} if and only if this frontier contains the specified
     * element.
     *
     * @param element the specified element
     */
    public boolean contains(E element) {
        return queue.contains(element);
    }

    /**
     * Returns {@code true} if and only if this frontier is empty.
     */
    public boolean isEmpty() {
        return queue.empty();
    }
}

/**
 * The {@code PriorityQueueFrontier} class implement the frontier of a search
 * algorithm using a {@code PriorityQueue}.
 *
 * @param E the type of element that is stored in this frontier
 */
class PriorityQueueFrontier<E> implements Frontier<E> {

    /**
     * The queue for this frontier
     */
    private PriorityQueue<E> queue;

    /**
     * Construct a new, empty priority-queue-backed frontier.
     */
    public PriorityQueueFrontier() {
        queue = new PriorityQueue<E>();
    }

    /**
     * Construct a new, empty priority-queue-backed frontier.
     *
     * @param initialCapacity the initial capacity of this
     *                        priority-queue-backed frontier
     *
     * @param comparator      the {@code Comparator} used to order elements in
     *                        this priority-queue-backed frontier
     */
    public PriorityQueueFrontier(int initialCapacity,
            Comparator<E> comparator) {
        queue = new PriorityQueue<E>(initialCapacity, comparator);
    }

    /**
     * Add an element to the frontier.
     *
     * @param element the element to add this frontier
     */
    public void add(E element) {
        queue.add(element);
    }

    /**
     * Remove an element from the frontier.
     *
     * @return an element from the frontier
     */
    public E remove() {
        return queue.poll();
    }

    /**
     * Returns {@code ture} if and only if this frontier contains the specified
     * element.
     *
     * @param element the specified element
     */
    public boolean contains(E element) {
        return queue.contains(element);
    }

    /**
     * Returns {@code true} if and only if this frontier is empty.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
