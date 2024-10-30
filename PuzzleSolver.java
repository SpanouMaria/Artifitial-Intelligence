import java.util.*;

class PuzzleSolver {
    
    // The goal state of the puzzle
    private int[][] goalState = {{6, 5, 4}, {7, 0, 3}, {8, 1, 2}};

    /**
     * Solves the puzzle.
     * @param initialState The initial state of the puzzle.
     * @param useAStar Flag indicating whether to use A* algorithm.
     */
    public void solve(int[][] initialState, boolean useAStar) {
        
        // Check if the initial state is valid
        if (!isValidState(initialState)) {
            System.err.println("Invalid initial state provided.");
            return;
        }
        
        // Priority queue for frontier states
        PriorityQueue<PuzzleState> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> (useAStar ? s.getCost() + s.misplacedTilesHeuristic(goalState) : s.getCost())));
        Set<PuzzleState> explored = new HashSet<>();
        Set<PuzzleState> inFrontier = new HashSet<>(); // This set will mirror the frontier for quick lookup
        
        // Create root state and add to frontier
        PuzzleState root = new PuzzleState(initialState, findZeroRow(initialState), findZeroCol(initialState), null, 0, 0);
        frontier.add(root);
        inFrontier.add(root);

        // Main search loop
        while (!frontier.isEmpty()) {
            PuzzleState currentState = frontier.poll();
            inFrontier.remove(currentState);
            if (currentState.isGoal(goalState)) {
                printSolution(currentState);
                return;
            }
            explored.add(currentState);
            for (PuzzleState successor : currentState.generateSuccessors()) {
                if (!explored.contains(successor) && !inFrontier.contains(successor)) {
                    frontier.add(successor);
                    inFrontier.add(successor);
                }
            }
        }

        // If no solution is found
        try {
            while (!frontier.isEmpty()) {
                // Existing loop logic...
            }
            throw new NoSuchElementException("No solution found.");
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Checks if the given state is valid.
     * @param state The state to be validated.
     * @return True if the state is valid, false otherwise.
     */
    private boolean isValidState(int[][] state) {
        Set<Integer> tiles = new HashSet<>();
        for (int[] row : state) {
            for (int num : row) {
                tiles.add(num);
            }
        }

        // Check if all tiles from 0 to 8 are present
        for (int i = 0; i <= 8; i++) {
            if (!tiles.contains(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the solution path.
     * @param state The final state of the solution.
     */
    private void printSolution(PuzzleState state) {
        List<PuzzleState> path = state.getPathFromStart();
        System.out.println("Path to solution:");
        for (PuzzleState s : path) {
            // Use getter method to access state safely
            int[][] stateArray = s.getState(); 
            for (int[] row : stateArray) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println();
        }
        System.out.println("Cost: " + state.getCost());

        // Correct access to depth using getter
        System.out.println("Depth: " + state.getDepth()); 
    }

    /**
     * Finds the row index of the zero tile.
     * @param state The puzzle state.
     * @return The row index of the zero tile.
     */
    private int findZeroRow(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    return i;
                }
            }
        }

        // Not found
        return -1; 
    }

    /**
     * Finds the column index of the zero tile.
     * @param state The puzzle state.
     * @return The column index of the zero tile.
     */
    private int findZeroCol(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    return j;
                }
            }
        }
        
        // Not found
        return -1;
    }
}