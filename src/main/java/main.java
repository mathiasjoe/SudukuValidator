

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The type Main. a sudoku validator
 */
public class main {
    private static int[][] s;
    private static boolean rowCheck;
    private static boolean columnChecker;
    private static boolean gridCheck;

    /**
     * Row checker. Checks if the rows of the sudoku bord is valid or not
     *
     * @param s the sudoku puzzle
     */
// row checker
    public static void rowChecker(int[][] s) {
        rowCheck = true;
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 8; col++)
                for (int col2 = col + 1; col2 < 9; col2++)
                    if (s[row][col] == s[row][col2]) {
                        rowCheck = false;
                        break;
                    }

    }


    /**
     * Column checker.
     *
     * @param s the sudoku puzzle
     */
// column checker
    public static void columnChecker(int[][] s) {
        columnChecker = true;
        for (int col = 0; col < 9; col++)
            for (int row = 0; row < 8; row++)
                for (int row2 = row + 1; row2 < 9; row2++)
                    if (s[row][col] == s[row2][col])
                        columnChecker = false;
    }


    /**
     * Grid checker.
     *
     * @param s the sudoku puzzle
     */
// grid checker
    public static void gridChecker(int[][] s) {
        gridCheck = true;
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                // row, col is start of the 3 by 3 grid
                for (int pos = 0; pos < 8; pos++) {
                    for (int pos2 = pos + 1; pos2 < 9; pos2++) {
                        if (s[row + pos % 3][col + pos / 3] == s[row + pos2 % 3][col + pos2 / 3]) {
                            gridCheck = false;
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * Scanner int [ ] [ ].
     *
     * @param filePath the file path
     * @return the int [ ] [ ]
     * @throws FileNotFoundException the file not found exception
     */
    public static int[][] scanner(String filePath) throws FileNotFoundException {
        int[][] sudoku = new int[9][9];
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",");
        int u = 0;
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(";");
            for (int i = 0; i < 9; i++) {
                sudoku[u][i] = Integer.parseInt(line[i]);
            }
            u++;
        }
        System.out.println(Arrays.deepToString(sudoku));

        return sudoku;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        String path = "src/main/resources/sudoku.csv";

        Thread thread = new Thread(() -> {
            try {
                s = scanner(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {

                gridChecker(s);
                if (!gridCheck) {
                    System.out.println("Error in grid check");
                }
            }

        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {


                columnChecker(s);
                if (!columnChecker) {
                    System.out.println("Error in column check");
                }
            }

        });


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

                rowChecker(s);
                if (!rowCheck) {
                    System.out.println("Error in row check");
                }
            }
        });
        thread.join();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println(rowCheck + "row");
        System.out.println(columnChecker + "column");
        System.out.println(gridCheck + "grid");
        if (rowCheck && columnChecker && gridCheck) {
            System.out.println("valid babe<3");
        } else {
            System.out.println("Invalid bitch </3");
        }
    }
}

