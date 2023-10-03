package gg.acai.aurora.sentiment;

import gg.acai.aurora.token.Tokenizer;
import gg.acai.opt.OptWeighting;
import gg.acai.opt.OptWeightingBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A sentiment analysis model built on top of the {@link OptWeighting} model.
 *
 * @author Clouke
 * @since 05.07.2023 20:10
 * © Aurora - All Rights Reserved
 */
public class OptSentiment extends OptWeighting {

  private final Tokenizer tokenizer;

  public OptSentiment(OptWeightingBuilder builder, Tokenizer tokenizer) {
    super(builder);
    this.tokenizer = tokenizer;
  }

  public SentimentResult predictSentiment(String... words) {
    Map<Sentiment, Double> probabilities = predictSentimentProbabilities(words);
    double confidence = probabilities.values()
      .stream()
      .mapToDouble(Double::doubleValue)
      .sum() / probabilities.size();
    return new SentimentResult(probabilities, confidence);
  }

  public double predictSingle(String... words) {
    double sum = 0;
    int n = 0;
    for (String word : words) {
      double tokenId = tokenizer.indexOf(word);
      if (weights.containsKey(tokenId)) {
        sum += activation.apply(weights.get(tokenId));
        n++;
      }
    }
    return sum / n;
  }

  public void train(Map<String, Integer> samples, int epochs) {
    Map<Double, Integer> transformations = new HashMap<>();
    for (Map.Entry<String, Integer> sample : samples.entrySet()) {
      String sequence = sample.getKey();
      tokenizer.fit(Collections.singletonList(sequence));
      double tokenId = tokenizer.indexOf(sequence);
      transformations.put(tokenId, sample.getValue());
    }

    fit(transformations, epochs);
  }

  public double predictSentimentProbability(Sentiment sentiment, String... words) {
    double prediction = predictSingle(words);
    switch (sentiment) {
      case POSITIVE: return prediction;
      case NEGATIVE: return 1.0 - prediction;
      case NEUTRAL: return 1.0 - Math.abs(prediction - 0.5);
      default: return 0.0;
    }
  }

  public Map<Sentiment, Double> predictSentimentProbabilities(String... words) {
    Map<Sentiment, Double> probabilities = new HashMap<>();
    Arrays.stream(Sentiment.values())
      .forEach(sentiment ->
        probabilities.put(
          sentiment,
          predictSentimentProbability(sentiment, words)
        ));
    return probabilities;
  }

}
