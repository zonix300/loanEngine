import { useState } from "react";

export interface LoanRequestPayload {
  personalCode: string;
  loanAmount: number;
  loanPeriod: number;
}

export interface LoanResponse {
  status: "APPROVED" | "REJECTED";
  loanAmount: number;
  loanPeriod: number;
  cause: "NO_CAUSE" | "NO_MONEY" | "NO_PERIOD";
}

export interface LoanRequestResult {
  success: boolean;
  data?: LoanResponse;
  error?: string;
}

export function useLoanRequest() {
  const [isLoading, setIsLoading] = useState(false);
  const [result, setResult] = useState<LoanRequestResult | null>(null);

  const submitLoanRequest = async (payload: LoanRequestPayload) => {
    setIsLoading(true);
    setResult(null);

    try {
      const response = await fetch("http://localhost:8080/api/v1/loan", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }

      const data: LoanResponse = await response.json();
      setResult({ success: true, data });
    } catch (err) {
      setResult({
        success: false,
        error: err instanceof Error ? err.message : "Something went wrong",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return { submitLoanRequest, isLoading, result };
}
