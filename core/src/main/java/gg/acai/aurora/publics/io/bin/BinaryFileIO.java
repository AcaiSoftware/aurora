package gg.acai.aurora.publics.io.bin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Clouke
 * @since 01.07.2023 14:15
 * © Aurora - All Rights Reserved
 */
public class BinaryFileIO {

  public static void saveAsBin(BinaryWrapper wrapper, String pathToBin) throws IOException {
    try (OutputStream outputStream = Files.newOutputStream(Paths.get(pathToBin));
         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {

      byte[] compressedData = compressString(wrapper.composed());
      objectOutputStream.writeObject(compressedData);
    } catch (IOException e) {
      throw new IOException("Error occurred while saving the .model file: " + e.getMessage());
    }
  }

  public static String loadFromBin(String pathToBin) throws IOException {
    try (InputStream inputStream = Files.newInputStream(Paths.get(pathToBin));
         BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
         ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {

      byte[] compressedData = (byte[]) objectInputStream.readObject();

      return decompressString(compressedData);
    } catch (IOException | ClassNotFoundException e) {
      throw new IOException("Error occurred while loading the .model file: " + e.getMessage());
    }
  }

  private static byte[] compressString(String input) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
      gzipOutputStream.write(input.getBytes(StandardCharsets.UTF_8));
    }
    return outputStream.toByteArray();
  }

  private static String decompressString(byte[] input) throws IOException {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
    try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
         InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
      return stringBuilder.toString();
    }
  }
}
