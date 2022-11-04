package graph.Test;

import graph.core.*;
import graph.impl.AdjacencyListGraph;
import graph.util.DLinkedList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is a file that reads from a file to create a
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
 * The graph read from the 'airport.txt' file store unique vertex elements
 */

public class AdjacencyListTest4 {
    public static void main(String[] args) {
        IGraph<String,Integer> g = new AdjacencyListGraph<>();
        DLinkedList<String[]> details = new DLinkedList<>();
        DLinkedList<String> vertices = new DLinkedList<>();

        // read a file called 'airport' to record details before constructing a graph
        try (BufferedReader br = new BufferedReader(new FileReader("airport"))){
            String line = br.readLine();
            while (line != null){
                String[] lines = line.split(" ");
                String[] curr = new String[]{lines[0], lines[1], lines[2]};
                details.insertLast(curr);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IIterator<String[]> itDetails = details.iterator();
        AdjacencyListGraph graph = (AdjacencyListGraph) g;
        while (itDetails.hasNext()){
            String[] tmp = itDetails.next();
            String startV = tmp[0];
            String endV = tmp[1];
            Integer edge = Integer.parseInt(tmp[2]);
            IVertex v1, v2;
            // insert start vertex
            if (!contains(vertices, startV) && !contains(vertices, startV)){
                v1 = g.insertVertex(startV);
                vertices.insertLast(startV);
            }
            // if the vertex is already inserted, record it to insert edge afterwards
            else {
                v1 = graph.getVertex(startV);
            }
            // insert end vertex
            if (!contains(vertices, endV) && !contains(vertices, endV)){
                v2 = g.insertVertex(endV);
                vertices.insertLast(endV);
            }
            // if the vertex is already inserted, record it to insert edge afterwards
            else {
                v2 = graph.getVertex(endV);
            }
            g.insertEdge(v1, v2, edge);
        }

        System.out.println("-------------------------------- ---test vertices------------------------------------------");

        // test vertices
        IIterator verticesIt = g.vertices();
        System.out.print("Vertices in this graph: ");
        graph.printIterator(verticesIt);
        String[] correctVer = new String[]{"HNL", "LAX", "SFO", "ORD", "DFW", "PVD", "LGA", "MIA"};
        verticesIt = g.vertices();
        System.out.println("\nVertices are correct: " + graph.testEqualElement(verticesIt, correctVer));

        System.out.println("------------------------------------test edges---------------------------------------------");

        // test edges
        IIterator edgesIt = g.edges();
        System.out.print("Edges in this graph: ");
        graph.printIterator(edgesIt);
        Integer[] correctEdge = new Integer[]{2555, 337, 1843, 1743, 1233, 849, 1387, 1120, 1099, 142, 802};
        edgesIt = g.edges();
        System.out.println("\nEdges are correct: " + graph.testEqualElement(edgesIt, correctEdge));
    }

    public static boolean contains (DLinkedList<String> list, String str){
        IIterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String curr = iterator.next();
            if (curr.equals(str))
                return true;
        }
        return false;
    }
}
