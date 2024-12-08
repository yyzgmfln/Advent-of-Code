import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day2Step1 {
    private static final String FILE_NAME = ".\\src\\advent_of_code\\day2\\input.txt";
    
    public static void main(String[] args) throws IOException {
        Day2Step1 day2 = new Day2Step1();
        day2.day2();
    }
    
    public void day2() throws IOException {
        String[] allReports = getInput(FILE_NAME);
        
        // Part1, Part2
        int safeCount = 0;
        for (int i = 0; i < allReports.length; i++) {
            int[] report = Stream.of(allReports[i].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            /* Part1
            if (!allLevelSafe(report)) {
                continue;
            }
            */
            // Part2
            if (!allLevelSafe(report) && !isSafeWhenRemoveOneLevel(report)) {
                continue;
            }
            safeCount++;
        }
        System.out.println(safeCount);
    }

    // initialize
    private String[] getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(FILE_NAME).toAbsolutePath();
        return Files.readString(inputFile).split("\r\n");
    }
    
    // Part1
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
            if (isAsc) {
                if (!(report[i - 1] < report[i] && 1 <= absValue && absValue <= 3)) {
                    return false;
                }
                continue;
            }
            if (!isAsc) {
                if (!(report[i - 1] > report[i] && 1 <= absValue && absValue <= 3)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    // Part2
    private boolean isSafeWhenRemoveOneLevel(int[] report) {
        for (int i = 0; i < report.length; i++) {
            int[] firstHalf = Arrays.copyOfRange(report, 0, i);
            int[] lastHalf = Arrays.copyOfRange(report, i + 1, report.length);
            int[] skipOneLevel = new int[firstHalf.length + lastHalf.length];
            System.arraycopy(firstHalf, 0, skipOneLevel, 0, firstHalf.length);
            System.arraycopy(lastHalf, 0, skipOneLevel, firstHalf.length, lastHalf.length);
            if (allLevelSafe(skipOneLevel)) {
                return true;
            }
        }
        return false;
    }
    // 下記を参考にした 1つでもtrueがあればtrueになることを分かることができなかった
    // https://github.com/chang2049/AdventOfCode2024/blob/main/src/main/java/science/changliu/Day02.java
    // 確かに、山谷が2つ以上だったら、1つ消してもfalseなので、1つ消してtrueになるものが見つかった時点でsafeになれる
    
    
    // Part2 WA sampleはうまくいく
    private boolean isSafeWhenRemoveOneLevel_WA(int[] report) {
        if (report.length == 1) {
            return false;
        }
        boolean isAsc = true;
        if (!(report[0] < report[report.length - 1])) {
            isAsc = false;
        }
        int left = 0;
        int skipIndex = -1;
        for (int right = 1; right < report.length; right++) {
            int absValue = (int) Math.abs(report[right] - report[left]);
            if (isAsc) {
                if (!(report[left] < report[right] && 1 <= absValue && absValue <= 3)) {
                    if (right - left > 1) {
                        return false;
                    }
                    skipIndex = right;
                    continue;
                }
                left++;
                if (left == skipIndex) {
                    left++;
                }
                continue;
            }
            if (!isAsc) {
                if (!(report[left] > report[right] && 1 <= absValue && absValue <= 3)) {
                    if (right - left > 1) {
                        return false;
                    }
                    skipIndex = right;
                    continue;
                }
                left++;
                if (left == skipIndex) {
                    left++;
                }
                continue;
            }
        }
        return true;
    }
    // 配列をコピーしたくなかったが、WAにせよ、ifが入れ込みすぎている。
    
    
    /*
     * nがreportの個数、mがそれぞれのreportの長さとする
     * Part1
     * 時間計算量：O(n*max(m))
     * 空間計算量：O(max(m)): inputはO(nm)だけど含めないとして、reportを毎回作っていると考えた。
     * 
     * Part2
     * 時間計算量：O(n^2*max(m))
     * 空間計算量：O(max(m)^2)
     */
}
