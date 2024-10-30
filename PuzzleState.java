import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PuzzleState {

    // The current state of the puzzle
    private int[][] state;

    // Position of the blank space
    private PuzzleState parent;

    // Position of the blank space
    private int zeroRow, zeroCol; 

    // Cost to reach this state
    private int cost; 

    // Number of moves made to reach this state
    private int depth; 

    /**
     * Constructor for creating a new puzzle state.
     * @param state The state of the puzzle.
     * @param zeroRow Row index of the blank space.
     * @param zeroCol Column index of the blank space.
     * @param parent Parent state.
     * @param cost Cost to reach this state.
     * @param depth Number of moves made to reach this state.
     */
    public PuzzleState(int[][] state, int zeroRow, int zeroCol, PuzzleState parent, int cost, int depth) {
        this.state = deepCopy(state);
        this.zeroRow = zeroRow;
        this.zeroCol = zeroCol;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
    }

    /**
     * Generates successors by moving the blank space.
     * @return List of successor states.
     */
    public List<PuzzleState> generateSuccessors() {
        List<PuzzleState> successors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] dir : directions) {
            int newRow = zeroRow + dir[0];
            int newCol = zeroCol + dir[1];
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newState = deepCopy(state);
                newState[zeroRow][zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                successors.add(new PuzzleState(newState, newRow, newCol, this, this.cost + 1, this.depth + 1));
            }
        }
        return successors;
    }

    /**
     * Utility method for deep copying the state array.
     * @param original The original state array.
     * @return Deep copy of the state array.
     */
    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    /**
     * Checks if the state is the goal state.
     * @param goalState The goal state of the puzzle.
     * @return True if the state is the goal state, false otherwise.
     */
    public boolean isGoal(int[][] goalState) {
        return Arrays.deepEquals(this.state, goalState);
    }

    /**
     * Retrieves the path from the start state to this state.
     * @return List of states representing the path.
     */
    public List<PuzzleState> getPathFromStart() {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState current = this;
        while (current != null) {
            // Add to the beginning
            path.add(0, current); 
            current = current.parent;
        }
        return path;
    }

    /**
     * Retrieves the cost to reach this state.
     * @return Cost to reach this state.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Retrieves the depth of this state.
     * @return Depth of this state.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Retrieves a deep copy of the state array.
     * @return Deep copy of the state array.
     */
    public int[][] getState() {
        return deepCopy(state);
    }

    /**
     * Heuristic function based on Misplaced Tiles.
     * @param goalState The goal state of the puzzle.
     * @return Number of misplaced tiles.
     */
    public int misplacedTilesHeuristic(int[][] goalState) {
        int misplacedTiles = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != 0 && state[i][j] != goalState[i][j]) {
                    misplacedTiles++;
                }
            }
        }
        return misplacedTiles;
    }

    /**
     * Overrides the equals method.
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleState other = (PuzzleState) obj;
        return Arrays.deepEquals(this.state, other.state);
    }

    /**
     * Overrides the hashCode method.
     * @return Hash code value for this state.
     */
    public int hashCode() {
        return Arrays.deepHashCode(this.state);
    }
}
