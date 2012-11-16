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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * {@code Graph} implements an undirected graph.
 *
 * @author  <a href="mailto:barkle36@gmail.com">Andrew Allen Barkley</a>
 * @version 2012-11-13
 */
public class Graph<E> {
    /**
     * The set of nodes that compose this graph.
     */
    private Set<Node<E>> nodes;

    /**
     * Construct an empty {@code Graph}.
     */
    public Graph() {
        nodes = new HashSet<Node<E>>();
    }

    /**
     * Add a node with the specified element to this graph.
     *
     * @param  element
     *     the element to add to this graph
     */
    public void addNode(E element) {
        nodes.add(new Node<E>(element));
    }

    /**
     * Return the specified node from this graph.
     *
     * @param  target
     *     the specified node
     */
    private Node<E> node(Node<E> target) {
        for (Node<E> n : nodes) {
            if (n.equals(target)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Return {@code true} if and only if this graph contains the specified
     * element, {@code false} otherwise.
     *
     * @param element
     *     the specified element
     *
     * @return
     *     {@code true} if and only if this graph contains the specified element
     */
    public boolean contains(E element) {
        return nodes.contains(new Node<E>(element));
    }

    /**
     * Returns the set of elements stored by nodes in this graph.
     *
     * @return
     *     the set of elements stored by nodes in this graph
     */
    public Set<E> elements() {
        Set<E> elements = new HashSet<E>();
        for (Node<E> node: nodes) {
            elements.add(node.element());
        }
        return elements;
    }

    /**
     * Add an edge to this graph from the node for the origin element to the
     * node for the apex elements.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     */
    public void addEdge(E origin, E apex) {
        addEdge(origin, apex, 1d);
    }

    /**
     * Remove the edge with the specified origin and apex elements from this
     * graph.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     */
    public void removeEdge(E origin, E apex) {
        removeEdge(origin, apex, 1d);
    }

    /**
     * Add an edge to this graph from the node for the origin element to the
     * node for the apex elements with the specified weight.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @param weight
     *     the cost of traversing the edge
     */
    public void addEdge(E origin, E apex, double weight) {
        Node<E> nodeA = new Node<E>(origin);
        Node<E> nodeB = new Node<E>(apex);

        node(nodeA).addEdge(node(nodeA), node(nodeB), weight);
        node(nodeB).addEdge(node(nodeB), node(nodeA), weight);
    }

    /**
     * Remove the edge with the specified origin and apex elements and weight
     * from this graph.
     *
     * @param origin
     *     the element of the origin node
     *
     * @param apex
     *     the element of the apex node
     *
     * @param weight
     *     the cost of traversing the edge
     */
    public void removeEdge(E origin, E apex, double weight) {
        Node<E> nodeA = new Node<E>(origin);
        Node<E> nodeB = new Node<E>(apex);

        node(nodeA).removeEdge(node(nodeA), node(nodeB), weight);
        node(nodeB).removeEdge(node(nodeB), node(nodeA), weight);
    }

    /**
     * Returns {@code true} if this graph contains an edge between the nodes for
     * the specified elements, {@code false} otherwise.
     *
     * @param element1
     *     an element in this graph
     *
     * @param element2
     *     an element in this graph
     *
     * @return
     *     {@code true} if this graph contains an edge between the nodes for the
     *     specified elements, {@code false} otherwise.
     */
    public boolean containsEdge(E element1, E element2) {
        if (!contains(element1) || !contains(element2)) {
            return false;
        }

        for (E element: neighbors(element1)) {
            if (element.equals(element2)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the set containing the elements whose nodes are connected by an
     * edge to the node for the specified element.
     *
     * @return
     *     the set containing the elements whose nodes are connected by an edge
     *     to the node for the specified element
     */
    public Set<E> neighbors(E element) {
        Set<E> neighbors = new HashSet<E>();
        Node<E> node = node(new Node<E>(element));
        for (Edge<E> edge: node.edges()) {
            neighbors.add(edge.apex().element());
        }
        return neighbors;
    }

    /**
     * Creates and returns a copy of this graph.  This performs a deep copy of
     * the graph data structure, i.e., without copying the elements stored by
     * it.
     *
     * @return
     *      copy of this graph
     */
    public Graph<E> clone() {
        Graph<E> clone = new Graph<E>();
        for (Node<E> node: nodes) {
            clone.addNode(node.element());
        }
        for (Node<E> node: nodes) {
            for (Edge<E> edge: node.edges()) {
                clone.addEdge(edge.origin().element(),
                              edge.apex().element(),
                              edge.weight());
            }
        }
        return clone;
    }

    /**
     * Compares this graph to the specified object.  Returns {@code true} if the
     * specified object is an equivalent graph.
     *
     * @param object
     *     the specified object
     *
     * @return
     *     {@code true} if the specified object is an equivalent graph, {@code
     *     false} otherwise
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Graph) {
            Graph<E> graph = (Graph<E>)object;
            if (!graph.elements().equals(elements())) {
                return false;
            }
            else {
                for (E element: elements()) {
                    if (!graph.neighbors(element).equals(neighbors(element))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns a path from initial element to the 'closest' goal
     * element.
     *
     * @param initial
     *     the initial element
     *
     * @param goals
     *     the list of goal elements
     *
     * @return
     *     a list containing the elements along the path from initial
     *     element to goal element, containing initial if initial is
     *     equivalent to a goal in goals, empty if no path was found
     */
    public List<E> findPath(E initial, Set<E> goals) {
        List<E> path             = new ArrayList<E>();
        Double  pathSize         = Double.POSITIVE_INFINITY;
        List<E> shortestPath     = path;
        Double  shortestPathSize = pathSize;

        for (E goal: goals) {
            path     = findPath(initial, goal);
            pathSize = Double.valueOf(path.size());

            if (pathSize > 0d && pathSize < shortestPathSize) {
                shortestPath     = path;
                shortestPathSize = pathSize;
            }
        }

        return shortestPath;
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
     * @return
     *     a list containing the elements along the path from initial
     *     element to goal element, containing initial if initial is
     *     equivalent to goal, empty if no path was found
     */
    public List<E> findPath(E initial, E goal) {
        List<E> path = new ArrayList<E>();

        if (initial.equals(goal)) {
            path.add(initial);
            return path;
        }

        Node<E> initialNode = node(new Node<E>(initial));
        Node<E> goalNode = node(new Node<E>(goal));

        Frontier<SearchNode<Node<E>>> frontier
            = new QueueFrontier<SearchNode<Node<E>>>();
        frontier.add(new SearchNode<Node<E>>(initialNode, null, 0d));

        NavigableSet<SearchNode<Node<E>>>
            explored = new TreeSet<SearchNode<Node<E>>>();

        while (true) {
            if (frontier.isEmpty()) {
                return path;
            }
            SearchNode<Node<E>> n = frontier.remove();
            explored.add(n);
            for (Edge<E> e : n.state().edges()) {
                SearchNode<Node<E>> child = new SearchNode<Node<E>>(
                                e.apex(), n, n.pathCost() + e.weight());
                if (!explored.contains(child) &&
                    !frontier.contains(child)) {
                    if (child.state().equals(goalNode)) {
                        Stack<E> stack = new Stack<E>();
                        while (true) {
                            stack.push(child.state().element());
                            if (child.parent() == null) {
                                break;
                            }
                            child = child.parent();
                        }
                        while (!stack.isEmpty()) {
                            path.add(stack.pop());
                        }
                        return path;
                    }
                    frontier.add(child);
                }
            }
        }
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
     * Construct a new search tree node with the specified state, parent, and
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
    public SearchNode(T state, SearchNode<T> parent, double pathCost) {
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
 * {@code Node} implements a node for this graph.
 */
class Node<E> {

    /**
     * The element stored at this node.
     */
    private E element;

    /**
     * The list of edges connected to this node.
     */
    private Set<Edge<E>> edges;

    /**
     * Constructs a new, empty node.
     */
    public Node() {
        this.element = null;
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
     */
    public void addEdge(Node<E> origin, Node<E> apex, double weight) {
        edges.add(new Edge<E>(origin, apex, weight));
    }

    /**
     * Remove the edge with the specified origin and apex nodes and weight
     * from this graph.
     *
     * @param origin
     *     the origin {@code Node}
     *
     * @param apex
     *     the apex {@code Node}
     *
     * @param weight
     *     the cost of traversing the edge
     */
    public void removeEdge(Node<E> origin, Node<E> apex, double weight) {
        edges.remove(new Edge<E>(origin, apex, weight));
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
     * nodes.
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
     *     the origin node of this edge
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
     * @param element
     *     the element to add this frontier
     */
    public void add(E element);

    /**
     * Remove an element from the frontier.
     *
     * @return an element from the frontier
     */
    public E remove();

    /**
     * Returns {@code true} if and only if this frontier contains the specified
     * element.
     *
     * @param element
     *     the specified element
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
 * @param E
 *     the type of element that is stored in this frontier
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
     * @param element
     *     the element to add this frontier
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
     * Returns {@code true} if and only if this frontier contains the specified
     * element.
     *
     * @param element
     *     the specified element
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
