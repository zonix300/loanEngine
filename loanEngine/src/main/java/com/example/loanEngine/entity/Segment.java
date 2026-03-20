package com.example.loanEngine.entity;

/**
 * Segment
 */
public class Segment {

  private Integer creditModifier;

  public Segment(Integer creditModifier) {
    this.creditModifier = creditModifier;
  }

  public Integer getCreditModifier() {
    return creditModifier;
  }

  public void setCreditModifier(Integer creditModifier) {
    this.creditModifier = creditModifier;
  }

}
