package gg.acai.aurora.ml.nlp;

import gg.acai.acava.collect.Mutability;
import gg.acai.acava.commons.graph.Graph;
import gg.acai.aurora.ml.nn.NetworkBuilder;
import gg.acai.aurora.ml.nn.NeuralNetworkTrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Clouke
 * @since 15.03.2023 23:21
 * © Aurora - All Rights Reserved
 */
public class Chatbot {

  private final Map<String, List<String>> keywordResponses;
  private final WordVectorizer vectorizer;
  private final Graph<Integer> graph;
  private final Filter filter = Filter.SIMPLE;
  private NeuralNetworkTrainer network;

  public Chatbot(Map<String, List<String>> keywordResponses, WordVectorizer vectorizer) {
    this.keywordResponses = keywordResponses;
    this.vectorizer = vectorizer;
    List<String> sentences = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : keywordResponses.entrySet()) {
      sentences.add(filter.apply(entry.getKey()));
      sentences.addAll(entry.getValue());
    }
    this.vectorizer.fit(sentences);
    this.graph = Graph.newBuilder()
            .setFixedSize(100)
            .setHeight(30)
            .setMutability(Mutability.MUTABLE)
            .build();
  }

  public void train() {
    double[][] inputs = new double[keywordResponses.size()][];
    double[][] outputs = new double[keywordResponses.size()][];
    int index = 0;
    for (Map.Entry<String, List<String>> entry : keywordResponses.entrySet()) {
      List<String> responses = entry.getValue();
      if (responses == null || responses.isEmpty()) {
        continue;
      }

      String input = filter.apply(entry.getKey());
      inputs[index] = vectorizer.transform(input);
      outputs[index] = vectorizer.transform(responses.get(0));
      index++;
    }
    int inputSize = vectorizer.size();
    int outputSize = vectorizer.size();
    //int hiddenSize = 40;
    int hiddenSize = 100;
    double learningRate = 0.1; // 0.1
    int epochs = 1000;

    network = NetworkBuilder.training()
            .inputLayerSize(inputSize)
            .hiddenLayerSize(hiddenSize)
            .outputLayerSize(outputSize)
            .learningRate(learningRate)
            .accuracyTest(new double[]{outputs[0][0]})
            .epochs(epochs)
            .build();

    network.train(inputs, outputs);

    // benchmark the network
    /*
    for (Map.Entry<String, List<String>> entry : keywordResponses.entrySet()) {
      String word = entry.getKey();
      List<String> responses = entry.getValue();
      if (word == null || word.isEmpty() || responses == null || responses.isEmpty()) {
        continue;
      }

      String actualResponse = responses.get(0);
      String aiResponse = generateResponse(word);

      int distance = LevenshteinDistance.compute(actualResponse, aiResponse);
      graph.addNode(distance);
    }
     */
  }

  public String generateResponse(String input) {
    input = filter.apply(input);

    double[] inputVector = vectorizer.transform(input);
    double[] outputVector = network.predict(inputVector);

    double bestSim = Double.MIN_VALUE;
    //String closestResponse = null; // TODO: Change to index to reply with a random response
    List<String> match = null;

    for (List<String> responses : keywordResponses.values()) {
      for (String response : responses) {
        double similarity = vectorizer.cosineSimilarity(outputVector, vectorizer.transform(response));
        if (similarity > bestSim) {
          bestSim = similarity;
          //closestResponse = response;
          match = responses;
        }
      }
    }

    if (match != null) {
      return match.get((int) (Math.random() * match.size()));
    }
    return "I don't know what to say!";
    //return closestResponse == null ? "I don't know what to say!" : closestResponse;
  }

  public void debug() {
    System.out.println("Keyword Responses: " + keywordResponses);
    System.out.println("Vectorizer Vocabulary: " + vectorizer.toString());
    System.out.println("Vectorizer Size: " + vectorizer.size());
    System.out.println("Network: " + network.save().serialize());
  }

  public Graph<Integer> benchmark() {
    return graph;
  }

  public void save() {
    network.saveAs("chatbot_xd").save();
  }

}
