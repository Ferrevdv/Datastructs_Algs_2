package gna;

import java.util.Arrays;

public class Board {

    final String[][] tiles;
    final int energy;
    Graph.BellmanFord bellmanFord;

    private final int N;
    private final int M;

    /**
     * Constructor for an n-by-m, or rows-by-cols board.
     *
     * @param tiles  2D-matrix of tiles.
     * @param energy The starting energy of the runner.
     */
    public Board(String[][] tiles, int energy) {
        this.tiles = tiles;
        this.energy = energy;
        N = tiles.length;
        M = tiles[0].length;

        Graph graph = new Graph(N * M, energy);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i < N - 1 && j < M - 1) {
                    if (!(tiles[i + 1][j].equals("X")))
                        graph.addEdge(i * M + j, (i + 1) * M + j, getValue(tiles[i + 1][j]));
                    if (!(tiles[i][j + 1].equals("X")))
                        graph.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1]));
                } else if (i < N - 1 && j == M - 1) {
                    if (!(tiles[i + 1][j].equals("X")))
                        graph.addEdge(i * M + j, (i + 1) * M + j, getValue(tiles[i + 1][j]));
                } else if (i == N - 1 && j < M - 1) {
                    if (!(tiles[i][j + 1].equals("X")))
                        graph.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1]));
                }
            }
        }
        this.bellmanFord = new Graph.BellmanFord(graph, 0, energy);
    }

//    public Iterable<Graph.Edge> pathTo(int v) {
//        return bellmanFord.pathTo(v);
//    }

//    public String[] pathTo() {
//        String[] result = new String[graph.nodes[graph.V - 1].pathTo.size()];
//        graph.nodes[graph.V - 1].pathTo.toArray(result);
//        System.out.println(bellmanFord.getCost());
//        return result;
//
//    }

    public int getCost() {
        return bellmanFord.getCost();
    }

    /**
     * @return Number of columns
     */
    public int cols() {
        return M;
    }

    /**
     * @return Number of rows
     */
    public int rows() {
        return N;
    }

    private int getValue(String s) {
        switch (s) {
            case ".":
                return 1;
            case "E":
                return -1;
            case "M":
                return 2;
            case "H":
                return 3;
        }
        throw new IllegalArgumentException("String s cannot exist");
    }

    /**
     * Gets an array consisting of "RIGHT" and "DOWN" that describes the path from start (0, 0) to
     * finish (rows, cols) such that the energy at the finish is optimal, and is not equal to or lower than
     * zero anywhere along the path.
     *
     * @return Array consisting of "RIGHT" and "DOWN"
     */
    public String[] getPathSequence() {
        String[] result = new String[N + M - 2];
        int counter = N + M - 3;
        if (bellmanFord.pathTo(N * M - 1) == null)
            return new String[]{"EMPTY"};
        for (String s : bellmanFord.pathTo(N * M - 1))
            result[counter--] = s;
        return result;
    }


    /**
     * Returns the energy that the runner has when he reaches the finish line.
     *
     * @return The energy at the finish (rows, cols).
     */
    public int getFinishTileEnergy() {
        return energy - bellmanFord.distTo(N * M - 1);
    }

    /**
     * Is solvable if the amount of energy left in finish tile is higher than 0, and there
     * exists a path such that the energy along this path is strictly above zero as well.
     *
     * @return True if there is a valid path from start to finish, false otherwise.
     */
    public boolean isSolvable() {
        return !(bellmanFord.pathTo(N * M - 1) == null);
    }

    /**
     * Override equals and hashCode.
     */
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;
        if (this.N != ((Board) y).rows() || this.M != ((Board) y).cols() || this.energy != ((Board) y).energy)
            return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (this.tiles[i][j] != ((Board) y).tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.tiles) + Integer.hashCode(energy);
    }

    /**
     * Return a printable String representation of the board
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(256);
        for (int row = 0; row < this.rows(); row++) {
            for (int col = 0; col < this.cols(); col++) {
                str.append(tiles[row][col]);
                str.append(" ");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
