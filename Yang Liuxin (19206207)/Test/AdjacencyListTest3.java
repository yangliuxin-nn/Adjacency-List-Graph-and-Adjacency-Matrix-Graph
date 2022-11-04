package graph.Test;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyListGraph;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AdjacencyListTest3 {

    /**
     * This class reads and performs Adjacency List Graph operation from a file
     * The vertex of the graph is type String
     * The edge of the graph is type Integer
     */

    public static void main(String[] args) {
        IGraph<Integer,Integer> g = new AdjacencyListGraph<>();
        IVertex[] vertices = new IVertex[100];


        try(BufferedReader br = new BufferedReader(new FileReader("textAdjacencyList1"))) {
            // the number of vertices to be inserted: vertexNum
            int vertexNum = Integer.parseInt(br.readLine());
            Integer vertex = Integer.parseInt(br.readLine());

            // insert vertex
            for (int i = 0; i < vertexNum; i++){
                vertices[i] = g.insertVertex(vertex);
                vertex = Integer.parseInt(br.readLine());
            }
            String edge = br.readLine(); // the number of edges to be inserted

            // insert edge
            for (int j = 0; j < Integer.parseInt(String.valueOf(vertex)); j++){
                String[] lines = edge.split(" ");
                IVertex<Integer> v1 = vertices[Integer.parseInt(lines[0])];
                IVertex<Integer> v2 = vertices[Integer.parseInt(lines[1])];
                g.insertEdge(v1, v2, Integer.parseInt(lines[2]));
                edge = br.readLine(); // the last line represents the number of vertices to be replaced
            }

            // replace vertex
            String replaceVer = br.readLine();
            for (int k = 0; k < Integer.parseInt(edge); k++){
                String[] replaces = replaceVer.split(" ");
                int index = Integer.parseInt(replaces[0]);
                g.replace(vertices[index], Integer.parseInt(replaces[1]));
                replaceVer = br.readLine(); // the last line represents the number of vertices to be removed
            }

            // remove vertex
            String removeVer = br.readLine();
            for (int m = 0; m < Integer.parseInt(replaceVer); m++){
                int index = Integer.parseInt(removeVer);
                g.removeVertex(vertices[index]);
                removeVer = br.readLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdjacencyListGraph graph = (AdjacencyListGraph) g;

        // test vertices
        IIterator verticesIt = g.vertices();
        System.out.print("Vertices in this graph: ");
        graph.printIterator(verticesIt);
        IVertex[] correctVer = new IVertex[]{vertices[1], vertices[2], vertices[3], vertices[4], vertices[5]};
        verticesIt = g.vertices();
        System.out.println("\nVertices are correct: " + graph.testEqual(verticesIt, correctVer));

        System.out.println("------------------------------------------------------------------------------");

        // test edges
        IIterator edgesIt = g.edges();
        System.out.print("Edges in this graph: ");
        graph.printIterator(edgesIt);
        Integer[] correctEdge = new Integer[]{5, 6, 7, 8};
        edgesIt = g.edges();
        System.out.println("\nEdges are correct: " + graph.testEqualElement(edgesIt, correctEdge));

        System.out.println("------------------------------------------------------------------------------");

        // test incident sequence
        IIterator i1 = graph.incidentEdges(vertices[1]);
        System.out.print("Incidence sequence of vertex 1: ");
        graph.printIterator(i1);
        i1 = graph.incidentEdges(vertices[1]);
        Integer[] correctI1 = new Integer[]{5, 8};
        System.out.println("Is this incidence sequence correct: " + graph.testEqualElement(i1, correctI1));

        IIterator i2 = graph.incidentEdges(vertices[2]);
        System.out.print("Incidence sequence of vertex 2: ");
        graph.printIterator(i2);
        i2 = graph.incidentEdges(vertices[2]);
        Integer[] correctI2 = new Integer[]{5, 6, 7};
        System.out.println("Is this incidence sequence correct: " + graph.testEqualElement(i2, correctI2));

        IIterator i3 = graph.incidentEdges(vertices[3]);
        System.out.print("Incidence sequence of vertex 3: ");
        graph.printIterator(i3);
        i3 = graph.incidentEdges(vertices[3]);
        Integer[] correctI3 = new Integer[]{6};
        System.out.println("Is this incidence sequence correct: " + graph.testEqualElement(i3, correctI3));

        IIterator i4 = graph.incidentEdges(vertices[4]);
        System.out.print("Incidence sequence of vertex 4: ");
        graph.printIterator(i4);
        i4 = graph.incidentEdges(vertices[4]);
        Integer[] correctI4 = new Integer[]{7, 8};
        System.out.println("Is this incidence sequence correct: " + graph.testEqualElement(i4, correctI4));

        IIterator i5 = graph.incidentEdges(vertices[5]);
        System.out.print("Incidence sequence of vertex 5: ");
        graph.printIterator(i5);
        i5 = graph.incidentEdges(vertices[5]);
        Integer[] correctI5 = new Integer[]{};
        System.out.println("Is this incidence sequence correct: " + graph.testEqualElement(i5, correctI5));


    }
}
