package gg.acai.aurora.hierarchy.mst;

import gg.acai.aurora.Clusterer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Application of Kruskal's MST algorithm to clustering data
 *
 * @author Jonathan
 * @since 23.04.2023 20:45
 * © Aurora - All Rights Reserved
 */
final class DisjointSet {
  int[] e;

  DisjointSet(int size) {
    e = new int[size];
    Arrays.fill(e, -1);
  }

  boolean sameSet(int a, int b) {
    return find(a) == find(b);
  }

  int size(int x) {
    return -e[find(x)];
  }

  int find(int x) {
    return (e[x] < 0) ? x : (e[x] = find(e[x]));
  }

  boolean join(int a, int b) {
    a = find(a);
    b = find(b);
    if (a == b) return false;
    if (e[a] > e[b]) {
      int temp = a;
      a = b;
      b = temp;
    }
    e[a] += e[b];
    e[b] = a;
    return true;
  }
}

final class Edge implements Comparable<Edge> {
  double comp_value;
  int i, j;

  Edge(double comp_value, int i, int j) {
    this.comp_value = comp_value;
    this.i = i;
    this.j = j;
  }

  @Override
  public int compareTo(Edge o) {
    return (comp_value < o.comp_value ? 1 : 0) - (o.comp_value < comp_value ? 1 : 0);
  }
}

public class MSTClusterer implements Clusterer {

  public List<List<double[]>> cluster(double[][] input, int desiredClusterCount) {
    if (input.length < desiredClusterCount) {
      throw new IllegalArgumentException("Not enough data points in input to create desired number of clusters");
    }
    DisjointSet ds = new DisjointSet(input.length);

    int edgeIdx = 0;
    Edge[] edges = new Edge[input.length * (input.length - 1) / 2];

    for (int i = 0; i < input.length; ++i) {
      for (int j = i + 1; j < input.length; ++j) {
        double sum_squared_diffs = 0;

        // not needed
        assert input[i].length == input[j].length;

        if (input[i].length != input[j].length) {
          throw new IllegalArgumentException("Mismatching data point degrees");
        }

        for (int k = 0; k < input[i].length; ++k) {
          sum_squared_diffs += (input[i][k] - input[j][k]) * (input[i][k] - input[j][k]);
        }

        edges[edgeIdx++] = new Edge(sum_squared_diffs, i, j);
      }
    }

    Arrays.sort(edges);
    int currNumClusters = input.length;
    while (currNumClusters > desiredClusterCount) {
      Edge curr = edges[--edgeIdx];
      currNumClusters -= ds.join(curr.i, curr.j) ? 1 : 0;
    }

    boolean[] visited = new boolean[input.length];
    int[] index = new int[input.length];

    Arrays.fill(visited, false);
    List<List<double[]>> result = new ArrayList<>(desiredClusterCount);
    for (int i = 0; i < desiredClusterCount; i++) {
      result.add(new ArrayList<>(ds.size(i)));
    }

    for (int i = 0, j = 0; i < input.length; i++) {
      int u = ds.find(i);
      if (visited[u]) continue;
      visited[u] = true;
      index[u] = j++;
    }

    for (int i = 0; i < input.length; i++) {
      result.get(index[ds.find(i)]).add(input[i].clone());
    }

    return result;
  }

  public double[] predict(double[] input) {
    throw new UnsupportedOperationException();
  }
}
