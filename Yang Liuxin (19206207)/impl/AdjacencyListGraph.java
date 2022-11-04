package graph.impl;


import graph.core.*;
import graph.util.DLinkedList;


public class AdjacencyListGraph<V,E> implements IGraph<V,E> {
    /**
     * Inner class to represent a vertex in an adjacency list graph implementation
     */
    private class AdjacencyListVertex implements IVertex<V> {
        // reference to a node in the vertex list
        IPosition<IVertex<V>> node;

        // incidence sequence for each vertex
        DLinkedList<IEdge<E>> incidentEdges;

        // element stored in this vertex
        V element;

        // store which AdjacencyMatrixGraph it belongs to
        AdjacencyListGraph graph;

        public AdjacencyListVertex(V element, AdjacencyListGraph graph) {
            this.element = element;
            this.graph = graph;
            this.incidentEdges = new DLinkedList<>();
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
     * Inner class to represent an edge in an adjacency list graph implementation.
     *
     */
    private class AdjacencyListEdge implements IEdge<E> {
        // reference to a node in the edge list
        IPosition<IEdge<E>> node;

        // element stored in this edge
        E element;

        // the start and end vertices that this edge connects
        AdjacencyListVertex startV, endV;

        // edge positions in the incident edges of its end points
        IPosition<IEdge<E>> startE, endE;

        // store which AdjacencyMatrixGraph it belongs to
        AdjacencyListGraph graph;

        // constructor to set the three fields
        public AdjacencyListEdge(AdjacencyListVertex startV, AdjacencyListVertex endV, E element, AdjacencyListGraph graph) {
            this.startV = startV;
            this.endV = endV;
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

    /**
     * Constructor
     */
    public AdjacencyListGraph() {
        // create new (empty) lists of edges and vertices
        vertices = new DLinkedList<IVertex<V>>();
        edges = new DLinkedList<IEdge<E>>();
    }


    @Override
    public IVertex<V>[] endVertices(IEdge<E> e) {
        // need to cast Edge type to AdjacencyListEdge
        AdjacencyListEdge edge = (AdjacencyListEdge) e;

        // create new array of length 2 that will contain
        // the edge's end vertices
        @SuppressWarnings("unchecked")
        IVertex<V>[] endpoints = new IVertex[2];

        // fill array
        endpoints[0] = edge.startV;
        endpoints[1] = edge.endV;

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
        AdjacencyListVertex vertexV = (AdjacencyListVertex) v;
        AdjacencyListVertex vertexW = (AdjacencyListVertex) w;

        // if v and w belongs to this graph
        if (vertexV.graph == this && vertexW.graph == this) {
            // degree of vertex v
            int degV = vertexV.incidentEdges.size();
            // degree of vertex w
            int degW = vertexW.incidentEdges.size();

            // iterate through all the edges in the smaller incident edges
            IIterator<IEdge<E>> it = degV < degW ? incidentEdges(v) : incidentEdges(w);

            while (it.hasNext()) {
                // must cast Object type to AdjacencyListEdge type
                AdjacencyListEdge edge = (AdjacencyListEdge) it.next();
                // edge connects v -> w (so they are adjacent)
                if (edge.startV.equals(v) && edge.endV.equals(w))
                    return true;
                // edge connects w -> v (so they are adjacent)
                if (edge.endV.equals(v) && edge.startV.equals(w))
                    return true;
            }
        }
        // no edge was found that connects v to w.
        return false;
    }

    @Override
    public V replace(IVertex<V> v, V x) {
        AdjacencyListVertex vertex = (AdjacencyListVertex) v;
        // store old element that we should return
        V temp = vertex.element;

        // do the replacement
        vertex.element = x;

        // return the old value
        return temp;
    }

    @Override
    public E replace(IEdge<E> e, E x) {
        AdjacencyListEdge edge = (AdjacencyListEdge) e;
        E temp = edge.element;
        edge.element = x;
        return temp;
    }

    @Override
    public IVertex<V> insertVertex(V v) {
        // create new vertex
        AdjacencyListVertex vertex = new AdjacencyListVertex(v, this);

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
        AdjacencyListEdge edge = new AdjacencyListEdge((AdjacencyListVertex) v, (AdjacencyListVertex) w, o,this);

        // insert into the edge list and store the reference to the node
        // in the edge object
        IPosition<IEdge<E>> n = edges.insertLast(edge);
        edge.node = n;

        // insert into the incidentEdges of both vertices
        // store the references
        edge.startE = ((AdjacencyListVertex) v).incidentEdges.insertLast(edge);
        edge.endE =((AdjacencyListVertex) w).incidentEdges.insertLast(edge);

        return edge;
    }

    @Override
    public V removeVertex(IVertex<V> v) {

        AdjacencyListVertex vertex = (AdjacencyListVertex) v;

        // if the vertex belongs to this graph
        if (vertex.graph == this) {

            // first find all incident edges of v
            IIterator vIncidentEdgesIt = incidentEdges(v);

            // remove those edges from the incident edges lists of its opposite vertices
            while (vIncidentEdgesIt.hasNext()) {
                AdjacencyListEdge edge = (AdjacencyListEdge) vIncidentEdgesIt.next();
                // remove the edge from this graph
                removeEdge(edge);
            }

            // now we can remove the vertex from the vertex list
            vertices.remove(vertex.node);

            // this vertex does not belong to this graph any more
            vertex.graph = null;

            // return the element of the vertex that was removed
            return vertex.element;
        }

        // special case: the vertex does not point to this graph
        else {
            System.out.println("This vertex cannot be removed because it does not belong to the graph any more!");
            return null;
        }
    }

    // remove edge from edge list and return its element
    @Override
    public E removeEdge(IEdge<E> e) {

        AdjacencyListEdge edge = (AdjacencyListEdge) e;

        // if the edge belongs to this graph
        if (edge.graph == this) {

            // the edge does not point to this graph any more
            edge.graph = null;

            edges.remove(edge.node);

            // start vertex on end edge
            AdjacencyListVertex startVertex = edge.startV;

            // end vertex on another end edge
            AdjacencyListVertex endVertex = edge.endV;

            // remove the edge from the incident edges of startVertex
            startVertex.incidentEdges.remove(edge.startE);

            // remove the edge from the incident edges of endVertex
            endVertex.incidentEdges.remove(edge.endE);

            return edge.element;
        }

        // special case: this edge has already been removed from the graph
        else {
            System.out.println("This edge cannot be removed because it does not belong to the graph any more!");
            return null;
        }
    }

    @Override
    public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
        AdjacencyListVertex vertex = (AdjacencyListVertex) v;

        // if the vertex belongs to this graph
        if (vertex.graph == this) {
            // get the incident edges of v
            IList<IEdge<E>> list = vertex.incidentEdges;

            // return an iterator over these edges.
            return list.iterator();
        }
        // special case: the vertex does not belong to this graph
        else{
            System.out.println("The vertex has no incident edges since it does not belong to this graph any more!");
            return null;
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

    // print the elements stored in the iIterator
    // return the number of positions in this iterator
    public int printIterator(IIterator iIterator){
        int num = 0;
        while (iIterator.hasNext()){
            IPosition ip = (IPosition) iIterator.next();
            System.out.print(ip.element() + " ");
            num++;
        }
        return num;
    }


    // compare the values stored in the iIterator against what it is supposed to be
    // return a boolean value for testing
    // return true if the method returning the IIterator type is correctly implemented
    // return false if the method returning the IIterator type is wrongly implemented
    public boolean testEqual(IIterator iIterator, IPosition[] correct){
        boolean flag = true;
        int index = 0;
        while (iIterator.hasNext()){
            IPosition ip= (IPosition) iIterator.next();
            if (ip != correct[index++]) {
                flag = false;
            }
        }
        return flag;
    }

    // test whether the Integer element stored is the same
    public boolean testEqualElement(IIterator iIterator, Object[] correct){
        boolean flag = true;
        int index = 0;
        while (iIterator.hasNext()){
            IPosition ip= (IPosition) iIterator.next();
            if (!ip.element().equals(correct[index++])) {
                flag = false;
            }
        }
        return flag;
    }

    public AdjacencyListVertex getVertex (V element){
        IIterator<IVertex<V>> vertices = this.vertices();
        while (vertices.hasNext()){
            AdjacencyListVertex vertex = (AdjacencyListVertex) vertices.next();
            if (vertex.element().equals(element))
                return vertex;
        }
        return null;
    }


}
