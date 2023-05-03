package gg.acai.aurora.sentiment;

import com.google.gson.Gson;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.Tokenizer;
import gg.acai.aurora.model.Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @author Clouke
 * @since 03.05.2023 13:01
 * © Aurora - All Rights Reserved
 */
public class SentimentAnalyzerModel implements SentimentAnalyzer, Model {

  private final Tokenizer tokenizer;
  private String name = "sentiment-analyzer-" + Aurora.version();
  private Map<String, Integer> scores;

  private transient String saveDirectory;
  private transient boolean loaded;

  public SentimentAnalyzerModel(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  @Override
  public void load(String path) {
    Gson gson = GsonSpec.standard();
    try {
      SentimentAnalyzerModel model = gson.fromJson(new FileReader(path), SentimentAnalyzerModel.class);
      this.scores = model.scores;
      this.saveDirectory = model.saveDirectory;
      this.name = model.name;
      this.loaded = true;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public double analyze(String text) {
    if (!loaded) {
      throw new IllegalStateException("Model not loaded");
    }
    double score = 0.0;
    List<String> words = tokenizer.tokenize(text);
    for (String word : words) {
      if (scores.containsKey(word)) {
        score += scores.get(word);
      }
    }
    return score;
  }

  @Override
  public void fit(Map<String, Integer> scores) {
    if (this.scores == null) {
      this.scores = scores;
    } else {
      this.scores.putAll(scores);
    }
    loaded = true;
  }

  @Override
  public Sentiment type(String text) {
    double score = analyze(text);
    if (score > 0.0) {
      return Sentiment.POSITIVE;
    } else if (score < 0.0) {
      return Sentiment.NEGATIVE;
    } else {
      return Sentiment.NEUTRAL;
    }
  }

  @Override
  public Tokenizer tokenizer() {
    return tokenizer;
  }

  @Override
  public Model name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    return this;
  }

  @Override
  public String version() {
    return Aurora.version();
  }

  @Override
  public String saveDirectoryPath() {
    return saveDirectory;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void close() {
    save(saveDirectory);
  }

  @Override
  public String serialize() {
    Gson gson = GsonSpec.standard();
    return gson.toJson(this);
  }
}