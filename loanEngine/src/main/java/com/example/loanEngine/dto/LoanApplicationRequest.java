package com.example.loanEngine.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ApproveLoanRequest
 */
public record LoanApplicationRequest(
    @NotBlank(message = "Personal code is required") String personalCode,
    @NotNull(message = "Loan amount is required") @Min(value = 2000, message = "Minimum loan amount is 2000") @Max(value = 10000, message = "Maximum loan amount is 1000") Integer loanAmount,
    @NotNull(message = "Loan period is required") @Min(value = 12, message = "Minimum loan period is 12") @Max(value = 60, message = "Maximum loan period is 60") Integer loanPeriod) {
}
