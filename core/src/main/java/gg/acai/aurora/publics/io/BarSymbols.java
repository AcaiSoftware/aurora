package gg.acai.aurora.publics.io;

/**
 * @author Clouke
 * @since 25.04.2023 07:29
 * © Aurora - All Rights Reserved
 */
public class BarSymbols {

  public static final BarSymbols MODERN = of('█', '░');
  public static final BarSymbols CLASSIC = of('#', '=');
  public static final BarSymbols WAVE = of('~', '-');
  public static final BarSymbols TAB = of('▌', ' ');
  public static final BarSymbols MODERN_TAB = of('█', ' ');
  public static final BarSymbols BRAILLE = of('⣿', '⣀');

  public static BarSymbols of(char complete, char incomplete) {
    return new BarSymbols(complete, incomplete);
  }

  private final char complete;
  private final char incomplete;

  public BarSymbols(char complete, char incomplete) {
    this.complete = complete;
    this.incomplete = incomplete;
  }

  public char complete() {
    return complete;
  }

  public char incomplete() {
    return incomplete;
  }

}
