package gg.acai.aurora.ml;

/**
 * @author Clouke
 * @since 07.03.2023 02:46
 * © Aurora - All Rights Reserved
 */
public class LevenshteinDistance {

  public static int compute(CharSequence lhs, CharSequence rhs) {
    int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

    for (int i = 0; i <= lhs.length(); i++)
      distance[i][0] = i;
    for (int j = 1; j <= rhs.length(); j++)
      distance[0][j] = j;

    for (int i = 1; i <= lhs.length(); i++)
      for (int j = 1; j <= rhs.length(); j++)
        distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1), distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

    return distance[lhs.length()][rhs.length()];
  }

}
