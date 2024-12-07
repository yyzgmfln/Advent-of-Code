import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day1Step4 {
    private static final String FILE_NAME = ".\\src\\advent_of_code\\day1\\input.txt";
    
    public static void main(String[] args) throws IOException {
        Day1Step4 day1 = new Day1Step4();
        day1.run();
    }
    
    public void run() throws IOException {
        String[] twoLocationIds = getInput(FILE_NAME);
        int[] leftLocationIds = new int[twoLocationIds.length];
        int[] rightLocationIds = new int[twoLocationIds.length];
        fillLocationIds(twoLocationIds, leftLocationIds, rightLocationIds);
        
        // Part1
        int sum = calculateDistanceSum(leftLocationIds, rightLocationIds);
        System.out.println(sum);
        
        // Part2
        int similaliryScore = calculateSimilarityScore(leftLocationIds, rightLocationIds);
        System.out.println(similaliryScore);
    }

    // get input
    private String[] getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(FILE_NAME).toAbsolutePath();
        return Files.readString(inputFile).split("\\r\\n");
    }
    
    // initialize
    private void fillLocationIds(String[] twoLocationIds, int[] leftLocationIds, int[] rightLocationIds) {
        for (int i = 0; i < twoLocationIds.length; i++) {
            String[] ids = twoLocationIds[i].trim().split("\\s+");
            leftLocationIds[i] = Integer.parseInt(ids[0]);
            rightLocationIds[i] = Integer.parseInt(ids[1]);
        }
    }
    
    // Part1
    private int calculateDistanceSum(int[] leftLocationIds, int[] rightLocationIds) {
        // change input
        Arrays.sort(leftLocationIds);
        Arrays.sort(rightLocationIds);
        int distanceSum = 0;
        for (int i = 0; i < leftLocationIds.length; i++) {
            distanceSum += Math.abs(leftLocationIds[i] - rightLocationIds[i]);
        }
        return distanceSum;
    }
    
    // Part2
    private int calculateSimilarityScore(int[] leftLocationIds, int[] rightLocationIds) {
        Map<Integer, Integer> rightIdToCount = new HashMap<>();
        for (int rightId : rightLocationIds) {
            int count = rightIdToCount.getOrDefault(rightId, 0);
            rightIdToCount.put(rightId, count + 1);
        }
        
        int similarityScore = 0;
        for (int leftId : leftLocationIds) {
            similarityScore += leftId * rightIdToCount.getOrDefault(leftId, 0);
        }
        return similarityScore;
    }    
}
