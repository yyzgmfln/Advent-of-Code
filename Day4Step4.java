package advent_of_code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day4Step4 {
    /*
     * https://github.com/thonda28/adventofcode/pull/4
     * https://github.com/katataku/advent_of_code/pull/4
     */
    
    private static final String SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day4\\sample.txt";
    private static final String INPUT_FILE_NAME = ".\\src\\advent_of_code\\day4\\input.txt";
    
    public static void main(String[] args) throws IOException {
        char[][] input = getInput(INPUT_FILE_NAME);
        Day4Step4 day4 = new Day4Step4();
        
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
        int[][] deltas = new int[][] { {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1} };
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] != 'X') {
                    continue;
                }
                for (int[] delta : deltas) {
                    count += searchOneDirection(input, row, delta[0], col, delta[1], "MAS");
                }
            }
        }
        
        return count;
    }
    
    private int searchOneDirection(char[][] input, int row, int deltaRow, int col, int deltaCol, String word) {
        for (int i = 0; i < word.length(); i++) {
            row += deltaRow;
            col += deltaCol;
            if (!isTargetChar(input, row, col, word.charAt(i))) {
                return 0;
            }
        }
        return 1;
    }
    
    private int part2_searchXshapeMAS(char[][] input) {
        int count = 0;
        int[][] deltas = new int[][] { {-1, -1}, {-1, 1}, {1, 1}, {1, -1} };
        for (int row = 1; row < input.length; row++) {
            for (int col = 1; col < input[0].length; col++) {
                for (int direction = 0; direction < 4; direction++) {
                    if (isTargetChar(input, row, col, 'A')
                            && isTargetChar(input, row + deltas[(0 + direction) % 4][0], col + deltas[(0 + direction) % 4][1], 'M')
                            && isTargetChar(input, row + deltas[(1 + direction) % 4][0], col + deltas[(1 + direction) % 4][1], 'M')
                            && isTargetChar(input, row + deltas[(2 + direction) % 4][0], col + deltas[(2 + direction) % 4][1], 'S')
                            && isTargetChar(input, row + deltas[(3 + direction) % 4][0], col + deltas[(3 + direction) % 4][1], 'S')) {
                        
                        count++;
                    }
                    
                }
            }
        }
        return count;
    }

    private boolean isTargetChar(char[][] input, int row, int col, char c) {
        if (!endIndexInInput(input, row, col)) {
            return false;
        }
        return input[row][col] == c;
    }

    private boolean endIndexInInput(char[][] input, int row, int col) {
        return 0 <= row && row < input.length && 0 <= col && col < input[0].length;
    }
}
