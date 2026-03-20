package com.example.loanEngine.builder;

import com.example.loanEngine.dto.ApproveLoanResponse;
import com.example.loanEngine.dto.ApproveLoanStatus;
import com.example.loanEngine.dto.RejectLoanCause;

/**
 * LoanDecisionResponseBuilder
 */
public class LoanDecisionResponseBuilder {
  private ApproveLoanStatus status = ApproveLoanStatus.NO_DECISION;
  private Integer loanAmount = 0;
  private Integer loanPeriod = 0;
  private RejectLoanCause cause = RejectLoanCause.NO_CAUSE;

  private LoanDecisionResponseBuilder() {
  }

  public static LoanDecisionResponseBuilder anApproveLoanResponse() {
    return new LoanDecisionResponseBuilder();
  }

  public LoanDecisionResponseBuilder approve() {
    this.status = ApproveLoanStatus.APPROVED;
    return this;
  }

  public LoanDecisionResponseBuilder reject() {
    this.status = ApproveLoanStatus.REJECTED;
    return this;
  }

  public LoanDecisionResponseBuilder withStatus(ApproveLoanStatus status) {
    this.status = status;
    return this;
  }

  public LoanDecisionResponseBuilder withLoanAmount(Integer loanAmount) {
    this.loanAmount = loanAmount;
    return this;
  }

  public LoanDecisionResponseBuilder withLoanPeriod(Integer loanPeriod) {
    this.loanPeriod = loanPeriod;
    return this;
  }

  public LoanDecisionResponseBuilder withCause(RejectLoanCause cause) {
    this.cause = cause;
    return this;
  }

  public ApproveLoanResponse build() {
    return new ApproveLoanResponse(status, loanAmount, loanPeriod, cause);
  }
}
