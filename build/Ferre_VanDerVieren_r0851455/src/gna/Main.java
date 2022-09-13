package gna;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Example given in practicum
        // | . . M . . . |
        // | H M . . X X |
        // | H . E . . E |
        // | E E X . . . |
        String[][] tiles = new String[][]{
                {".", "H", ".", ".", ".", "E", "."},
                {".", ".", ".", "M", ".", "H", "."},
                {".", "E", ".", ".", ".", "M", "."}};
        int energy = 10;
        Board board = new Board(tiles, energy);
        System.out.println(Arrays.toString(board.getPathSequence()));
        System.out.println(board.getFinishTileEnergy());
        System.out.println(board.getCost());


    }
}
