package gna;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public boolean equals(Object o) {
            Edge edge = (Edge) o;
            return source == edge.source && destination == edge.destination;
        }

        public int hashCode() {
            return Objects.hash(source, destination, weight);
        }
    }

    static class Node {
        int index;
        int costFromStart;

        public Node(int index, int costFromStart) {
            this.index = index;
            this.costFromStart = costFromStart;
        }
    }

    final int V;
    int E;
    int startingEnergy;
    LinkedList<Graph.Edge>[] adjacencylist;
    Node[] nodes;

    Graph(int V, int startingEnergy) {
        this.V = V;
        this.startingEnergy = startingEnergy;
        adjacencylist = new LinkedList[V];
        nodes = new Node[V];
        for (int i = 0; i < V; i++) {                       // complexiteit van 2|V| = 2NM
            adjacencylist[i] = new LinkedList<>();
            nodes[i] = new Node(i, 0);
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Graph.Edge edge = new Graph.Edge(source, destination, weight);
        adjacencylist[source].addFirst(edge); // for directed graph
        E++;
    }

    public static void getCrossingPoints(Graph G, int N, int M) {
        for (int i = M + 1; i < (N - 1) * M; i++) {
            if (!(i % M == 0)) {
                if (G.adjacencylist[i].size() == 2 && G.adjacencylist[i - 1].contains(new Edge(i, i - 1, 0))) {
                }
            }

        }
    }

    public static class BellmanFord {          // no negative cycles!
        private int[] distTo;
        private Graph.Edge[] edgeTo;
        private boolean[] onQueue;
        private Queue<Integer> queue;
        private int cost;
        public final int startingEnergy;

        public BellmanFord(Graph G, int source, int startingEnergy) {
            distTo = new int[G.V];
            edgeTo = new Graph.Edge[G.V];
            onQueue = new boolean[G.V];
            queue = new LinkedList<>();
            this.startingEnergy = startingEnergy;

            // initialize
            for (int v = 0; v < G.V; v++)
                distTo[v] = Integer.MAX_VALUE;
            distTo[source] = 0;
            queue.add(source);
            onQueue[source] = true;
            while (!queue.isEmpty()) {      // queue zal ooit leeg worden want geen negatieve kringen
                int v = queue.poll();
                onQueue[v] = false;
                relax(G, v);
            }
        }

        private void relax(Graph G, int v) {
            for (Graph.Edge e : G.adjacencylist[v]) {
                cost++;
                int w = e.destination;
                if (distTo[w] > distTo[v] + e.weight && G.nodes[v].costFromStart < startingEnergy) {
                    distTo[w] = distTo[v] + e.weight;
                    edgeTo[w] = e;
                    G.nodes[w].costFromStart = G.nodes[v].costFromStart + e.weight;
                    if (!onQueue[w]) {
                        queue.add(w);
                        onQueue[w] = true;
                    }
                }
            }
        }

        public int distTo(int v) {
            return distTo[v];
        }

        public int[] getDistTo() {
            return distTo;
        }

        public boolean hasPathTo(int v) {
            return distTo[v] < Integer.MAX_VALUE;
        }

        public Iterable<String> pathTo(int v) {
            if (!hasPathTo(v)) return null;
            Stack<String> path = new Stack<>();
            for (Graph.Edge e = edgeTo[v]; e != null; e = edgeTo[e.source])
                if (e.source + 1 == e.destination)
                    path.push("RIGHT");
                else
                    path.push("DOWN");
            return path;
        }

//        public List<String> pathTo(Graph G, int v) {
//            return G.nodes[G.V - 1].pathTo;
//        }

        public int getCost() {
            return this.cost;
        }

    }
}
