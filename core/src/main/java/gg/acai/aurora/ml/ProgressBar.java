package gg.acai.aurora.ml;

/**
 * @author Clouke
 * @since 02.04.2023 22:49
 * © Aurora - All Rights Reserved
 */
public abstract class ProgressBar {

  public abstract char getProgressChar();

  public abstract char getEmptyChar();

  public boolean useSideBars() {
    return false;
  }

  public static ProgressBar ASCII = new ProgressBar() {
    @Override
    public char getProgressChar() {
      return '█';
    }

    @Override
    public char getEmptyChar() {
      return '░';
    }
  };

  public static ProgressBar SIMPLE = new ProgressBar() {
    @Override
    public char getProgressChar() {
      return '#';
    }

    @Override
    public char getEmptyChar() {
      return '-';
    }

    @Override
    public boolean useSideBars() {
      return true;
    }
  };

}
