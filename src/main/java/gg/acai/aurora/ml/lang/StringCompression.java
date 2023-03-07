package gg.acai.aurora.ml.lang;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author Clouke
 * @since 06.03.2023 21:47
 * © Aurora - All Rights Reserved
 */
public class StringCompression {

  public static byte[] compress(String text) {
    byte[] input = text.getBytes();
    byte[] output = new byte[input.length];

    Deflater deflater = new Deflater();
    deflater.setInput(input);
    deflater.finish();
    int compressedSize = deflater.deflate(output);
    deflater.end();

    byte[] result = new byte[compressedSize];
    System.arraycopy(output, 0, result, 0, compressedSize);
    return result;
  }

  public static String decompress(byte[] compressedData) {
    byte[] output = new byte[1024];

    Inflater inflater = new Inflater();
    inflater.setInput(compressedData);

    int uncompressedSize;
    try {
      uncompressedSize = inflater.inflate(output);
    } catch (Exception e) {
      throw new RuntimeException("Failed to decompress data", e);
    }

    inflater.end();
    return new String(output, 0, uncompressedSize);
  }

  public static String decompress(String s) {
    byte[] bytes = new byte[s.length()];
    for (int i = 0; i < s.length(); i++) {
      bytes[i] = (byte) s.charAt(i);
    }
    return decompress(bytes);
  }

}

