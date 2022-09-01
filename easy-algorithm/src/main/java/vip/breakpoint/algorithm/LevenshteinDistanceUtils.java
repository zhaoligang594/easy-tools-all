package vip.breakpoint.algorithm;

import vip.breakpoint.utils.AssertUtils;

/**
 * 文本编辑距离
 * <p>
 * https://en.wikipedia.org/wiki/Levenshtein_distance
 *
 * @author : breakpoint
 * create on 2022/09/02
 * 欢迎关注公众号 《代码废柴》
 */
public class LevenshteinDistanceUtils {

    /**
     * 计算两个字符串的文本距离
     *
     * @param word1 first
     * @param word2 second
     * @return the distance between them
     */
    public static int levenDistance(String word1, String word2) {
        AssertUtils.text(word1, "word1");
        AssertUtils.text(word2, "word2");
        if ("".equals(word1)) {
            return word2.length();
        }
        if ("".equals(word2)) {
            return word1.length();
        }
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int substitutionCost;
                if (word1.charAt(i) == word2.charAt(j)) {
                    substitutionCost = 0;
                } else {
                    substitutionCost = 1;
                }
                dp[i + 1][j + 1] = Math.min(dp[i][j] + substitutionCost, Math.min(dp[i][j + 1] + 1, dp[i + 1][j] + 1));
            }
        }
        return dp[m][n];
    }

}
