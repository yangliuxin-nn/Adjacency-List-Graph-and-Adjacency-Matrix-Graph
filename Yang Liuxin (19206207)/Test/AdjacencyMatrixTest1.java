package graph.Test;

import graph.core.IEdge;

import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyMatrixGraph;


/**
 * This is a file that contains some code to create a
 * AdjacencyMatrix graph and test some of its methods.
 *
 * The graph is the graph of airports and the distances
 * between them that we have seen in the lectures.
 *
 */

public class AdjacencyMatrixTest1 {
    public static void main( String[] args ) throws Exception {
        IGraph<String,Integer> g = new AdjacencyMatrixGraph<>();

        // create some vertices
        IVertex<String> hnl = g.insertVertex( "HNL" );  // 0
        IVertex<String> lax = g.insertVertex( "LAX" );  // 1
        IVertex<String> sfo = g.insertVertex( "SFO" );  //   remove sfo afterwards
        IVertex<String> ord = g.insertVertex( "ORD" );  // 2
        IVertex<String> dfw = g.insertVertex( "DFW" );  // 3
        IVertex<String> lga = g.insertVertex( "LGA" );  // 4
        IVertex<String> pvd = g.insertVertex( "PVD" );  // 5 remove pvd afterwards
        IVertex<String> mia = g.insertVertex( "MIA" );  // 6

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
        IEdge<Integer> lgapvd = g.insertEdge( lga, pvd, 142 );// 1,2 (337); 2,3 (1843)

        System.out.println("-----------------------------------test areAdjacent----------------------------------------------");

        // test areAdjacent
        if ( g.areAdjacent( hnl, sfo ) )
            System.out.println( "HNL and SFO adjacent before inserting the edge \'hnlsfo': yes" );
        else
            System.out.println( "HNL and SFO adjacent before inserting the edge \'hnlsfo': no" );

        System.out.println("-----------------------------------test areAdjacent and insertEdge-------------------------------");

        // another test areAdjacent and insertEdge
        IEdge<Integer> hnlsfo = g.insertEdge(hnl, sfo, 100);
        if ( g.areAdjacent( hnl, sfo ) )
            System.out.println( "HNL and SFO adjacent after inserting the edge \'hnlsfo': yes" );
        else
            System.out.println( "HNL and SFO adjacent after inserting the edge \'hnlsfo': no" );

        System.out.println("------------------------------------test endVertices---------------------------------------------");

        // test endVertices
        IVertex<String>[] ends = g.endVertices( hnllax );
        if ( ( ends[0] == lax && ends[1] == hnl ) ||
                ( ends[1] == lax && ends[0] == hnl ) )
            System.out.println( "End vertices of LAX<->ORD: correct" );
        else
            System.out.println( "End vertices of LAX<->ORD: incorrect" );

        System.out.println("------------------------------------test opposite------------------------------------------------");

        // test opposite
        if ( g.opposite( lax, hnllax ) == hnl )
            System.out.println( "Opposite of PVD along LGA<->PVD: correct" );
        else
            System.out.println( "Opposite of PVD along LGA<->PVD: incorrect" );

        System.out.println("------------------------------------test getting an object---------------------------------------");

        // test getting an object from the graph
        System.out.println( "Element of HNL is: " + hnl.element());
        System.out.println( "Test getting an object from the graph: " + hnl.element().equals("HNL"));

        System.out.println("------------------------------------test replace vertex------------------------------------------");

        // test replace vertex
        g.replace(hnl, "NEWHNL");
        System.out.println( "After replacing vertex, element of HNL is: " + hnl.element());
        System.out.println( "Test replacing an object of the graph: " + hnl.element().equals("NEWHNL"));

        System.out.println("------------------------------------test getting an element--------------------------------------");

        // test getting an element stored in an object.
        int hnllaxElement = hnllax.element();
        System.out.println( "Distance from HNL to LAX is: " + hnllaxElement );
        System.out.println( "Test getting an element stored in an object: " + (hnllaxElement == 2555));

        System.out.println("-----------------------------------test replace edge---------------------------------------------");

        // test replace edge
        g.replace(hnllax, 1);
        System.out.println( "After replacing edge, distance from HNL to LAX is: " + hnllax.element() );
        System.out.println( "Test replacing an element stored in an object: " + (hnllax.element() == 1));

        System.out.println("-----------------------------------test matrix---------------------------------------------------");

        // convert the IGraph to AdjacencyMatrixGraph
        // so that I can use some additional methods to test this graph
        AdjacencyMatrixGraph graph = (AdjacencyMatrixGraph)g;

        graph.printMatrix();
        IEdge[][] correctMatrix0 = new IEdge[][]{
                {null, hnllax, hnlsfo, null, null, null, null, null},
                {hnllax, null, laxsfo, laxord, dfwlax, null, null, null},
                {hnlsfo, laxsfo, null, ordsfo, null, null, null, null},
                {null, laxord, ordsfo, null, null, null, ordpvd, null},
                {null, dfwlax, null, null, null, dfwlga, null, dfwmia},
                {null, null, null, null, dfwlga, null, lgapvd, lgamia},
                {null, null, null, ordpvd, null, lgapvd, null, null},
                {null, null, null, null, dfwmia, lgamia, null, null},
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix0));

