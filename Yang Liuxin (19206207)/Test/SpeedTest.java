package graph.Test;

import graph.core.IGraph;
import graph.impl.AdjacencyListGraph;
import graph.impl.AdjacencyMatrixGraph;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class to measure how quickly my AdjacencyListGraph and AdjacencyMatrixGraph implementation works.
 *
 * Strategy: - Insert a number of values (set in the 'range' variable) into the
 * AdjacencyListGraph and AdjacencyMatrixGraph, in random order. - Search for 'range' random variables.
 *
 *  The difference of time of inserting vertex between AdjacencyListGraph and AdjacencyMatrixGraph
 *  becomes larger when there are more newly inserted vertices.
 *
 *  Example 1:
 *  For value: 500
 *  The time of inserting vertex in AdjacencyListGraph is 1
 *  The time of inserting vertex in AdjacencyMatrixGraph is 128
 *
 *  Example 2:
 *  For value: 5000
 *  The time of inserting vertex in AdjacencyListGraph is 2
 *  The time of inserting vertex in AdjacencyMatrixGraph is 81134
 */

public class SpeedTest {
    public static void main( String[] args ) throws Exception {
        int range = 5000;

        System.out.println( "Values: " + range );

        // get a list of 'range' values.
        List<Integer> values = IntStream.range( 0, range ).boxed().collect( Collectors.toList() );

        // shuffle the list into random order
        Collections.shuffle( values );

        // create the AdjacencyListGraph
        IGraph<Integer,Integer> g = new AdjacencyListGraph<>();

        // record the time I started inserting at
        long start = System.currentTimeMillis();

        // insert all values into the graph
        for ( Integer v : values ) {
            g.insertVertex( v );
        }

        // record the time I stopped inserting at
        long end = System.currentTimeMillis();

        // output the time for inserting
        System.out.println( "Time of insertVertex in AdjacencyListGraph: " + ( end - start ) );

        // create the AdjacencyMatrixGraph
        IGraph<Integer,Integer> g1 = new AdjacencyMatrixGraph<>();

        // record the time I started inserting at
        long start1 = System.currentTimeMillis();

        // insert all values into the graph
        for ( Integer v : values ) {
            g1.insertVertex( v );
        }

        // record the time I stopped inserting at
        long end1 = System.currentTimeMillis();

        // output the time for inserting
        System.out.println( "Time of insertVertex in AdjacencyMatrixGraph: " + ( end1 - start1 ) );

        System.out.println("\nThe difference of time of inserting vertex between AdjacencyListGraph and AdjacencyMatrixGraph becomes larger " +
                "when there are more newly inserted vertices.");
    }
}
