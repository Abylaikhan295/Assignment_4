public class Edge {

    private final Vertex source;
    private final Vertex destination;

    public Edge(Vertex source, Vertex destination) {
        this.source      = source;
        this.destination = destination;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return source + " → " + destination;
    }
}