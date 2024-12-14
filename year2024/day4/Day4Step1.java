package advent_of_code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day4Step1 {
    private static final String SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day4\\sample.txt";
    private static final String INPUT_FILE_NAME = ".\\src\\advent_of_code\\day4\\input.txt";
    
    public static void main(String[] args) throws IOException {
        char[][] input = getInput(INPUT_FILE_NAME);
        Day4Step1 day4 = new Day4Step1();
        
        int XMAScount = day4.part1_searchXMAS(input);
        System.out.println(XMAScount == 2560);
        
        int XshapeMAScount = day4.part2_searchXshapeMAS(input);
        System.out.println(XshapeMAScount == 1910);
    }

    // initialize
    private static char[][] getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(fileName).toAbsolutePath();
        String[] input1D = Files.readString(inputFile).split("\r\n|\r|\n");
        char[][] input2D = new char[input1D.length][input1D[0].length()];
        for (int row = 0; row < input2D.length; row++) {
            String line = input1D[row];
            for (int col = 0; col < input2D[0].length; col++) {
                input2D[row][col] = line.charAt(col);
            }
        }
        return input2D;
    }
    
    private int part1_searchXMAS(char[][] input) {
        int count = 0;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] != 'X') {
                    continue;
                }
                count += searchHorizontal(input, row, col);
                count += searchVertical(input, row, col);
                count += searchDiagonal(input, row, col);
            }
        }
        return count;
    }

    private int searchHorizontal(char[][] input, int row, int col) {
        int count = 0;
        // ->
        if (endIndexInInput(input, row + 3, col) 
                && isStrMAS(input, row + 1, col, row + 2, col, row + 3, col)) {
            count++;
        }
        // <-
        if (endIndexInInput(input, row - 3, col) 
                && isStrMAS(input, row - 1, col, row - 2, col, row - 3, col)) {
            count++;
        }
        return count;
    }
    
    private int searchVertical(char[][] input, int row, int col) {
        int count = 0;
        // ↑
        if (endIndexInInput(input, row, col - 3) 
                && isStrMAS(input, row, col - 1, row, col - 2, row , col - 3)) {
            count++;
        }
        // ↓
        if (endIndexInInput(input, row, col + 3) 
                && isStrMAS(input, row, col + 1, row, col + 2, row , col + 3)) {
            count++;
        }
        return count;
    }
    
    private int searchDiagonal(char[][] input, int row, int col) {
        int count = 0;
        // ↗
        if (endIndexInInput(input, row - 3, col + 3) 
                && isStrMAS(input, row - 1, col + 1, row - 2, col + 2, row - 3, col + 3)) {
            count++;
        }
        // ↘
        if (endIndexInInput(input, row + 3, col + 3) 
                && isStrMAS(input, row + 1, col + 1, row + 2, col + 2, row + 3, col + 3)) {
            count++;
        }
        // ↙
        if (endIndexInInput(input, row + 3, col - 3) 
                && isStrMAS(input, row + 1, col - 1, row + 2, col - 2, row + 3, col - 3)) {
            count++;
        }
        // ↖
        if (endIndexInInput(input, row - 3, col - 3) 
                && isStrMAS(input, row - 1, col - 1, row - 2, col - 2, row - 3, col - 3)) {
            count++;
        }
        return count;
    }

    private boolean isStrMAS(char[][] input, int row1, int col1, int row2, int col2, int row3, int col3) {
        return input[row1][col1] == 'M' && input[row2][col2] == 'A' && input[row3][col3] == 'S';
    }

    private boolean endIndexInInput(char[][] input, int row, int col) {
        return 0 <= row && row < input.length && 0 <= col && col < input[0].length;
    }
    
    private int part2_searchXshapeMAS(char[][] input) {
        int count = 0;
        for (int row = 0; row < input.length - 2; row++) {
            for (int col = 0; col < input[0].length - 2; col++) {
                if (isStr(input, row, col, 'M') && isStr(input, row, col + 2, 'M')
                        || isStr(input, row, col, 'M') && isStr(input, row, col + 2, 'S')
                        || isStr(input, row, col, 'S') && isStr(input, row, col + 2, 'M')
                        || isStr(input, row, col, 'S') && isStr(input, row, col + 2, 'S')) {
                    
                    count += searchShapeInDiagonal(input, row, col);
                }
            }
        }
        return count;
    }

    private int searchShapeInDiagonal(char[][] input, int row, int col) {
        if (!endIndexInInput(input, row + 2, col + 2)) {
            return 0;
        }
        // (↘ MAS || SAM) && (↙ MAS || SAM)
        if (    (isStrMAS(input, row, col, row + 1, col + 1, row + 2, col + 2)
                || 
                isStrMAS(input, row + 2, col + 2, row + 1, col + 1, row, col))
             && 
                (isStrMAS(input, row, col + 2, row + 1, col + 1, row + 2, col)
                || 
                isStrMAS(input, row + 2, col, row + 1, col + 1, row, col + 2))) {
            
            return 1;
        }
        return 0;
    }

    private boolean isStr(char[][] input, int row, int col, char c) {
        if (!endIndexInInput(input, row, col)) {
            return false;
        }
        return input[row][col] == c;
    }

}
/*
 * Part1
 * 時間計算量:O(n^2)
 * 空間計算量:O(1)
 * ・問題文の or even overlapping other words を勘違いしていた
 * XMASMAS は２カウントできるかと思った。そうじゃなくて、
 * XMASAMX のように2回同じ文字を使ってもいいということか。
 * 
 * Part2
 * 時間計算量:O(n^2)
 * 空間計算量:O(1)
 */