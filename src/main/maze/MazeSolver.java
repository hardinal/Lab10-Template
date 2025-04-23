package maze;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * MazeSolver is a backtracking version of MazeGame.
 * 
 * @author Mitch Parry
 * @author Willow Sapphire
 * @author Austin Hardin
 * @version 23APR25
 */
public class MazeSolver
{
    /**
     * The height of game maps.
     */
    private final static int HEIGHT = 99;

    /**
     * The width of game maps.
     */
    private final static int WIDTH = 99;

    /**
     * The game map, as a 2D array of booleans.
     * True indicates the spot is blocked.
     */
    private boolean[][] wall;

    /**
     * The game map, as a 2D array of booleans.
     * True indicates the spot has been visited.
     */
    private boolean[][] visited;
    /**
     * Constructor sets up the maps and the path list.
     * 
     * @param mazeFile name of the file containing the map.
     */
    public MazeSolver(String mazeFile)
    {
        loadMaze(mazeFile);
        visited = new boolean[HEIGHT][WIDTH];
    }

    /**
     * Loads the data from the maze file and creates the map
     * 2D array.
     *  
     * @param mazeFile the input maze file.
     */
    private void loadMaze(String mazeFile)
    {
        wall = new boolean[HEIGHT][WIDTH];
        Scanner mazeScanner;
        try
        {
            mazeScanner = new Scanner(new FileReader(mazeFile));
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    if (mazeScanner.next().equals("1"))
                    {
                        wall[i][j] = true;
                    }
                }
            }
            mazeScanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + mazeFile);
        }
    }

    /**
     * Prints the map.
     */
    public void printMap()
    {
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                if (wall[i][j])
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Calls the recursive search.
     *
     * @return a string of moves to reach the goal, null if no path.
     */
    public String findSolution()
    {
        return backtrack(0, 0, "");
    }

    /**
     * Checks if the cell bounds, !wall, and !visited.
     *
     * @param row current row
     * @param col current column
     * @return true if valid
     */
    private boolean isValid(int row, int col)
    {
        return row >= 0 && row < HEIGHT
                && col >= 0 && col < WIDTH
                && !wall[row][col]
                && !visited[row][col];
    }


    /**
     * Backtrack recursion excursion method to find a path.
     *
     * @param row   current row
     * @param col   current column
     * @param path  path built so far (e.g. "down right ...")
     * @return full path string if solution found; null otherwise
     */
    private String backtrack(int row, int col, String path)
    {
        if (row == HEIGHT - 1 && col == WIDTH -1)
        {
            return path;
        }

        if (!isValid(row, col))
        {
            return null;
        }

        visited[row][col] = true;

        String result;

        // GOING UP
        result = backtrack(row - 1, col, path + "up ");
        if (result != null) return result;

        // GET DOWN GET DOWN
        result = backtrack(row + 1, col, path + "down ");
        if (result != null) return result;

        // SLIIIIDE TO THE LEFT
        result = backtrack(row, col - 1, path + "left ");
        if (result != null) return result;

        // SLIIIIDE TO THE RIGHT
        result = backtrack(row, col + 1, path + "right ");
        if (result != null) return result;

        //LOST
        return null;
    }
}
