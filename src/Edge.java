public class Edge {

    private final Vertex source;
    private final Vertex destination;
    private final int weight;
    public Edge(Vertex source, Vertex destination) {
        this(source, destination, 1);
    }
    public Edge(Vertex source, Vertex destination, int weight) {
        this.source      = source;
        this.destination = destination;
        this.weight      = weight;
    }
    public Vertex getSource() {
        return source;
    }
    public Vertex getDestination() {
        return destination;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " ->(" + weight + ")-> " + destination;
    }
}