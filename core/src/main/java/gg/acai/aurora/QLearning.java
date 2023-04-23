package gg.acai.aurora;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Clouke
 * @since 21.04.2023 13:58
 * © Aurora - All Rights Reserved
 */
public class QLearning {

  private final double[][] Q; // Q-Table
  private final double learningRate; // Learning Rate
  private final double discountFactor; // Discount Factor
  private final int[] stateAction; // State-Action pair
  private final Random rand;

  public QLearning(int states, int actions, double learningRate, double discountFactor) {
    this.Q = new double[states][actions];
    this.learningRate = learningRate;
    this.discountFactor = discountFactor;
    this.stateAction = new int[2];
    this.rand = new Random();
  }

  public int selectAction(int state) {
    int action = 0;
    double maxVal = -Double.MAX_VALUE;

    for (int i = 0; i < Q[state].length; i++) {
      if (Q[state][i] > maxVal) {
        action = i;
        maxVal = Q[state][i];
      }
    }
    return action;
  }

  public void updateQ(int state, int action, double reward, int nextState) {
    double maxVal = -Double.MAX_VALUE;

    // Find maximum Q value in next state
    for (int i = 0; i < Q[nextState].length; i++) {
      if (Q[nextState][i] > maxVal) {
        maxVal = Q[nextState][i];
      }
    }

    // Q-Learning update
    Q[state][action] += learningRate * (reward + discountFactor * maxVal - Q[state][action]);
  }

  public int[] getStateAction() {
    return stateAction;
  }

  public void setStateAction(int state, int action) {
    stateAction[0] = state;
    stateAction[1] = action;
  }

  public int exploreAction(int state) {
    return rand.nextInt(Q[state].length);
  }

  public double[][] Q() {
    return Q;
  }

}

