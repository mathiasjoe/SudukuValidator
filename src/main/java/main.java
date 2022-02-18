

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
                        System.out.println("Sudoku bord not valid. Error in: " + " row " + (row+1) + " in column " + (col+1) + " and " + (col2+1) +" as they are containing the same number, number: " + s[row][col]);
                    }

    }


    /**
     * Column checker. Checks if the columns of the sudoku bord is valid or not
     *
     * @param s the sudoku puzzle
     */
// column checker
    public static void columnChecker(int[][] s) {
        columnChecker = true;
        for (int col = 0; col < 9; col++)
            for (int row = 0; row < 8; row++)
                for (int row2 = row + 1; row2 < 9; row2++)
                    if (s[row][col] == s[row2][col]) {
                        columnChecker = false;
                        System.out.println("Sudoku bord not valid. Error in: " + " column " + (col+1) + " in row " + (row+1) + " and " + (row2+1) +" as they are containing the same number, number: " + s[row][col]);
                    }
    }


    /**
     * Grid checker. Checks if the grids of the sudoku bord is valid or not
     *
     * @param s the sudoku puzzle
     */
    public static void gridChecker(int[][] s) {
        gridCheck = true;
        int gridNumber = 0;
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                gridNumber +=1;
                // row, col is start of the 3 by 3 grid
                for (int pos = 0; pos < 8; pos++) {
                    for (int pos2 = pos + 1; pos2 < 9; pos2++) {
                        if (s[row + pos % 3][col + pos / 3] == s[row + pos2 % 3][col + pos2 / 3]) {
                            gridCheck = false;
                            System.out.println("Sudoku bord not valid. Error in grid: " + gridNumber );
                            //todo return the string and print it out in a 4th thred. can be multiple strings string with new line
                        }
                    }
                }
            }
        }
    }


    /**
     * Scanner int [ ] [ ]. Reads the csv file and returns an array of type int
     *
     * @param filePath the file path
     * @return the int [ ] [ ] containing the sudoku bord
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

            //todo user input path
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
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                columnChecker(s);
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                rowChecker(s);
            }
        });

        thread.start();
        thread.join();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

//        System.out.println(rowCheck + "row");
//        System.out.println(columnChecker + "column");
//        System.out.println(gridCheck + "grid");
        if (rowCheck && columnChecker && gridCheck) {
            System.out.println("valid");
        } else {
            System.out.println("Invalid");
        }
    }
}

