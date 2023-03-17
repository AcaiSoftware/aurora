package gg.acai.aurora.ml.nlp;

/**
 * @author Clouke
 * @since 17.03.2023 01:31
 * © Aurora - All Rights Reserved
 */
public abstract class Filter {

  public abstract String apply(String input);

  public static final Filter SIMPLE = new Filter() {
    @Override
    public String apply(String input) {
      return input.toLowerCase()
        .replace("?","")
        .replace("!","")
        .replace(".","")
        .replace(",","");
    }
  };

  public static final Filter NUM = new Filter() {
    @Override
    public String apply(String input) {
      return input.replaceAll("\\d","");
    }
  };

  public static final Filter PUNC = new Filter() {
    @Override
    public String apply(String input) {
      return input.replaceAll("[^a-zA-Z\\d ]","");
    }
  };

  public static final Filter SPACE = new Filter() {
    @Override
    public String apply(String input) {
      return input.replaceAll("\\s+","");
    }
  };

  public static final Filter SPACE2 = new Filter() {
    @Override
    public String apply(String input) {
      return input.replaceAll("\\s{2,}","");
    }
  };

}
