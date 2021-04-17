package algorithms.mazeGenerators;

/**
 * This interface represents a maze generator
 */
public interface IMazeGenerator {

    /**
     * Generates a rowXcol maze
     * @param row The number of rows in the maze
     * @param col The number of cols in the maze
     * @return The new Maze generated
     */
    Maze generate(int row,int col);
    /**
     * measures the time it takes to generate a rowXcol maze
     * @param row the number of maze rows
     * @param col the number of maze cols
     * @return the time it took to genereate in milliseconds
     * @throws Exception in case of illegel size of maze
     */
    long measureAlgorithmTimeMillis (int row,int col) throws Exception;

}
