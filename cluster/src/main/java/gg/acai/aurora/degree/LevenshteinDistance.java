package gg.acai.aurora.degree;

/**
 * @author Clouke
 * @since 07.03.2023 02:46
 * © Aurora - All Rights Reserved
 */
public class LevenshteinDistance {

  public static int compute(CharSequence lhs, CharSequence rhs) {
    int l_length = lhs.length();
    int r_length = rhs.length();
    int[][] distance = new int[l_length + 1][r_length + 1];

    for (int i = 0; i <= l_length; i++) distance[i][0] = i;
    for (int j = 1; j <= r_length; j++) distance[0][j] = j;

    for (int i = 1; i <= l_length; i++)
      for (int j = 1; j <= r_length; j++)
        distance[i][j] = Math.min(
          Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
          distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1)
        );

    return distance[l_length][r_length];
  }

}
