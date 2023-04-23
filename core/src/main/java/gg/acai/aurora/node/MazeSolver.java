package gg.acai.aurora.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * @author Clouke
 * @since 21.04.2023 05:42
 * © Aurora - All Rights Reserved
 */
public class MazeSolver {

  private final int[][] maze;
  private final int startRow;
  private final int startCol;
  private final int endRow;
  private final int endCol;
  private final int[][] solution;
  private final Stack<Cell> stack;

  public MazeSolver(int[][] maze, int startRow, int startCol, int endRow, int endCol) {
    this.maze = maze;
    this.startRow = startRow;
    this.startCol = startCol;
    this.endRow = endRow;
    this.endCol = endCol;
    this.solution = new int[maze.length][maze[0].length];
    this.stack = new Stack<>();
  }

  /**
   * Moves the player to the given row and column.
   *
   * @param row The row to move to.
   * @param col The column to move to.
   * @return 0 if the player cannot move to the given row and column, 1 if the player can move to the given row and column, 2 if the player has reached the end.
   */
  public int move(int row, int col) {
    if (maze[row][col] == 0) {
      return 0;
    }

    if (solution[row][col] == 1) {
      return 1;
    }

    solution[row][col] = 1;

    if (row == endRow && col == endCol) {
      printSolution();
      return 2;
    }

    if (col > 0) {
      int move = move(row, col - 1);
      if (move == 2) {
        return 2;
      }
    }

    if (row < maze.length - 1) {
      int move = move(row + 1, col);
      if (move == 2) {
        return 2;
      }
    }

    if (col < maze[0].length - 1) {
      int move = move(row, col + 1);
      if (move == 2) {
        return 2;
      }
    }

    if (row > 0) {
      int move = move(row - 1, col);
      if (move == 2) {
        return 2;
      }
    }

    solution[row][col] = 0;
    return 0;
  }

  public int solve() {
    Cell current = new Cell(startRow, startCol);
    stack.push(current);

    while (!stack.isEmpty()) {
      current = stack.pop();

      if (maze[current.row][current.col] == 0 || solution[current.row][current.col] == 1) {
        continue;
      }

      solution[current.row][current.col] = 1;

      if (current.row == endRow && current.col == endCol) {
        printSolution();
        return 1;
      }

      if (current.col > 0) {
        stack.push(new Cell(current.row, current.col - 1)); // go left
      }

      if (current.row < maze.length - 1) {
        stack.push(new Cell(current.row + 1, current.col)); // go down
      }

      if (current.col < maze[0].length - 1) {
        stack.push(new Cell(current.row, current.col + 1)); // go right
      }

      if (current.row > 0) {
        stack.push(new Cell(current.row - 1, current.col)); // go up
      }
    }

    System.out.println("No solution found.");
    return 0;
  }

  public boolean isSolved() {
    return solution[endRow][endCol] == 1;
  }

  private void printSolution() {
    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution[0].length; j++) {
        if (solution[i][j] == 1) {
          System.out.print("* ");
        } else {
          System.out.print(maze[i][j] + " ");
        }
      }
      System.out.println();
    }
  }

  private static class Cell {
    public int row, col;

    public Cell(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) {
    for (int i = 0; i < 200; i++) {
      System.out.println(" ");
      int[][] maze = new MazeGenerator(10, 10).generateMaze();
      MazeSolver solver = new MazeSolver(maze, 0, 0, 4, 4);
      int r = solver.solve();
      if (r == 0) {
        System.out.println();
      }
    }
  }

  public static class MazeGenerator {
    private final int rows;
    private final int cols;
    private final int[][] maze;

    public MazeGenerator(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      this.maze = new int[rows][cols];
    }

    public int[][] generateMaze() {
      Random rand = new Random();
      Stack<Cell> stack = new Stack<>();
      Cell current = new Cell(rand.nextInt(rows), rand.nextInt(cols));

      while (true) {
        maze[current.row][current.col] = 1;

        List<Cell> neighbors = getUnvisitedNeighbors(current);
        if (!neighbors.isEmpty()) {
          Cell randomNeighbor = neighbors.get(rand.nextInt(neighbors.size()));
          stack.push(current);
          removeWall(current, randomNeighbor);
          current = randomNeighbor;
        } else if (!stack.isEmpty()) {
          current = stack.pop();
        } else {
          break;
        }
      }

      return maze;
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell) {
      List<Cell> neighbors = new ArrayList<>();

      if (cell.row > 1 && maze[cell.row - 2][cell.col] == 0) {
        neighbors.add(new Cell(cell.row - 2, cell.col));
      }

      if (cell.row < rows - 2 && maze[cell.row + 2][cell.col] == 0) {
        neighbors.add(new Cell(cell.row + 2, cell.col));
      }

      if (cell.col > 1 && maze[cell.row][cell.col - 2] == 0) {
        neighbors.add(new Cell(cell.row, cell.col - 2));
      }

      if (cell.col < cols - 2 && maze[cell.row][cell.col + 2] == 0) {
        neighbors.add(new Cell(cell.row, cell.col + 2));
      }

      return neighbors;
    }

    private void removeWall(Cell current, Cell neighbor) {
      int rowDiff = current.row - neighbor.row;
      int colDiff = current.col - neighbor.col;

      switch (rowDiff) {
        case -2: maze[current.row + 1][current.col] = 1; break;
        case 2: maze[current.row - 1][current.col] = 1; break;
      }

      switch (colDiff) {
        case -2: maze[current.row][current.col + 1] = 1; break;
        case 2: maze[current.row][current.col - 1] = 1; break;
      }
    }
  }

}
