package graph.Test;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyListGraph;


/**
 * This is a file that contains some code to create a
 * graph and test some of its methods.
 *
 * The graph is the graph of airports and the distances
 * between them that we have seen in the lectures.
 *
 * The graph contains vertices of Type String and edges of Type Integer
 *
 * This class tests all methods
 * of the adjacency list graph implementation.
 *
 */

public class AdjacencyListTest1 {
    public static void main( String[] args ) throws Exception {
        IGraph<String,Integer> g = new AdjacencyListGraph<>();

        // create some vertices
        IVertex<String> hnl = g.insertVertex( "HNL" );
        IVertex<String> lax = g.insertVertex( "LAX" );
        IVertex<String> sfo = g.insertVertex( "SFO" );
        IVertex<String> ord = g.insertVertex( "ORD" );
        IVertex<String> dfw = g.insertVertex( "DFW" );
        IVertex<String> lga = g.insertVertex( "LGA" );
        IVertex<String> pvd = g.insertVertex( "PVD" );
        IVertex<String> mia = g.insertVertex( "MIA" );

        // create some edges
        IEdge<Integer> hnllax = g.insertEdge( hnl, lax, 2555 );
        IEdge<Integer> laxsfo = g.insertEdge( lax, sfo, 337 );
        IEdge<Integer> ordsfo = g.insertEdge( ord, sfo, 1843 );
        IEdge<Integer> laxord = g.insertEdge( lax, ord, 1743 );
        IEdge<Integer> dfwlax = g.insertEdge( dfw, lax, 1233 );
        IEdge<Integer> ordpvd = g.insertEdge( ord, pvd, 849 );
        IEdge<Integer> dfwlga = g.insertEdge( dfw, lga, 1387 );
        IEdge<Integer> dfwmia = g.insertEdge( dfw, mia, 1120 );
        IEdge<Integer> lgamia = g.insertEdge( lga, mia, 1099 );

        System.out.println("------------------------------test areAdjacent------------------------------------");

        // test areAdjacent
        if ( g.areAdjacent( lga, pvd ) ) {
            System.out.println("LGA and PVD adjacent before inserting \'lgapvd': yes");
            System.out.println("Test areAdjacent: " + false);
        }
        else {
            System.out.println("LGA and PVD adjacent before inserting \'lgapvd': no");
            System.out.println("Test areAdjacent: " + true);
        }

        System.out.println("------------------------------test areAdjacent-------------------------------------");

        // another test for areAdjacent and insertEdge
        IEdge<Integer> lgapvd = g.insertEdge( lga, pvd, 142 );
        if ( g.areAdjacent( lga, pvd ) ) {
            System.out.println("LGA and PVD adjacent after inserting lga-pvd edge: yes");
            System.out.println("Test areAdjacent and insertEdge: " + true);
        }
        else {
            System.out.println("LGA and PVD adjacent after inserting lga-pvd edge: no");
            System.out.println("Test areAdjacent and insertEdge: " + false);
        }

        System.out.println("------------------------------test endVertices-------------------------------------");

        // test endVertices
        IVertex<String>[] ends = g.endVertices( hnllax );
        if ( ( ends[0] == lax && ends[1] == hnl ) ||
                ( ends[1] == lax && ends[0] == hnl ) )
            System.out.println( "End vertices of LAX<->ORD: correct" );
        else
            System.out.println( "End vertices of LAX<->ORD: incorrect" );

        System.out.println("------------------------------test opposite----------------------------------------");

        // test opposite
        if ( g.opposite( lax, hnllax ) == hnl ) {
            System.out.println("Opposite of PVD along LGA<->PVD: correct");
            System.out.println("Test opposite: " + true);
        }
        else {
            System.out.println("Opposite of PVD along LGA<->PVD: incorrect");
            System.out.println("Test opposite: " + false);
        }

        System.out.println("----------------------------test getting an object---------------------------------");

        // test getting an object from the graph
        String hnlElement = hnl.element();
        System.out.println( "Element of HNL is: " + hnlElement );
        System.out.println( "Test getting an object from the graph: " + hnlElement.equals("HNL"));

        System.out.println("----------------------------test getting the element-------------------------------");

        // test getting the element stored in the edge
        int hnllaxElement = hnllax.element();
        System.out.println( "Distance from HNL to LAX is: " + hnllaxElement );
        System.out.println( "Test getting the element stored in the edge: " + (hnllaxElement == 2555));

        System.out.println("----------------------------test incident edges------------------------------------");

        // test incident edges of a vertex
        System.out.print("incidentEdges of \'dfw': ");
        IIterator<IEdge<Integer>> it1 = g.incidentEdges(dfw);
        int num1 = ((AdjacencyListGraph) g).printIterator(it1);
        System.out.println("\nNumber of incident edges of \'dfw': " + num1);
        IEdge[] correct1 = new IEdge[]{dfwlax, dfwlga, dfwmia};
        it1 = g.incidentEdges(dfw);
        System.out.println("Test incident edges of \'dfw': " + ((AdjacencyListGraph) g).testEqual(it1, correct1));

        System.out.println("-----------------------------test incident edges-----------------------------------");

        // another test incident edges of a vertex
        System.out.print("incidentEdges of \'lax': ");
        IIterator<IEdge<Integer>> it3 = g.incidentEdges(lax);
        int num3 = ((AdjacencyListGraph) g).printIterator(it3);
        System.out.println("\nNumber of incident edges of \'lax': " + num3);
        IEdge[] correct2 = new IEdge[]{hnllax, laxsfo, laxord, dfwlax};
        it3 = g.incidentEdges(lax);
        System.out.println("Test incident edges of \'lax': " + ((AdjacencyListGraph) g).testEqual(it3, correct2));

        System.out.println("-----------------------------test remove vertex------------------------------------");

        // test: remove vertex will also remove its incident vertices which compose other vertices
        g.removeVertex(lax);

        // print the incidentEdges of dfw
        System.out.print("incidentEdges of dfw after removing vertex \'lax': ");
        IIterator<IEdge<Integer>> it2 = g.incidentEdges(dfw);
        int num2 = ((AdjacencyListGraph) g).printIterator(it2);
        System.out.println("\nNumber of incident edges of \'dfw': " + num2);
        IEdge[] correct3 = new IEdge[]{dfwlga, dfwmia};
        it2 = g.incidentEdges(dfw);
        System.out.println("Test incident edges of \'dfw': " + ((AdjacencyListGraph) g).testEqual(it2, correct3));

        System.out.println("-----------------------------test remove vertex------------------------------------");

        // test remove a vertex that has also already been removed from the graph
        g.removeVertex(lax); // nothing changes

        System.out.println("-----------------------------test the incident edge list---------------------------");

        // test the incident edge list after removing edge
        g.removeEdge(dfwmia);
        System.out.print("incidentEdges of dfw after removing vertex \'lax' and edge \'dfwmia': ");
        IIterator<IEdge<Integer>> it4 = g.incidentEdges(dfw);
        int num4 = ((AdjacencyListGraph) g).printIterator(it4);
        System.out.println("\nNumber of incident edges of dfw: " + num4);
        IEdge[] correct4 = new IEdge[]{dfwlga};
        it4 = g.incidentEdges(dfw);
        System.out.println("Test incident edges of \'dfw': " + ((AdjacencyListGraph) g).testEqual(it4, correct4));

        // test areAdjacent after removing edge
        System.out.println("---------------------------------test areAdjacent--------------------------------");
        boolean areAdjacent = g.areAdjacent(dfw, mia);
        if (!areAdjacent)
            System.out.println("Test areAdjacent after removing edge: correct");
        else
            System.out.println("Test areAdjacent after removing edge: incorrect");

        System.out.println("-----------------------------test the edge list-----------------------------------");

        // test the edge list after removing edge
        System.out.print("All edges removing lax: ");
        IIterator<IEdge<Integer>> it5 = g.edges();
        int num5 = ((AdjacencyListGraph) g).printIterator(it5);
        System.out.println("\nNumber of incident edges of dfw: " + num5);
        IEdge[] correct5 = new IEdge[]{ordsfo, ordpvd, dfwlga, lgamia, lgapvd};
        it5 = g.edges();
        System.out.println("Test incident edges of \'dfw': " + ((AdjacencyListGraph) g).testEqual(it5, correct5));


        System.out.println("-----------------------------test remove an edge---------------------------------");

        // test remove an edge that has already been removed from the graph
        g.removeEdge(dfwmia); // nothing changes

        System.out.println("-----------------------------test replace vertex---------------------------------");

        // test replace vertex
        System.out.println("ord: " + ord);
        g.replace(ord, "NEW ORD");
        System.out.println("ord after replacing vertex: " + ord);
        System.out.println("Test replace vertex: " + ord.element().equals("NEW ORD"));

        System.out.println("-----------------------------test replace edge-----------------------------------");

        // test replace edge
        System.out.println("lgamia: " + lgamia);
        g.replace(lgamia, -1);
        System.out.println("lgamia after replacing edge: " + lgamia);
        System.out.println("Test replace edge: " + lgamia.element().equals(-1));

    }
}