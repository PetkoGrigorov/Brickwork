import java.util.*;

public class Application {

    private static int n = 0;
    private static int m = 0;
    /**
     * Input from console.
     */
    private static int[][] firstLayer;
    /**
     * Build by app.
     */
    private static int[][] secondLayer;
    /**
     * Creates list of ids of bricks put on matrix, used to control ids when build second layer with brick's id.
     */
    private static List<Integer> bricksIdList;
    private static final Scanner cin = new Scanner(System.in);


    public static void main(String[] args) {

        inputMatrixDimensions();

        firstLayer = new int[n][m];
        secondLayer = new int[n][m];
        bricksIdList = new ArrayList<>();
        int numberOfBricks = m * n / 2;
        for (int i = 0; i < numberOfBricks; i++) {
            bricksIdList.add(i + 1);
        }

        inputMatrix();
        System.out.println();

//        printMatrix(firstLayer);
//        System.out.println();

        List<Brick> brickList = getBricksFromMatrix(firstLayer);

        if (brickList == null) {
            System.out.println("invalid input of brick layer");
            return;
        }

        if (isSolutionExists(0) && getBricksFromMatrix(secondLayer) != null) {
            printMatrix(secondLayer);
        } else {
            System.out.println(-1 + " No solution exists");
        }
    }

    /**
     * Shows if brick can be placed horizontal from position <code>row</code> & <code>col</code>.
     * @param row row of matrix.
     * @param col row of matrix.
     * @return true or false.
     */
    private static boolean isHorizontalValid(int row, int col) {
        return (col < m - 1) && (firstLayer[row][col] != firstLayer[row][col + 1]) && (secondLayer[row][col + 1] == 0) && (secondLayer[row][col] == 0);
    }

    private static boolean isVerticalValid(int row, int col) {
        return (row < n - 1) && (firstLayer[row][col] != firstLayer[row + 1][col]) && (secondLayer[row + 1][col] == 0) && (secondLayer[row][col] == 0);
    }

    /**
     * Puts bricks on second layer if is possible.
     * @param row
     * @return
     */
    private static boolean isSolutionExists(int row) {
        for (int i = row; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (secondLayer[i][j] != 0) {
                    continue;
                }
                if (isHorizontalValid(i, j)) {
                    secondLayer[i][j] = secondLayer[i][j + 1] = bricksIdList.get(0);
                    bricksIdList.remove(0);
                    if (!isSolutionExists(i)) {
                        bricksIdList.add(0, secondLayer[i][j]);
                        secondLayer[i][j] = secondLayer[i][j + 1] = 0;
                    } else {
                        return true;
                    }
                }
                if (isVerticalValid(i, j)) {
                    secondLayer[i][j] = secondLayer[i + 1][j] = bricksIdList.get(0);
                    bricksIdList.remove(0);
                    if (!isSolutionExists(i)) {
                        bricksIdList.add(0, secondLayer[i][j]);
                        secondLayer[i][j] = secondLayer[i + 1][j] = 0;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Prints matrix <code>board</code> on console.
     * @param board
     */
    private static void printMatrix(int[][] board) {
        for (int[] ints : board) {
            for (int anInt : ints) {
                String string = String.valueOf(anInt);
                int count = string.length();
                int a = 3 - count;
                String separator = " ";
                for (int i = 0; i < a; i++) {
                    separator += " ";
                }
                System.out.print(separator + anInt);
            }
            System.out.println();
        }
    }

    /**
     * Builds matrix from console input.
     */
    private static void inputMatrix() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                firstLayer[row][col] = cin.nextInt();
            }
        }
    }


    /**
     * Creates a list of <code>Brick</code> got from matrix <code>board</code>,
     * if brick's size is correct.
     * @param board matrix of bricks.
     * @return list of <code>Brick</code> if all bricks are correct, else <code>null</code>.
     */
    private static List<Brick> getBricksFromMatrix(int[][] board) {
        List<Brick> brickList = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int id = board[row][col];
                Position newPosition = new Position(row, col);
                if (brickList.isEmpty()) {
                    brickList.add(new Brick(id, newPosition));
                    continue;
                }
                boolean isBrickCompleted = false;
                for (Brick element : brickList) {
                    if (element.getId() == id) {
                        if (element.getPositionB() == null) {
                            element.setPositionB(newPosition);
                            isBrickCompleted = true;
                        } else {
                            return null;
                        }
                    }
                }
                if (!isBrickCompleted) {
                    brickList.add(new Brick(id, newPosition));
                }
            }
        }
        for (Brick brick : brickList) {
            Position positionA = brick.getPositionA();
            Position positionB = brick.getPositionB();
            if (positionA == null || positionB == null) {
                return null;
            }
        }
        return brickList;
    }

    private static void inputMatrixDimensions() {
//        String message = "Input rows (N) and columns (M): ";
        while (!isInputValid(n) || !isInputValid(m)) {
//            System.out.print(message);
//            Scanner cin = new Scanner(System.in);
            try {
                n = cin.nextInt();
                m = cin.nextInt();
            } catch (Exception e) {
                System.out.println("Not a number!");
            }
            if (isInputValid(n) && isInputValid(m)) {
                return;
            }
            n = m = 0;
//            message = "Input correct rows(N) and columns (M): ";
        }
    }

    /**
     * Checks if input value is even and between 0 and 100.
     * @param a
     * @return
     */
    private static boolean isInputValid(int a) {
        return (a > 0 && a <= 100) && a % 2 == 0;
    }

}
