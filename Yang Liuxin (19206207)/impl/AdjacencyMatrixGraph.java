package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.IPosition;
import graph.core.IVertex;
import graph.util.DLinkedList;



public class AdjacencyMatrixGraph<V,E> implements IGraph<V,E> {
    /**
     * Inner class to represent a vertex in an adjacency matrix graph implementation
     */

    private class AdjacencyMatrixVertex implements IVertex<V> {
        // reference to a node in the vertex list
        IPosition<IVertex<V>> node;

        // element stored in this vertex
        V element;

        // store the index of the vertex
        int index;

        // store which AdjacencyMatrixGraph it belongs to
        AdjacencyMatrixGraph graph;

        public AdjacencyMatrixVertex(V element, int index, AdjacencyMatrixGraph graph) {
            this.element = element;
            this.index = index;
            this.graph = graph;
        }

        @Override
        public V element() {
            return element;
        }



        // It's useful to have a toString() method that can
        // return details about this object, so you can
        // print the object later and get useful information.
        // This one prints the element
        public String toString() {
            return element.toString();
        }
    }

    /**
     * Inner class to represent an edge in an edge list graph implementation.
     *
     */
    private class AdjacencyMatrixEdge implements IEdge<E> {
        // reference to a node in the edge list
        IPosition<IEdge<E>> node;

        // element stored in this edge
        E element;

        // the start and end vertices that this edge connects
        AdjacencyMatrixVertex start, end;

        // store which AdjacencyMatrixGraph it belongs to
        AdjacencyMatrixGraph graph;

        // constructor to set the three fields
        public AdjacencyMatrixEdge(AdjacencyMatrixVertex start, AdjacencyMatrixVertex end, E element, AdjacencyMatrixGraph graph) {
            this.start = start;
            this.end = end;
            this.element = element;
            this.graph = graph;
        }

        @Override
        public E element() {
            return element;
        }

        public String toString() {
            return element.toString();
        }
    }

    // vertex list
    private IList<IVertex<V>> vertices;

    // edge list
    private IList<IEdge<E>> edges;

    // graph matrix
    private IEdge<E>[][] matrix;

    // size of the graph
    private int size = 0;

    /**
     * Constructor
     */
    public AdjacencyMatrixGraph() {
        // create new (empty) lists of edges and vertices
        vertices = new DLinkedList<IVertex<V>>();
        edges = new DLinkedList<IEdge<E>>();
        matrix = new IEdge[size][size];
    }

    @Override
    public IVertex<V>[] endVertices(IEdge<E> e) {
        // need to cast Edge type to EdgeListEdge
        AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;

        // create new array of length 2 that will contain
        // the edge's end vertices
        @SuppressWarnings("unchecked")
        IVertex<V>[] endpoints = new IVertex[2];

        // fill array
        endpoints[0] = edge.start;
        endpoints[1] = edge.end;

        return endpoints;
    }

    @Override
    public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
        // find end points of Edge e
        IVertex<V>[] endpoints = endVertices(e);

        // return the end point that is not v
        if (endpoints[0].equals(v)) {
            return endpoints[1];
        } else if (endpoints[1].equals(v)) {
            return endpoints[0];
        }

