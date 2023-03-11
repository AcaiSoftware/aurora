package gg.acai.aurora.ml.lang;

import gg.acai.acava.io.Callback;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Clouke
 * @since 06.03.2023 00:03
 * © Aurora - All Rights Reserved
 */
public class EventStreamResponse {

  private final Callback<String> reader;
  private final long latency;

  public EventStreamResponse(Callback<String> reader, long latency) {
    this.reader = reader;
    this.latency = latency;
  }

  public EventStreamResponse(Callback<String> write) {
    this(write, 1L);
  }

  public void write(String response) {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      String current = "";
      @Override
      public void run() {
        if (current.length() == response.length()) {
          timer.cancel();
          return;
        }
        current += response.charAt(current.length());
        reader.onCallback(current);
      }
    }, 0L, latency);
  }

}
