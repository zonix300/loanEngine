package com.example.loanEngine.dto;

/**
 * ApproveLoanResponse
 */
public record ApproveLoanResponse(

    ApproveLoanStatus status,
    Integer loanAmount,
    Integer loanPeriod,
    RejectLoanCause cause) {
}
