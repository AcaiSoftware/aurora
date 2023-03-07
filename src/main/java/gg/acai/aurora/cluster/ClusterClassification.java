package gg.acai.aurora.cluster;

import gg.acai.aurora.ml.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Clouke
 * @since 07.03.2023 04:03
 * © Aurora - All Rights Reserved
 *
 * Cluster Classification:
 * 1. Start with clustering inputs into groups
 * 2. Group similar inputs together
 * 3. Generate a classification for each group
 */
public class ClusterClassification implements QRCluster<String> {

  private final List<Group<String>> groups;

  public ClusterClassification() {
    this.groups = new ArrayList<>();
  }

  @Override
  public Group<String> cluster(String input) {
    Group<String> match = null;
    int stringLength = input.length();
    int matchLength = 0;
    for (Group<String> group : groups) {
      for (String string : group.getContent()) {
        int length = string.length();
        if (length > stringLength) {
          length = stringLength;
        }

        for (int i = 0; i < length; i++) {
          if (string.charAt(i) != input.charAt(i)) {
            break;
          }

          int distance = LevenshteinDistance.compute(string, input);
          if (distance <= 2) {
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
      match = new Group<>(new String[]{input});
      groups.add(match);
    } else {
      String[] content = match.getContent();
      String[] newContent = new String[content.length + 1];
      System.arraycopy(content, 0, newContent, 0, content.length);
      newContent[content.length] = input;
      match = new Group<>(newContent);
    }
    return match;
  }

  @Override
  public Group<String> findClosestCluster(String s) {
    String[] matches = new String[groups.size()];
    int[] matchLengths = new int[groups.size()];
    int index = 0;
    for (Group<String> group : groups) {
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
          if (distance <= 2) {
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

    // find the absolute best match
    int bestMatch = 0;
    for (int i = 0; i < matchLengths.length; i++) {
      if (matchLengths[i] > matchLengths[bestMatch]) {
        bestMatch = i;
      }
    }

    // find the group that contains the best match
    Group<String> newGroup = new Group<>(new String[]{matches[bestMatch]});
    for (String match : matches) {
      for (Group<String> group : groups) {
        double similarity = group.similarity(match);
        if (similarity > 0.8) {
          newGroup.addNode(match);
          break;
        }
      }
    }

    return newGroup;
  }

  @Override
  public List<Group<String>> clusters() {
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
    for (Group<String> group : groups) {
      serialized.append(group);
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

      @Override
      public String next() {
        return groups.iterator().next().getHighestDegreeNode();
      }
    };
  }
}
