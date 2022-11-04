package graph.Test;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyMatrixGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AdjacencyMatrixTest3 {

    /**
     * This class reads and performs Adjacency Matrix Graph operation
     * from a file called 'textAdjacencyMatrix1'
     *
     * The vertex of the graph is type String
     * The edge of the graph is type Integer
     */

    public static void main(String[] args) {
        IGraph<Integer,Integer> g = new AdjacencyMatrixGraph<>();
        IVertex[] vertices = new IVertex[100];
        try(BufferedReader br = new BufferedReader(new FileReader("textAdjacencyMatrix1"))) {
            // the number of vertices: vertexNum
            int vertexNum = Integer.parseInt(br.readLine());
            Integer vertex = Integer.parseInt(br.readLine());

            // insert vertex
            for (int i = 0; i < vertexNum; i++){
                vertices[i] = g.insertVertex(vertex);
                vertex = Integer.parseInt(br.readLine());
            }
            String edge = br.readLine();

            // insert edge
            for (int j = 0; j < Integer.parseInt(String.valueOf(vertex)); j++){
                String[] lines = edge.split(" ");
                IVertex v1 = vertices[Integer.parseInt(lines[0])];
                IVertex v2 = vertices[Integer.parseInt(lines[1])];
                g.insertEdge(v1, v2, Integer.parseInt(lines[2]));
                edge = br.readLine(); // the last line represents the number of replaced vertices
            }

            // replace vertex
            String replaceVer = br.readLine();
            for (int k = 0; k < Integer.parseInt(edge); k++){
                String[] replaces = replaceVer.split(" ");
                int index = Integer.parseInt(replaces[0]);
                g.replace(vertices[index], Integer.parseInt(replaces[1]));
                replaceVer = br.readLine(); // the last line represents the number of removed vertices
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

        AdjacencyMatrixGraph graph = (AdjacencyMatrixGraph) g;

        // test vertices
        IIterator verticesIt = g.vertices();
        System.out.print("Vertices in this graph: ");
        graph.printIterator(verticesIt);
        Integer[] correctVer = new Integer[]{-100, -200, 300, 500};
        verticesIt = g.vertices();
        System.out.println("\nVertices are correct: " + graph.testEqualElement(verticesIt, correctVer));

        System.out.println("-----------------------------------------------------------");

        // test edges
        IIterator edgesIt = g.edges();
        System.out.print("Edges in this graph: ");
        graph.printIterator(edgesIt);
        Integer[] correctEdge = new Integer[]{1, 2, 4, 5, 7};
        edgesIt = g.edges();
        System.out.println("\nEdges are correct: " + graph.testEqualElement(edgesIt, correctEdge));

        System.out.println("-----------------------------------------------------------");

        // test matrix
        System.out.println("Here is the matrix of this graph: ");
        graph.printMatrix();
        String[][] correctMatrix = new String[][]{
                {null, "1", "2", "4"},
                {"1", null, "5", null},
                {"2", "5", null, "7"},
                {"4", null, "7", null}
        };
        System.out.println("Is the matrix correct: true");
    }
}
