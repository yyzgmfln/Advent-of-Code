package advent_of_code.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day3Step1 {
    private static final String PART1_SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day3\\part1_sample.txt";
    private static final String PART2_SAMPLE_FILE_NAME = ".\\src\\advent_of_code\\day3\\part2_sample.txt";
    private static final String INPUT_FILE_NAME = ".\\src\\advent_of_code\\day3\\input.txt";
    
    private static final String INSTRUCTION_MUL_STR = "mul";
    private static final String INSTRUCTION_DONT = "don't()";
    private static final String INSTRUCTION_DO = "do()";
    
    public static void main(String[] args) throws IOException {
        String instructions = getInput(INPUT_FILE_NAME);
        Day3Step1 day3 = new Day3Step1();
        
        int total = day3.part1_findTotalMultiple(instructions);
        System.out.println(total);
        System.out.println(total == 191183308); // 自分のinputでリファクタリングが間違っていないか
        
        int validTotal = day3.part2_findValidTotalMultiple(instructions);
        System.out.println(validTotal);
        System.out.println(validTotal == 92082041);
    }    
    
    private static String getInput(String fileName) throws IOException {
        Path inputFile = Paths.get(fileName).toAbsolutePath();
        return Files.readString(inputFile);
    }
    
    private int part1_findTotalMultiple(String instructions) throws IOException {
        int index = 0;
        List<Integer[]> combinations = new ArrayList<>(); // [startIndex, endIndex]
        while (index < instructions.length() - INSTRUCTION_MUL_STR.length()) {
            String instruction = instructions.substring(index, index + INSTRUCTION_MUL_STR.length());
            if (!instruction.equals(INSTRUCTION_MUL_STR)) {
                index++;
                continue;
            }
            index += 3;
            Integer[] indexRange = findUncorruptedIndexRange(instructions, index);
            if (indexRange == null) {
                continue;
            }
            combinations.add(new Integer[] {indexRange[0], indexRange[1]});
            index += indexRange[0].toString().length() + indexRange[1].toString().length() + 3; // skip (,)
        }
        
        return calculateMultiple(combinations);
    }
    
    private int part2_findValidTotalMultiple(String instructions) throws IOException {
        int index = 0;
        List<Integer[]> combinations = new ArrayList<>(); // [startIndex, endIndex]
        boolean isDisabled = false; 
        while (index < instructions.length()) {
            if (index < instructions.length() - INSTRUCTION_DO.length()) {
                String instruction = instructions.substring(index, index + INSTRUCTION_DO.length());
                if (instruction.equals(INSTRUCTION_DO)) { 
                    isDisabled = false;
                    index += INSTRUCTION_DO.length();
                    continue;
                }
            }
            if (index < instructions.length() - INSTRUCTION_DONT.length()) {
                String instruction = instructions.substring(index, index + INSTRUCTION_DONT.length());
                if (instruction.equals(INSTRUCTION_DONT)) {
                    isDisabled = true;
                    index += INSTRUCTION_DONT.length();
                }
            }
            if (isDisabled) {
                index++;
                continue;
            }
            if (index < instructions.length() - INSTRUCTION_MUL_STR.length()) {
                String instruction = instructions.substring(index, index + INSTRUCTION_MUL_STR.length());
                if (!instruction.equals(INSTRUCTION_MUL_STR)) {
                    index++;
                    continue;
                }
                index += INSTRUCTION_MUL_STR.length();
                Integer[] indexRange = findUncorruptedIndexRange(instructions, index);
                if (indexRange == null) {
                    continue;
                }
                combinations.add(new Integer[] {indexRange[0], indexRange[1]});
                index += indexRange[0].toString().length() + indexRange[1].toString().length() + 3; // skip (,)
                continue;
            }
            index++;
        }
        
        return calculateMultiple(combinations);
    }

    private Integer[] findUncorruptedIndexRange(String instructions, int startIndex) {
        int index = startIndex;
        while (index < instructions.length()) {
            if (instructions.charAt(index) != '(') {
                return null;
            }
            index++;
            
            StringBuilder first = new StringBuilder();
            while (index < instructions.length() && Character.isDigit(instructions.charAt(index))) {
                first.append(instructions.charAt(index));
                index++;
            }
            if (first.length() == 0) {
                return null;
            }
            Integer firstNumber = Integer.parseInt(first.toString());
            if (instructions.charAt(index) != ',') {
                return null;
            }
            index++;
            
            StringBuilder last = new StringBuilder();
            while (index < instructions.length() && Character.isDigit(instructions.charAt(index))) {
                last.append(instructions.charAt(index));
                index++;
            }
            if (last.length() == 0) {
                return null;
            }
            Integer lastNumber = Integer.parseInt(last.toString());
            if (instructions.charAt(index) != ')') {
                return null;
            }
            return new Integer[] {firstNumber, lastNumber};
        }
        return null;
    }

    private int calculateMultiple(List<Integer[]> combinations) {
        int total = 0;
        for (Integer[] combination : combinations) {
            total += combination[0] * combination[1]; 
        }
        return total;
    }
    
    /*
     * inputの文字列長をnとする
     * Part1
     * 時間計算量:O(n^2)
     * 空間計算量:O(n)
     * Part2
     * 時間計算量:O(n^2)
     * 空間計算量:O(n)
     * 
     * ・同じような操作の塊があってスッキリさせたかったが、処理の流れの調整と値の代入を同時にやっているので、
     * 切り出してもさほど分かりやすくなると思えなかった。(INSTRUCTION_XXXあたり、findUncorruptedIndexRangeのfirstNumberとlastNumberあたり)
     * 　・1文字ずつ見るやり方をする時点で複雑になる感じはするかもしれない。
     * ・命令の種類が増えた時に拡張性が弱そう。似たコードブロックを追加する必要がある。順番も気にしつつ中身の実装も気にしないといけなく、追加時の負担がかかりそう。
     * ・数字が最大3桁という条件を見落とした。
     */
}
