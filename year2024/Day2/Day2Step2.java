package advent_of_code.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day2Step2 {
    private static final String SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day2\\sample.txt";
    private static final String INPUT_FILE_NAME = ".\\src\\advent_of_code\\day2\\input.txt";
    
    public static void main(String[] args) throws IOException {
        Day2Step2 day2 = new Day2Step2();
        day2.day2();
    }
    
    // Part1
    public void day2() throws IOException {
        String[] allReports = getInput(INPUT_FILE_NAME);
        
        int safeCount = 0;
        for (int i = 0; i < allReports.length; i++) {
            int[] report = Stream.of(allReports[i].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if (!allLevelSafe(report)) {
                continue;
            }
            safeCount++;
        }
        System.out.println(safeCount);
    }
    
    // initialize
    private String[] getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(fileName).toAbsolutePath();
        return Files.readString(inputFile).split("\r\n");
    }
    
    private boolean allLevelSafe(int[] report) {
        if (report.length == 1) {
            return false;
        }
        boolean isAsc = true;
        if (!(report[0] < report[report.length - 1])) {
            isAsc = false;
        }
        for (int i = 1; i < report.length; i++) {
            int absValue = (int) Math.abs(report[i - 1] - report[i]);
            if (isAsc && !(report[i - 1] < report[i])) {
                return false;
            }
            if (!isAsc && !(report[i - 1] > report[i])) {
                return false;
            }
            if (!(1 <= absValue && absValue <= 3)) {
                return false;
            }
        }
        return true;
    }
    // ifを浅くできた。

    /*
     * ・report.length == 1 の時、falseではなくtrueだ。
     */
    
    /*
     * https://github.com/katataku/advent_of_code/pull/2
     * ・ifが続いている所は関数に分けてもよい
     * ・関数の役割は1つだとよさそう
     * ・差分を反転して使える
     * ・降順はもう1回確認せずにひっくり返せば昇順の確認を使える
     * https://github.com/thonda28/adventofcode/pull/2
     */
}
