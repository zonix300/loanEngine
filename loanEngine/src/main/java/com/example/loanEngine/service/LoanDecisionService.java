package com.example.loanEngine.service;

import org.springframework.stereotype.Service;

import com.example.loanEngine.builder.LoanDecisionResponseBuilder;
import com.example.loanEngine.dto.ApproveLoanResponse;
import com.example.loanEngine.dto.LoanApplicationRequest;
import com.example.loanEngine.dto.RejectLoanCause;
import com.example.loanEngine.entity.Borrower;
import com.example.loanEngine.exception.BorrowerNotFoundException;
import com.example.loanEngine.registry.BorrowerMockRegistry;

/**
 * LoanDecisionService
 */
@Service
public class LoanDecisionService {

  private final BorrowerMockRegistry borrowerMockRegistry;

  public LoanDecisionService(BorrowerMockRegistry borrowerMockRegistry) {
    this.borrowerMockRegistry = borrowerMockRegistry;
  }

  public ApproveLoanResponse calculateLoan(LoanApplicationRequest request) {
    LoanDecisionResponseBuilder builder = LoanDecisionResponseBuilder.anApproveLoanResponse();
    System.out.println(request);

    Borrower borrower = borrowerMockRegistry.findBorrowerByPersonalCode(request.personalCode())
        .orElseThrow(
            () -> new BorrowerNotFoundException("Borrower not found wiht personal code: " + request.personalCode()));

    if (borrower.isHasDebt()) {
      return builder.reject().withCause(RejectLoanCause.HAS_DEBT).build();
    }
    int creditModifier = borrower.getSegment().getCreditModifier();

    int maxLoan = request.loanPeriod() * creditModifier;
    maxLoan = Math.min(maxLoan, 10000);
    if (maxLoan < request.loanAmount()) {
      int minPeriod = (int) Math.ceil((double) request.loanAmount() / creditModifier);
      if (minPeriod > 60) {
        return builder.reject().withLoanAmount(request.loanAmount()).withLoanPeriod(0)
            .withCause(RejectLoanCause.NO_PERIOD).build();
      }
      minPeriod = Math.max(minPeriod, 12);
      maxLoan = Math.max(maxLoan, 2000);
      return builder.reject().withLoanAmount(maxLoan).withLoanPeriod(minPeriod).withCause(RejectLoanCause.NO_MONEY)
          .build();
    }

    return builder.approve().withLoanAmount(maxLoan).withLoanPeriod(request.loanPeriod()).build();
  }
}
