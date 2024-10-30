import java.util.*;

public class Test {
	
    /**
     * Main method to execute the puzzle solver.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);
        int[][] initialState = new int[3][3];

        // Prompt user to input the initial state matrix row by row
        System.out.println("Enter the initial state matrix row by row, using space as a separator:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initialState[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        // Create a puzzle solver instance
        PuzzleSolver solver = new PuzzleSolver();

        // Run Uniform Cost Search (UCS)
        System.out.println("Running Uniform Cost Search (UCS):\n");
        solver.solve(initialState, false);

        // Run A* Search
        System.out.println("Running A* Search:\n");
        solver.solve(initialState, true);
    }
}