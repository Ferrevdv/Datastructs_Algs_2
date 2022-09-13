package gna;

import java.util.ArrayList;
import java.util.List;

public class Board2P {

    private String[][] tiles;
    private int energyP1;
    private int energyP2;

    Graph.BellmanFord bellmanFord1;
    Graph.BellmanFord bellmanFord2;

    private final int N;
    private final int M;
    private int finishTileEnergyP1;
    private int finishTileEnergyP2;


    /**
     * Constructor for an n-by-m or rows-by-cols board.
     *
     * @param tiles    2D-matrix of tiles.
     * @param energyP1 The starting energy of runner 1 starting in (0, 0).
     * @param energyP2 The starting energy of runner 2 starting in (rows, 0).
     */
    public Board2P(String[][] tiles, int energyP1, int energyP2) {
        this.tiles = tiles;
        this.energyP1 = energyP1;
        this.energyP2 = energyP2;
        N = tiles.length;
        M = tiles[0].length;

        Graph graphP1 = new Graph(N * M, energyP1);
        Graph graphP2 = new Graph(N * M, energyP2);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i < N - 1 && j < M - 1) {
                    if (!(tiles[i + 1][j].equals("X")))
                        graphP1.addEdge(i * M + j, (i + 1) * M + j, getValue(tiles[i + 1][j]));
                    if (!(tiles[i][j + 1].equals("X")))
                        graphP1.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1]));
                } else if (i < N - 1 && j == M - 1) {
                    if (!(tiles[i + 1][j].equals("X")))
                        graphP1.addEdge(i * M + j, (i + 1) * M + j, getValue(tiles[i + 1][j]));
                } else if (i == N - 1 && j < M - 1) {
                    if (!(tiles[i][j + 1].equals("X")))
                        graphP1.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1]));
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i > 0 && j < M - 1) {
                    if (!(tiles[i - 1][j].equals("X")))
                        graphP2.addEdge(i * M + j, (i - 1) * M + j, getValue(tiles[i - 1][j])); // up
                    if (!(tiles[i][j + 1].equals("X")))
                        graphP2.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1])); // right
                } else if (i == 0 && j < M - 1) {
                    if (!(tiles[i][j + 1].equals("X")))
                        graphP2.addEdge(i * M + j, i * M + (j + 1), getValue(tiles[i][j + 1])); // right
                } else if (i > 0 && j == M - 1) {
                    if (!(tiles[i - 1][j].equals("X")))
                        graphP2.addEdge(i * M + j, (i - 1) * M + j, getValue(tiles[i - 1][j])); // up
                }
            }
        }
        this.bellmanFord1 = new Graph.BellmanFord(graphP1, 0, energyP1);
        this.bellmanFord2 = new Graph.BellmanFord(graphP2, 0, energyP2);
    }

    private List<Integer> getCrossingPoints() {
        List<Integer> indexOfCrossingPoints = new ArrayList<>();
        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < M - 1; j++) {
                if (!(tiles[i - 1][j].equals("X")) && !(tiles[i + 1][j].equals("X")) && !(tiles[i][j - 1].equals("X")) && !(tiles[i][j + 1].equals("X")))
                    indexOfCrossingPoints.add(i * M + j);
            }
        }
        return indexOfCrossingPoints;
    }

    private void calculateCrossingPoint(int crossPoint, Graph.BellmanFord B1, Graph.BellmanFord B2, Graph G1, Graph G2) {
        int energyP1_horizontal = 0;
        int energyP2_vertical = 0;

        int energyP1_vertical = 0;
        int energyP2_horizontal = 0;

        energyP1_horizontal += B1.distTo(crossPoint - 1);

    }

    public int cols() {
        return M;
    }

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
     * Gets an array consisting of "RIGHT" and "DOWN" that describes the path of runner 1 from start (0, 0) to
     * finish (rows, cols) such that the energy at the finish is optimal, and is not equal to or lower than
     * zero anywhere along the path, and crosses the path of runner 2 exactly once.
     *
     * @return Array consisting of "RIGHT" and "DOWN"
     */
    public String[] getPathSequenceP1() {
        // TODO: Implement
        return null;
    }

    /**
     * Gets an array consisting of "RIGHT" and "UP" that describes the path of runner 2 from start (rows, 0) to
     * finish (0, cols) such that the energy at the finish is optimal, and is not equal to or lower than
     * zero anywhere along the path, and crosses the path of runner 1 exactly once.
     *
     * @return Array consisting of "RIGHT" and "UP"
     */
    public String[] getPathSequenceP2() {
        // TODO: Implement
        return null;
    }

    /**
     * Returns the energy that the runner 1 has when he reaches the finish line (rows, cols).
     *
     * @return The energy at the finish (rows, cols).
     */
    public int getFinishTileEnergyP1() {
        // TODO: Implement
        return 0;
    }

    /**
     * Returns the energy that the runner 2 has when he reaches the finish line (0, cols).
     *
     * @return The energy at the finish (0, cols).
     */
    public int getFinishTileEnergyP2() {
        // TODO: Implement
        return 0;
    }

    /**
     * Is solvable if the amount of energy left of both runners is higher than 0, and there
     * exists a path for both runners such that the energy along these paths is strictly
     * above zero as well and they cross each other exactly once.
     *
     * @return True if there is a valid path for both runners from start to finish, false otherwise.
     */
    public boolean isSolvable() {
        // TODO: Implement
        return false;
    }

    /**
     * Override equals and hashCode.
     */
    @Override
    public boolean equals(Object y) {
        // TODO: Implement
        return false;

    }

    @Override
    public int hashCode() {
        // TODO: Implement
        return 0;
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
