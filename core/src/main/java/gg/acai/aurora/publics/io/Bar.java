package gg.acai.aurora.publics.io;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.io.Closeable;
import gg.acai.aurora.QRMath;
import org.apache.commons.lang.StringUtils;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * A configurable progress bar which is displayed during training.
 *
 * @author Clouke
 * @since 25.04.2023 07:00
 * © Aurora - All Rights Reserved
 */
public class Bar implements Closeable {

  public static final Bar MODERN = new Bar(BarSymbols.MODERN, 30);
  public static final Bar CLASSIC = new Bar(BarSymbols.CLASSIC, 30, false, true);
  private static final String[] CHARS = {"|", "╱", "—", "\\"};

  private final BarSymbols symbols;
  private final int length;
  private final boolean printNewLine;
  private final boolean sideBar;
  private final char[] bar;
  private int nextIndex, scaledIndex;

  public Bar(BarSymbols symbols, int length, boolean printNewLine, boolean sideBar) {
    this.symbols = symbols;
    this.length = (int) QRMath.clamp(length, 1, 100);
    this.printNewLine = printNewLine;
    this.sideBar = sideBar;
    this.bar = new char[length + 1];
    for (int i = 0; i <= length; i++)
      bar[i] = symbols.incomplete();
  }

  public Bar(BarSymbols symbols, int length) {
    this(symbols, length, false, false);
  }

  /**
   * Increments the progress bar by one tick.
   */
  public void tick() {
    if (scaledIndex > length) {
      return;
    }

    scaledIndex = nextIndex;
    if (length != 100) {
      scaledIndex = (nextIndex * length) / 100;
    }

    bar[scaledIndex] = symbols.complete();
    nextIndex++;
  }

  /**
   * Prints the progress bar with the given attributes.
   *
   * @param attributes The attributes to print.
   */
  public void print(Attributes attributes) {
    String bar = toString();
    if (attributes != null) {
      StringBuilder builder = new StringBuilder().append(bar);
      attributes.asMap().forEach((key, value) -> builder
        .append(" ")
        .append(StringUtils.capitalize(key))
        .append(": ")
        .append(value)
        .append(" |"));
      bar = builder.substring(0, builder.length() - 2);
    }

    PrintStream stream = System.out;
    if (printNewLine)
      stream.println(bar);
    else {
      stream.print("\r" + bar + " (" + CHARS[nextIndex % CHARS.length] + ")");
    }
  }

  public void print() {
    print(null);
  }

  @Override
  public String toString() {
    String bar = new String(this.bar);
    return sideBar ? "[" + bar + "]" : bar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bar other = (Bar) o;
    return nextIndex == other.nextIndex && Arrays.equals(bar, other.bar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(length, Arrays.hashCode(bar), nextIndex);
  }

  @Override
  public void close() {
    for (int i = 0; i <= length; i++)
      bar[i] = symbols.incomplete();
    nextIndex = 0;
  }

}
