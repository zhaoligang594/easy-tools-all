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
     * @param text1 first
     * @param text2 second
     * @return the distance between them
     */
    public static int levenDistance(String text1, String text2) {
        AssertUtils.text(text1, "text1");
        AssertUtils.text(text2, "text2");
        if ("".equals(text1)) {
            return text2.length();
        }
        if ("".equals(text2)) {
            return text1.length();
        }
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int substitutionCost;
                if (text1.charAt(i) == text2.charAt(j)) {
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
