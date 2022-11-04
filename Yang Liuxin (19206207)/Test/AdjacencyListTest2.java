package graph.Test;

import graph.core.*;
import graph.impl.AdjacencyListGraph;

/**
 * This is a file that contains some code to create a
 * graph and test some of its methods.
 *
 * The graph is composed of several connected components.
 *
 * The graph contains vertices of Type Integer and edges of Type Integer
 *
 * This class tests all methods
 * of the adjacency list graph implementation.
 *
 */

public class AdjacencyListTest2 {
    public static void main(String[] args) {
        IGraph<Integer,Integer> g = new AdjacencyListGraph<>();

        // create some 13 vertices in total in three connected components

        // create the first connected component
        IVertex<Integer> v0 = g.insertVertex(0);
        IVertex<Integer> v1 = g.insertVertex(1);
        IVertex<Integer> v2 = g.insertVertex(2);
        IVertex<Integer> v3 = g.insertVertex(3);
        IVertex<Integer> v4 = g.insertVertex(4);
        IVertex<Integer> v5 = g.insertVertex(5);
        IVertex<Integer> v6 = g.insertVertex(6);

        // create the second connected component
        IVertex<Integer> v7 = g.insertVertex(7);
        IVertex<Integer> v8 = g.insertVertex(8);

        // create the third connected component
        IVertex<Integer> v9 = g.insertVertex(9);
        IVertex<Integer> v10 = g.insertVertex(10);
        IVertex<Integer> v11 = g.insertVertex(11);
        IVertex<Integer> v12 = g.insertVertex(12);

        // create some edges
        IEdge<Integer> e1 = g.insertEdge( v0, v5, 50 );
        IEdge<Integer> e2 = g.insertEdge( v0, v1, 10 );
        IEdge<Integer> e3 = g.insertEdge( v0, v2, 20 );
        IEdge<Integer> e4 = g.insertEdge( v0, v6, 60 );
        IEdge<Integer> e5 = g.insertEdge( v3, v5, 35 );
        IEdge<Integer> e6 = g.insertEdge( v4, v5, 45 );
        IEdge<Integer> e7 = g.insertEdge( v3, v4, 34 );
        IEdge<Integer> e8 = g.insertEdge( v4, v6, 46 );
        IEdge<Integer> e9 = g.insertEdge( v7, v8, 78 );
        IEdge<Integer> e11 = g.insertEdge( v9, v10, 900 );
        IEdge<Integer> e10 = g.insertEdge( v9, v11, 911 );
        IEdge<Integer> e12 = g.insertEdge( v9, v12, 912 );
        IEdge<Integer> e13 = g.insertEdge( v11, v12, 1112 );

        // test the element stored in a vertex:
        System.out.println("The element stored in v9 is: " + v9.element());
        System.out.println("Test the element stored in a vertex: " + v9.element().equals(9));

        System.out.println("-----------------------------------------------------------------------------");

        // test the element stored in an edge:
        System.out.println("The element stored in e2 is: " + e2.element());
        System.out.println("Test the element stored in an edge: " + e2.element().equals(10));

        System.out.println("-----------------------------------------------------------------------------");

        // test end vertices of an edge
        IVertex<Integer>[] endVer = g.endVertices(e1);
        IVertex<Integer> endV1 = endVer[0];
        IVertex<Integer> endV2 = endVer[1];
        System.out.println("End vertices of an edge: " + endV1 + " and " + endV2);
        boolean testEndV = (endV1.equals(v0) && endV2.equals(v5)) || (endV1.equals(v5) && endV2.equals(v0));
        System.out.println("Test end vertices of an edge: " + testEndV);

        System.out.println("-----------------------------------------------------------------------------");

        // test the opposite of a vertex
        IVertex<Integer> oppo = g.opposite(v9, e12);
        System.out.println("The opposite of a vertex: " + oppo);
        System.out.println("Test the opposite of a vertex: " + oppo.equals(v12));

        System.out.println("-----------------------------------------------------------------------------");

        // test whether two vertices are adjacent
        // test 1: if two vertices are not adjacent
        boolean areAdjacent1 = g.areAdjacent(v1, v9);
        if (areAdjacent1)
            System.out.println("v1 and v9 are adjacent");
        else
            System.out.println("v1 and v9 are not adjacent");
        // test 2: if two vertices are adjacent
        boolean areAdjacent2 = g.areAdjacent(v9, v10);
        if (areAdjacent1)
            System.out.println("v9 and v10 are adjacent");
        else
            System.out.println("v9 and v10 are not adjacent");
        boolean testAdMethod = (areAdjacent1 == false) && (areAdjacent2 == true);
        System.out.println("Test whether two vertices are adjacent: " + testAdMethod);

        System.out.println("-----------------------------------------------------------------------------");

        // test replace vertex
        System.out.println("Previous element stored at v8: " + v8.element());
        g.replace(v8, 88888);
        Integer newV8 = v8.element();
        System.out.println("New element stored at v8 after replacement: " + newV8);
        System.out.println("Test replace vertex: " + newV8.equals(88888));

        System.out.println("-----------------------------------------------------------------------------");

        // test replace edge
        System.out.println("Previous element stored at e13: " + e13.element());
        g.replace(e13, -1112);
        Integer newE13 = e13.element();
        System.out.println("New element stored at e13 after replacement: " + newE13);
        System.out.println("Test replace edge: " + newE13.equals(-1112));

        System.out.println("-----------------------------------------------------------------------------");

        // test insert vertex
        // previous vertices
        IIterator vertices1 = g.vertices();
        int count1 = ((AdjacencyListGraph) g).printIterator(vertices1);
        System.out.println();
        System.out.println("The number of vertices previously: " + count1);
        // insert a new vertex
        IVertex<Integer> v13 = g.insertVertex(13);
        IIterator vertices2 = g.vertices();
        int count2 = ((AdjacencyListGraph) g).printIterator(vertices2);
        System.out.println();
        System.out.println("The number of vertices after inserting a new vertex: " + count2);
        System.out.println("Test insert vertex: " + (count1 == 13 && count2 ==14));

        System.out.println("-----------------------------------------------------------------------------");

        // test insert edge
        IIterator edges1 = g.edges();
        int count3 = ((AdjacencyListGraph) g).printIterator(edges1);
        System.out.println();
        System.out.println("The number of vertices previously: " + count3);
        // insert a new edge
        IEdge<Integer> e14 = g.insertEdge( v12, v13, 1213 );
        IIterator edges2 = g.edges();
        int count4 = ((AdjacencyListGraph) g).printIterator(edges2);
        System.out.println();
        System.out.println("The number of edges after inserting a new edge: " + count4);
        System.out.println("Test insert edge: " + (count1 == 13 && count2 ==14));

        System.out.println("-----------------------------------------------------------------------------");

        // test remove vertex
        System.out.println("v0 and v5 are adjacent: " + g.areAdjacent(v0, v5));
        IIterator incident0 = g.incidentEdges(v0);
        int numIn1 = ((AdjacencyListGraph) g).printIterator(incident0);
        System.out.println();
        System.out.println("The number of incident edges of v0: " + numIn1);
        g.removeVertex(v5);
        IIterator incident1 = g.incidentEdges(v0);
        int numIn2 = ((AdjacencyListGraph) g).printIterator(incident1);
        System.out.println();
        System.out.println("The number of incident edges of v0 after removing v5: " + numIn2);
        System.out.println("Test remove vertex: " + (numIn2 == numIn1 - 1));

        System.out.println("-----------------------------------------------------------------------------");

        // test remove vertex
        // special case: remove a vertex that does not belong to the graph.
        g.removeVertex(v5);

        System.out.println("-----------------------------------------------------------------------------");

        // test remove edge
        System.out.println("End vertices of e7: v" + g.endVertices(e7)[0] + ", v" + g.endVertices(e7)[1]);
        IIterator incident2 = g.incidentEdges(v4);
        System.out.print("Incident edges of v4: ");
        int numIn3 = ((AdjacencyListGraph) g).printIterator(incident2);
        System.out.println();
        System.out.println("The number of incident edges of v0: " + numIn3);
        g.removeEdge(e7);
        IIterator incident3 = g.incidentEdges(v4);
        System.out.print("Incident edges of v4 (after removing the edge between v3 and v4): ");
        int numIn4 = ((AdjacencyListGraph) g).printIterator(incident3);
        System.out.println();
        System.out.println("The number of incident edges of v0 after removing (after removing the edge between v3 and v4): " + numIn4);
        System.out.println("Test remove vertex: " + (numIn4 == numIn3 - 1));

        System.out.println("-----------------------------------------------------------------------------");

        AdjacencyListGraph graph = (AdjacencyListGraph) g;

        // test incident edges
        IEdge[] correctIn = new IEdge[]{e12, e13, e14};
        IIterator incidentV12 = g.incidentEdges(v12);
        System.out.println("Test incident edges: " + graph.testEqual(incidentV12, correctIn));

        System.out.println("-----------------------------------------------------------------------------");

        // test vertices
        IVertex[] correctVer = new IVertex[]{v0, v1, v2, v3, v4, v6, v7, v8, v9, v10, v11, v12, v13};
        IIterator vertices = g.vertices();
        System.out.println("Test vertices: " + graph.testEqual(vertices, correctVer));

        System.out.println("-----------------------------------------------------------------------------");

        // test edges
        IEdge[] correctEdges = new IEdge[]{e2, e3, e4, e8, e9, e11, e10, e12, e13, e14};
        IIterator edges = g.edges();
        System.out.println("Test edges: " + graph.testEqual(edges, correctEdges));

    }
}
