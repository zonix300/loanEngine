package com.example.loanEngine.entity;

/**
 * Borrower
 */
public class Borrower {

  private String personalCode;
  private boolean hasDebt;
  private Segment segment;

  public Borrower(Builder builder) {
    this.personalCode = builder.personalCode;
    this.hasDebt = builder.hasDebt;
    this.segment = builder.segment;
  }

  public String getPersonalCode() {
    return personalCode;
  }

  public void setPersonalCode(String personalCode) {
    this.personalCode = personalCode;
  }

  public boolean isHasDebt() {
    return hasDebt;
  }

  public void setHasDebt(boolean hasDebt) {
    this.hasDebt = hasDebt;
  }

  public Segment getSegment() {
    return segment;
  }

  public void setSegment(Segment segment) {
    this.segment = segment;
  }

  public static class Builder {
    private String personalCode = null;
    private boolean hasDebt = false;
    private Segment segment = new Segment(null);

    public static Builder getInstance() {
      return new Builder();
    }

    public Builder personalCode(String personalCode) {
      this.personalCode = personalCode;
      return this;
    }

    public Builder hasDebt(boolean hasDebt) {
      this.hasDebt = hasDebt;
      return this;
    }

    public Builder segment(Segment segment) {
      this.segment = segment;
      return this;
    }

    public Borrower build() {
      return new Borrower(this);
    }

  }

}