        // Problem! e is not connected to v.
        throw new RuntimeException("Error: cannot find opposite vertex.");
    }

    @Override
    public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
        AdjacencyMatrixVertex vertexV = (AdjacencyMatrixVertex) v;
        AdjacencyMatrixVertex vertexW = (AdjacencyMatrixVertex) w;

        // find whether the matrix stores the reference to edge object for adjacent vertices
        // return not null for nonadjacent vertices
        if (vertexV.graph == this && vertexW.graph == this) {
            return matrix[vertexV.index][vertexW.index] != null;
        }

        // special case: v or w is not in the graph now
        else {
            System.out.println("Error: cannot find vertices.");
            return false;
        }
    }

    @Override
    public V replace(IVertex<V> v, V x) {
        AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;

        // if the vertex belongs to this graph
        if (vertex.graph == this){
            // store old element that we should return
            V temp = vertex.element;
            // do the replacement
            vertex.element = x;
            // return the old value
            return temp;
        }

        // special case: the vertex does not belong to this graph
        else {
            System.out.println("The vertex cannot be replaced because it does not belong to this graph any more!");
            return null;
        }
    }

    @Override
    public E replace(IEdge<E> e, E x) {
        AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;

        // if the edge belongs to this graph
        if (edge.graph == this) {
            E temp = edge.element;
            edge.element = x;
            return temp;
        }

        // special case: the edge does not belong to this graph
        else {
            System.out.println("The edge cannot be replaced since it does not belong to this graph any more!");
            return null;
        }
    }

    @Override
    public IVertex<V> insertVertex(V v) {
        // create new vertex
        AdjacencyMatrixVertex vertex = new AdjacencyMatrixVertex(v, size, this);

        // increase the matrix size by one
        size++;

        // resize the matrix
        matrix = growArr(size);

        // insert the vertex into the vertex list
        // (returns a reference to the new Node that was created)
        IPosition<IVertex<V>> node = vertices.insertLast(vertex);

        // this reference must be stored in the vertex,
        // to make it easier to remove the vertex later.
        vertex.node = node;

        // return the new vertex that was created
        return vertex;
    }

    @Override
    public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
        // create new edge object
        AdjacencyMatrixEdge edge = new AdjacencyMatrixEdge((AdjacencyMatrixVertex) v, (AdjacencyMatrixVertex) w, o, this);

        // insert into the edge list and store the reference to the node in the edge object
        IPosition<IEdge<E>> n = edges.insertLast(edge);

        // find indices of end vertices
        int startIndex = edge.start.index;
        int endIndex = edge.end.index;

        // store two references in the matrix
        matrix[startIndex][endIndex] = edge;
        matrix[endIndex][startIndex] = edge;

        // store the reference of the edge node
        edge.node = n;

        return edge;
    }

    @Override
    public V removeVertex(IVertex<V> v) {
        AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;

        // if the vertex stored in this graph, then it can be removed
        if (vertex.graph == this) {
            // remove incident edges
            IIterator incidentEdges = incidentEdges(v);
            while (incidentEdges.hasNext()) {
                AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) incidentEdges.next();
                // remove the edge from this graph
                removeEdge(edge);
            }

            // decrease the size of matrix by one for resizing
            size--;

            // remove the vertex from the vertex list
            vertices.remove(vertex.node);

            // resize the matrix by omitting the row and column of the index of v
            narrowArr(size, vertex.index);

            // decrease the index of vertices that come after the removed vertex
            decIndex(vertex.index);

            // mark the vertex as removed
            // by assigning the graph that it belongs to null
            // now we can know that this removed vertex is not belong to this graph any more
            vertex.graph = null;

            // return the element of the vertex that was removed
            return vertex.element;
        }

        // special case: if the graph does not contain this vertex
        System.out.println("This vertex cannot be removed because it does not belong to the graph any more!");
        return null;

    }

    @Override
    public E removeEdge(IEdge<E> e) {
        // remove edge from edge list
        AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;

        // if the edge belongs to this graph
        if (edge.graph == this) {
            edges.remove(edge.node);

            // update the matrix by setting the position stored e as null
            int startIndex = edge.start.index;
            int endIndex = edge.end.index;
            matrix[startIndex][endIndex] = null;
            matrix[endIndex][startIndex] = null;

            // the removed edge does not belong to this graph any more
            edge.graph = null;

            // return the element of the removed edge
            return edge.element;
        }

        // special case: this edge does not belong to the graph
        else{
            System.out.println("This edge cannot be removed because it does not belong to the graph any more!");
            return null;
        }
    }

    @Override
    public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
        // special case: the vertex does not belong to the graph now
        if (((AdjacencyMatrixVertex) v).graph == null) {
            System.out.println("This vertex does not have any incident edges because it does not belong to the graph any more!");
            return null;
        }

        else {
            // find all non-null edges from one row or column of the matrix in which stores v
            // add them to "list".
            // using iterator() method in List to get an iterator over these edges.
            IList<IEdge<E>> list = new DLinkedList<IEdge<E>>();
            int vIndex = ((AdjacencyMatrixVertex) v).index;
            // iterate one row
            for (int i = 0; i < size; i++) {
                if (matrix[vIndex][i] != null) {
                    list.insertLast(matrix[vIndex][i]);
                }
            }
            return list.iterator();
        }
    }

    @Override
    public IIterator<IVertex<V>> vertices() {
        return vertices.iterator();
    }

    @Override
    public IIterator<IEdge<E>> edges() {
        return edges.iterator();
    }

    // grow the matrix
    private IEdge<E>[][] growArr(int newSize){
        int oldSize = newSize - 1;
        IEdge<E>[][] newMatrix = new IEdge[newSize][newSize];
        // copy edges from the old matrix to the new matrix
        for (int i = 0; i < oldSize; i++){
            for (int j = 0; j < oldSize; j++){
                newMatrix[i][j] = matrix[i][j];
            }
        }
        // update the reference of the matrix
        matrix = newMatrix;
        return newMatrix;
    }

    // narrow the matrix
    private IEdge<E>[][] narrowArr(int newSize, int index){
        IEdge<E>[][] newMatrix = new IEdge[newSize][newSize];
        // copy edges, except what is in the same row or column as the removed edge, to the new matrix
        for (int i = 0; i < newSize; i++){
            for (int j = 0; j < newSize; j++){
                if (i >= index && j >= index)
                    newMatrix[i][j] = matrix[i + 1][j + 1];
                else if (i >= index)
                    newMatrix[i][j] = matrix[i + 1][j];
                else if (j >= index)
                    newMatrix[i][j] = matrix[i][j + 1];
                else
                    newMatrix[i][j] = matrix[i][j];
            }
        }
        // update the reference of the matrix
        matrix = newMatrix;
        return newMatrix;
    }

    // decrease the index from the index of the removed vertex on
    private void decIndex(int index){
        IIterator it = vertices.iterator();
        while (it.hasNext()){
            AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) it.next();
            if (vertex.index > index)
                vertex.index--;
        }
    }


    // print the matrix of the graph
    public void printMatrix(){
        System.out.print("        ");
        for (int i = 0; i < size; i++) {
            System.out.print(String.format("%-8s", i));
        }
        System.out.println();
        for (int i = 0; i < size; i++){
            System.out.print(String.format("%-8s", i));
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%-8s", matrix[i][j]));
            }
            System.out.println();
        }
    }

    // get the matrix
    public IEdge<E>[][] getMatrix(){
        return matrix;
    }


    // print the elements stored in the iIterator
    // return the number of positions in this iterator
    public int printIterator(IIterator iIterator){
        // track the number of positions in this iterator: num
        int num = 0;
        // if the graph is not empty
        if (iIterator != null) {
            while (iIterator.hasNext()) {
                IPosition ip = (IPosition) iIterator.next();
                System.out.print(ip.element() + " ");
                num++;
            }
        }
        return num;
    }

    // compare the values stored in the iIterator against what it is supposed to be
    // return a boolean value for testing
    // return true if they are equal, meaning that the method returning the IIterator type is correctly implemented
    // return false if they are not equal, meaning that the method returning the IIterator type is wrongly implemented
    public boolean testEqual(IIterator iIterator, IPosition[] correct){
        boolean flag = true;
        int index = 0;
        while (iIterator.hasNext()){
            IPosition ip= (IPosition) iIterator.next();
            if (ip.element() != (correct[index++].element())) {
                flag = false;
            }
        }
        return flag;
    }

    // test whether the element store is the same
    public boolean testEqualElement(IIterator iIterator, Object[] correct){
        boolean flag = true;
        int index = 0;
        while (iIterator.hasNext() && flag){
            IPosition ip= (IPosition) iIterator.next();
            if (!ip.element().equals(correct[index++])) {
                flag = false;
            }
        }
        return flag;
    }

    // print out the matrix of the Adjacency Matrix Graph
    // // compare the values stored in the matrix against what it is supposed to be
    // return true if the matrix is correctly implemented
    // return false if the matrix is wrongly implemented
    public boolean testMatrix(IEdge[][] matrix, IEdge[][] correct){
        boolean flag = true;
        for (int i = 0; i < matrix.length && flag; i++){
            for (int j = 0; j < matrix.length && flag; j++)
                if (matrix[i][j] != correct[i][j]){
                    System.out.println("false: "+correct[i][j]);
                    flag = false;
                }
        }
        return flag;
    }


    public AdjacencyMatrixVertex getVertex (V element){
        IIterator<IVertex<V>> vertices = this.vertices();
        while (vertices.hasNext()){
            AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) vertices.next();
            if (vertex.element().equals(element))
                return vertex;
        }
        return null;
    }

}
