package advent_of_code.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Step2 {
    
    /*
     * https://github.com/katataku/advent_of_code/pull/3
     * ・スライスして,と)のインデックスの位置を求めて、から処理するとすごくスッキリ書ける。
     * ・どっちみち1文字ずつずれていかないといけなかった。ちょうどforで書ける。
     * ・disabledじゃなかったらdo()かも見なくていいね。
     * ・正規表現という考え方があった。
     * https://github.com/thonda28/adventofcode/pull/3
     * ・re.FindAllStringSubmatch()便利すぎる。
     * 　・JavaでWebヒットせず、GPTに聞いたらjava.util.regexのPatternとMatcherでやる方法を教えてくれた。※１
     * ・do()とdont()をなめながら有効なmulの範囲を取得すれば、part1の関数をそのまま使いまわせる。
     */
    
    // ※１ GPTコード
    public void findAllStringSubmatch(String[] args) {
        String input = "abc123def456ghi789";
        String regex = "(\\w+)(\\d+)"; // キャプチャグループを含む正規表現

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        List<String[]> matches = new ArrayList<>();

        while (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i <= matcher.groupCount(); i++) {
                groups[i] = matcher.group(i);
            }
            matches.add(groups);
        }

        // 結果の表示
        for (String[] match : matches) {
            System.out.println("Match: " + match[0]);
            System.out.println("Group 1: " + match[1]);
            System.out.println("Group 2: " + match[2]);
        }
    }
    /*
        入力文字列"abc123def456ghi789"に対して、正規表現"(\\w+)(\\d+)"を使用した場合の出力：
        
        Match: abc123
        Group 1: abc
        Group 2: 123
        Match: def456
        Group 1: def
        Group 2: 456
        Match: ghi789
        Group 1: ghi
        Group 2: 789
        
        解説
            正規表現定義: (\\w+)(\\d+) は、文字列部分と数字部分をキャプチャします。
            Matcher.find(): 一致する箇所を逐次検索します。
            キャプチャグループ取得: matcher.group(int) を使用して、各キャプチャグループを取得します。
        これにより、GolangのFindAllStringSubmatchに類似した動作をJavaで実現できます。
     */
    
}
