package gna;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The example JUnit test described in Practicum 3.
 * <p>
 * Make sure you write sufficient tests of your own as well.
 */
public class ExampleTests {

    @Test
    public void testPracticumExampleNotSufficientEnergy() {
        // Example given in practicum
        // | . . M . . . |
        // | H M . . X X |
        // | H . E . . E |
        // | E E X . . . |
        String[][] tiles = new String[][]{
                {".", ".", "M", ".", ".", "."},
                {"H", "M", ".", ".", "X", "X"},
                {"H", ".", "E", ".", "E", "E"},
                {"E", "E", "X", ".", ".", "."}};
        int energy = 4;
        Board board = new Board(tiles, energy);
        boolean isSolvable = board.isSolvable();
        /* Check if isSolveable  */
        String message = "Voor bord {{. . M . . .},{H M . . X X},{H . E . . E},{E E X . . .}} met begin-energie 6 zou je de finish niet mogen halen. Jij doet dat echter wel";
        assertFalse(message, isSolvable);
    }

    @Test
    public void testPracticumExampleSufficientEnergy() {
        // Example given in practicum
        // | . . M . . . |
        // | H M . . X X |
        // | H . E . E E |
        // | E E X . . . |
        String[][] tiles = new String[][]{
                {".", ".", "M", ".", ".", "."},
                {"H", "M", ".", ".", "X", "X"},
                {"H", ".", "E", ".", "E", "E"},
                {"E", "E", "X", ".", ".", "."}};
        int energy = 5;
        Board board = new Board(tiles, energy);
        int energyAtFinish = board.getFinishTileEnergy();
        /* Check if finish tile energy is 2 */
        String messageEnergy = "Voor bord {{. . M . . .},{H M . . X X},{H . E . . E},{E E X . . .}} met begin-energie 10 zou je nog 2 energie-punten over moeten hebben bij de finish. Bij jou is dat echter: " + energyAtFinish;
        assertTrue(messageEnergy, energyAtFinish == 2);
    }

    @Test
    public void testWalls() {
        // ?o possible path due to wall positioning
        // | . . . . |
        // | . . . . |
        // | . . X X |
        // | . . X . |
        String[][] tiles = new String[][]{
                {".", ".", ".", "."},
                {".", ".", ".", "."},
                {".", ".", "X", "X"},
                {".", ".", "X", "."}};
        int energy = 100;
        Board board = new Board(tiles, energy);
        boolean isSolvable = board.isSolvable();
        String message = "Bord {{. . . . }, {. . X X}, {. . X .}} zou geen oplossing mogen hebben door de omringende muren. Jouw isSolvable geeft echter true.";
        assertFalse(message, isSolvable);
    }

    @Test
    public void testNonSquareBoard() {
        // Check non-square board
        // | . H . . . E .|
        // | . X . M . H .|
        // | . E . . . M .|
        String[][] tiles = new String[][]{
                {".", "H", ".", ".", ".", "E", "."},
                {".", ".", ".", "M", ".", "H", "."},
                {".", "E", ".", ".", ".", "M", "."}};
        int energy = 10;
        try {
            Board board = new Board(tiles, energy);
            int energyAtFinish = board.getFinishTileEnergy();
            /* Check if energy left is 10 */
            String messageEnergy = "Voor bord {{. H . . . E .}, {. . . M . H .}, {. E . . . M .}} met begin-energie 10, verwachten we dat je nog 3 energie-punten over hebt, maar bij jouw is dat nog: " + energyAtFinish;
            assertTrue(messageEnergy, energyAtFinish == 3);
        } catch (Error e) {
            throw new AssertionError("Bij het oplossen van bord {{. H . . . E .}, {. . . M . H .}, {. E . . . M .}} krijgen we een error."); /* TODO: kijk zelf welke error */
        }
    }

    @Test
    public void testLargeBoardNeedsToPassEnergy() {
        // Needs to pass both energy tiles in order to reach the finish.
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . E . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . E . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . |
        // | . . . . . . . . . . . | (11x11)

        String[][] tiles = new String[][]{
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "E", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", "E", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."}};
        int energy = 17;
        Board board = new Board(tiles, energy);
        String[] path = board.getPathSequence();
        /* Check if they find a solution */
        String messageSolution = "Voor een 11x11 bord met slechts twee energie-tiles op positie 3x7 en 5x9, en met begin-energie 19, is oplosbaar (enkel indien je beide energietiles passeert). Jouw oplossing zegt echter dat dit niet oplosbaar is.";
        assertTrue(messageSolution, board.isSolvable());
        /* Check if only one energy is left at the finish */
        int energyAtFinish = board.getFinishTileEnergy();
        String messageEnergy = "Voor een 11x11 bord met slechts twee energie-tiles op positie 3x7 en 5x9, en met begin-energie 19, zou je nog maar 1 energiepunt over mogen hebben bij de finish. Jij hebt er echter nog:" + energyAtFinish;
        assertTrue(messageEnergy, energyAtFinish == 1);
        // assertTrue("messagePath", path.length == 20); // Dit moet ook waar zijn, maar nodig om hier nog eens te testen?
    }
}
