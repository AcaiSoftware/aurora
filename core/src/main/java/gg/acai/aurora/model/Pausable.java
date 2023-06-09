package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 17.05.2023 01:09
 * © Aurora - All Rights Reserved
 */
public interface Pausable {

  void pause();

  void resume();

  boolean paused();

}
