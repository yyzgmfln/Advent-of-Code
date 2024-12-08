import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day2Step4 {
    private static final String SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day2\\sample.txt";
    private static final String INPUT_FILE_NAME = ".\\src\\advent_of_code\\day2\\input.txt";
    
    public static void main(String[] args) throws IOException {
        Day2Step4 day2 = new Day2Step4();
        day2.part1();
        day2.part2();
    }
    
    public void part1() throws IOException {
        List<int[]> reports = getInput(INPUT_FILE_NAME);
        int safeCount = 0;
        for (int i = 0; i < reports.size(); i++) {
            int[] report = reports.get(i);
            if (!isSafe(report)) {
                continue;
            }
            safeCount++;
        }
        System.out.println(safeCount);
    }
    
    public void part2() throws IOException {
        List<int[]> reports = getInput(INPUT_FILE_NAME);
        int safeCount = 0;
        for (int i = 0; i < reports.size(); i++) {
            int[] report = reports.get(i);
            if (!isSafe(report) && !isSafeWhenRemoveOneLevel(report)) {
                continue;
            }
            safeCount++;
        }
        System.out.println(safeCount);
    }

    // initialize
    private List<int[]> getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(fileName).toAbsolutePath();
        String[] input = Files.readString(inputFile).split("\r\n|\n|\r");
        List<int[]> reports = new ArrayList<>();
        for (String report : input) {
            reports.add(Stream.of(report.split("\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray());
        }
        return reports;
    }
    
    private boolean isSafe(int[] report) {
        if (report.length < 2) {
            return true;
        }
        boolean isAsc = true;
        if (!(report[0] < report[1])) {
            isAsc = false;
        }
        for (int i = 1; i < report.length; i++) {
            int difference = report[i] - report[i - 1];
            if (!isAsc) {
                difference *= -1;
            }
            if (!(1 <= difference && difference <= 3)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSafeWhenRemoveOneLevel(int[] report) {
        for (int i = 0; i < report.length; i++) {
            int[] firstHalf = Arrays.copyOfRange(report, 0, i);
            int[] lastHalf = Arrays.copyOfRange(report, i + 1, report.length);
            int[] skipOneLevel = new int[firstHalf.length + lastHalf.length];
            System.arraycopy(firstHalf, 0, skipOneLevel, 0, firstHalf.length);
            System.arraycopy(lastHalf, 0, skipOneLevel, firstHalf.length, lastHalf.length);
            if (isSafe(skipOneLevel)) {
                return true;
            }
        }
        return false;
    }
}