        System.out.println("----------------------------------test remove edge----------------------------------------------");

        g.removeEdge(laxsfo);

        // test adjacent after removing its incident edge: laxsfo
        if (g.areAdjacent(lax, sfo)) {
            System.out.println("LAX and SFO are adjacent after removing edge \'laxsfo': Correct");
            System.out.println("Test adjacent after removing its incident edge: " + false);
        } else {
            System.out.println("LAX and SFO are adjacent after removing edge \'laxsfo': Incorrect");
            System.out.println("Test adjacent after removing its incident edge: " + true);
        }

        System.out.println("---------------------------------test remove vertex and replace hnllax--------------------------");

        // print the incidentEdges of lax after removing laxsfo and replace hnllax with 1
        System.out.print("incidentEdges of lax after removing \'laxsfo' and replacing \'hnllax' with 1: ");
        IIterator<IEdge<Integer>> it1 = g.incidentEdges(lax);
        while( it1.hasNext() ) {
            // here I must cast also, since it.next() returns an Object
            IEdge<Integer> v = it1.next();
            System.out.print( v.element() + " ");
        }
        System.out.println();

        System.out.println("Here is the matrix after removing edge \'laxsfo' and replacing \'hnllax' with 1:");

        graph.printMatrix();
        IEdge[][] correctMatrix1 = new IEdge[][]{
                {null, hnllax, hnlsfo, null, null, null, null, null},
                {hnllax, null, null, laxord, dfwlax, null, null, null},
                {hnlsfo, null, null, ordsfo, null, null, null, null},
                {null, laxord, ordsfo, null, null, null, ordpvd, null},
                {null, dfwlax, null, null, null, dfwlga, null, dfwmia},
                {null, null, null, null, dfwlga, null, lgapvd, lgamia},
                {null, null, null, ordpvd, null, lgapvd, null, null},
                {null, null, null, null, dfwmia, lgamia, null, null},
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix1));


        System.out.println("--------------------------------test remove edge---------------------------------------------");

        // test removing an edge that has already been removed from the graph
        g.removeEdge(laxsfo);

        System.out.println("--------------------------------test remove vertex------------------------------------------");

        g.removeVertex(sfo);
        System.out.println("Here is the matrix after removing vertex \'sfo':");
        graph.printMatrix();
        IEdge[][] correctMatrix2 = new IEdge[][]{
                {null, hnllax, null, null, null, null, null},
                {hnllax, null, laxord, dfwlax, null, null, null},
                {null, laxord, null, null, null, ordpvd, null},
                {null, dfwlax, null, null, dfwlga, null, dfwmia},
                {null, null, null, dfwlga, null, lgapvd, lgamia},
                {null, null, ordpvd, null, lgapvd, null, null},
                {null, null, null, dfwmia, lgamia, null, null},
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix2));

        System.out.println("-------------------------------test insert vertex and edge----------------------------------");

        // test insert new vertex and new edge
        IVertex<String> qaq = g.insertVertex( "QAQ" );  // 7
        IVertex<String> wow = g.insertVertex( "WOW" );  // 8
        IEdge<Integer> qaqwow = g.insertEdge( qaq, wow, 520 );
        IEdge<Integer> qaqhnl = g.insertEdge( qaq, hnl, 2130 );
        System.out.println("Here is the matrix after inserting vertex \'qaq', \'wow', and edge \'qaqwow', \'qaqhnl':");
        graph.printMatrix();
        IEdge[][] correctMatrix3 = new IEdge[][]{
                {null, hnllax, null, null, null, null, null, qaqhnl, null},
                {hnllax, null, laxord, dfwlax, null, null, null, null, null},
                {null, laxord, null, null, null, ordpvd, null, null, null},
                {null, dfwlax, null, null, dfwlga, null, dfwmia, null, null},
                {null, null, null, dfwlga, null, lgapvd, lgamia, null, null},
                {null, null, ordpvd, null, lgapvd, null, null, null, null},
                {null, null, null, dfwmia, lgamia, null, null, null, null},
                {qaqhnl, null, null, null, null, null, null, null, qaqwow},
                {null, null, null, null, null, null, null, qaqwow, null}
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix3));

        System.out.println("-----------------------------test remove vertex and edge-------------------------------------");

        // test removing edge and vertex
        // this can also test whether the indices of vertices after removed vertex are updated
        g.removeEdge(dfwlga); // index: dfw(3), lga(4)
        // matrix[3][4] and matrix[4][3] should be set to null
        g.removeVertex(pvd);  // the index of pvd is 5
        System.out.println("Here is the graph after removing \'pvd' of index 5:");
        graph.printMatrix();
        IEdge[][] correctMatrix4 = new IEdge[][]{
                {null, hnllax, null, null, null, null, qaqhnl, null},
                {hnllax, null, laxord, dfwlax, null, null, null, null},
                {null, laxord, null, null, null, null, null, null},
                {null, dfwlax, null, null, null, dfwmia, null, null},
                {null, null, null, null, null, lgamia, null, null},
                {null, null, null, dfwmia, lgamia, null, null, null},
                {qaqhnl, null, null, null, null, null, null, qaqwow},
                {null, null, null, null, null, null, qaqwow, null}
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix4));

        System.out.println("------------------------------test remove vertex------------------------------------------------");

        // test remove a vertex that has already been removed from the graph before
        g.removeVertex(pvd);
        // the printed graph should not change anything, as the vertex 'pvd' has already been removed before
        System.out.println("Here is the graph after removing \'pvd' of index 5: \n(nothing will change since \'pvd' has already been removed before)");
        graph.printMatrix();
        IEdge[][] correctMatrix5 = new IEdge[][]{
                {null, hnllax, null, null, null, null, qaqhnl, null},
                {hnllax, null, laxord, dfwlax, null, null, null, null},
                {null, laxord, null, null, null, null, null, null},
                {null, dfwlax, null, null, null, dfwmia, null, null},
                {null, null, null, null, null, lgamia, null, null},
                {null, null, null, dfwmia, lgamia, null, null, null},
                {qaqhnl, null, null, null, null, null, null, qaqwow},
                {null, null, null, null, null, null, qaqwow, null}
        };
        System.out.println("Is the matrix correct: " + graph.testMatrix(graph.getMatrix(), correctMatrix5));

        System.out.println("------------------------------test incident edges-----------------------------------------------");

        // test incident edges of a vertex that has already been removed from the graph
        IIterator inRemovedVertx = g.incidentEdges(pvd);
        graph.printIterator(inRemovedVertx);

        System.out.println("------------------------------test vertices-----------------------------------------------------");

        // test vertices in the vertex list
        // print names of all vertices
        String[] ver = new String[]{"NEWHNL", "LAX", "ORD", "DFW", "LGA", "MIA", "QAQ", "WOW"};
        boolean flag = true;
        int count = 0;
        System.out.print("All vertices in the vertex list: ");
        IIterator<IVertex<String>> it = g.vertices();
        while( it.hasNext() ) {
            // here I must cast also, since it.next() returns an Object
            IVertex<String> v = it.next();
            System.out.print( v.element() + " ");
            if (!v.element().equals(ver[count++]))
                flag = false;
        }
        System.out.println();
        if (flag)
            System.out.println("This graph is correctly implemented :)");
        else
            System.out.println("This graph is wronly implemented :)");
    }
}
