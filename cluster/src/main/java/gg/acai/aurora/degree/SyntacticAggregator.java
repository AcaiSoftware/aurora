package gg.acai.aurora.degree;

import gg.acai.aurora.ml.LevenshteinDistance;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>The purpose of this class is to perform cluster classification on a set of inputs,
 * which involves grouping similar inputs together and generating a classification for each group.
 * The process of cluster classification can be broken down into three main steps:</p>
 * <ol>
 *   <li>Clustering inputs into groups</li>
 *   <li>Grouping similar inputs together</li>
 *   <li>Generating a classification for each group</li>
 * </ol>
 *
 * <p>Commonly used for Natural Language Processing and other machine learning applications.
 *
 * @author Clouke
 * @since 07.03.2023 04:03
 * © Aurora - All Rights Reserved
 */
public class SyntacticAggregator implements DegreeCluster<String> {

  private final List<DegreeGroup<String>> groups;
  private final double similarityThreshold;
  private final double maxDistance;

  public SyntacticAggregator(double threshold, double maxDistance) {
    this.groups = new ArrayList<>();
    this.similarityThreshold = threshold;
    this.maxDistance = maxDistance;
  }

  public SyntacticAggregator() {
    this(0.8, 2.0);
  }

  @Override
  public DegreeGroup<String> cluster(String input) {
    DegreeGroup<String> match = null;
    int inputLength = input.length();
    int matchLength = 0;
    for (DegreeGroup<String> group : groups) {
      for (String string : group.getContent()) {
        int length = string.length();
        if (length > inputLength) {
          length = inputLength;
        }

        for (int i = 0; i < length; i++) {
          if (string.charAt(i) != input.charAt(i)) {
            break;
          }

          int distance = LevenshteinDistance.compute(string, input);
          if (distance <= maxDistance) {
            match = group;
            matchLength = i;
            break;
          }

          if (i > matchLength) {
            match = group;
            matchLength = i;
          }
        }
      }
    }

    if (match == null) {
      match = new DegreeGroup<>(new String[]{input});
      groups.add(match);
    } else {
      String[] content = match.getContent();
      String[] newContent = new String[content.length + 1];
      System.arraycopy(content, 0, newContent, 0, content.length);
      newContent[content.length] = input;
      match = new DegreeGroup<>(newContent);
    }
    return match;
  }

  @Override
  public DegreeGroup<String> findClosestCluster(String s) {
    String[] matches = new String[groups.size()];
    int[] matchLengths = new int[groups.size()];
    int index = 0;
    for (DegreeGroup<String> group : groups) {
      for (String string : group.getContent()) {
        int length = string.length();
        if (length > s.length()) {
          length = s.length();
        }

        for (int i = 0; i < length; i++) {
          if (string.charAt(i) != s.charAt(i)) {
            break;
          }

          int distance = LevenshteinDistance.compute(string, s);
          if (distance <= maxDistance) {
            matches[index] = string;
            matchLengths[index] = i;
            index++;
            break;
          }

          if (i > matchLengths[index]) {
            matches[index] = string;
            matchLengths[index] = i;
          }
        }
      }
    }

    int bestMatch = 0;
    for (int i = 0; i < matchLengths.length; i++) {
      if (matchLengths[i] > matchLengths[bestMatch]) {
        bestMatch = i;
      }
    }

    DegreeGroup<String> newGroup = new DegreeGroup<>(new String[]{matches[bestMatch]});
    for (String match : matches) {
      for (DegreeGroup<String> group : groups) {
        double similarity = group.similarity(match);
        if (similarity > similarityThreshold) {
          newGroup.addNode(match);
          break;
        }
      }
    }

    return newGroup;
  }

  @Override
  public List<DegreeGroup<String>> clusters() {
    return groups;
  }

  @Override
  public int size() {
    return groups.size();
  }

  @Override
  public void clean() {
    clusters().removeIf(group -> group.getHighestDegreeNode() == null || group.getHighestDegreeNode().isEmpty());
  }

  @Override
  public String serialize() {
    StringBuilder serialized = new StringBuilder();
    for (DegreeGroup<String> group : groups) {
      serialized.append(group).append("\n");
    }
    return serialized.toString();
  }

  @Override
  public Iterator<String> iterator() {
    return new Iterator<String>() {
      @Override
      public boolean hasNext() {
        return groups.iterator().hasNext();
      }

      @Override @Nullable
      public String next() {
        return groups.iterator()
          .next()
          .getHighestDegreeNode();
      }
    };
  }
}
