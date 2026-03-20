"use client";

import { useState } from "react";
import {
  DollarSign,
  Calendar,
  Send,
  CheckCircle,
  XCircle,
  Hash,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { useLoanRequest } from "./hooks/useLoanRequest";

export default function Home() {
  const [amount, setAmount] = useState("");
  const [period, setPeriod] = useState("");
  const [personalCode, setPersonalCode] = useState("");
  const [submittedAmount, setSubmittedAmount] = useState<number | null>(null);
  const [submittedPeriod, setSubmittedPeriod] = useState<number | null>(null);
  const { submitLoanRequest, isLoading, result } = useLoanRequest();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const parsedAmount = parseFloat(amount);
    const parsedPeriod = parseInt(period, 10);
    setSubmittedAmount(parsedAmount);
    setSubmittedPeriod(parsedPeriod);
    await submitLoanRequest({
      loanAmount: parsedAmount,
      loanPeriod: parsedPeriod,
      personalCode,
    });
  };

  const isValid =
    amount !== "" &&
    period !== "" &&
    personalCode.trim() !== "" &&
    parseFloat(amount) > 0 &&
    parseInt(period) > 0;

  const loan = result?.data;
  const isApproved = loan?.status === "APPROVED";
  const amountChanged = loan && parseFloat(amount) !== loan.loanAmount;
  const periodChanged = loan && parseInt(period) !== loan.loanPeriod;
  const isPeriodOverflow = loan?.cause === "NO_PERIOD";

  return (
    <main className="min-h-screen bg-muted/40 flex items-center justify-center p-4">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader>
          <CardTitle className="text-2xl">Loan Request</CardTitle>
          <CardDescription>
            Fill in the details below to submit your loan application.
          </CardDescription>
        </CardHeader>

        <form onSubmit={handleSubmit}>
          <CardContent className="space-y-5">
            {/* Personal Code */}
            <div className="space-y-2">
              <Label htmlFor="personalCode">Personal Code</Label>
              <div className="relative">
                <Hash className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <Input
                  id="personalCode"
                  type="text"
                  placeholder="e.g. 39001010000"
                  className="pl-9"
                  value={personalCode}
                  onChange={(e) => setPersonalCode(e.target.value)}
                  required
                />
              </div>
            </div>

            {/* Loan Amount */}
            <div className="space-y-2">
              <Label htmlFor="amount">Loan Amount</Label>
              <div className="relative">
                <DollarSign className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <Input
                  id="amount"
                  type="number"
                  min={2000}
                  max={10000}
                  step="0.01"
                  placeholder="e.g. 5000"
                  className="pl-9"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  required
                />
              </div>
            </div>

            {/* Loan Period */}
            <div className="space-y-2">
              <Label htmlFor="period">Loan Period (months)</Label>
              <div className="relative">
                <Calendar className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <Input
                  id="period"
                  type="number"
                  min={12}
                  max={60}
                  placeholder="e.g. 24"
                  className="pl-9"
                  value={period}
                  onChange={(e) => setPeriod(e.target.value)}
                  required
                />
              </div>
            </div>

            {/* Result feedback */}
            {result && !loan && (
              <Alert variant="destructive">
                <XCircle className="h-4 w-4" />
                <AlertTitle>Request Failed</AlertTitle>
                <AlertDescription>{result.error}</AlertDescription>
              </Alert>
            )}

            {loan && (
              <Alert variant={isApproved ? "default" : "destructive"}>
                {isApproved ? (
                  <CheckCircle className="h-4 w-4" />
                ) : (
                  <XCircle className="h-4 w-4" />
                )}
                <AlertTitle>
                  {isApproved ? "Application Approved" : "Application Rejected"}
                </AlertTitle>
                <AlertDescription className="space-y-1 mt-1">
                  {isApproved ? (
                    <>
                      <p>
                        Approved amount:{" "}
                        <span className="font-medium">
                          ${loan.loanAmount.toLocaleString()}
                        </span>
                        {amountChanged && (
                          <span className="text-muted-foreground">
                            {" "}
                            (you requested ${submittedAmount?.toLocaleString()})
                          </span>
                        )}
                      </p>
                      <p>
                        Approved period:{" "}
                        <span className="font-medium">
                          {loan.loanPeriod} months
                        </span>
                        {periodChanged && (
                          <span className="text-muted-foreground">
                            {" "}
                            (you requested {submittedPeriod} months)
                          </span>
                        )}
                      </p>
                    </>
                  ) : isPeriodOverflow ? (
                    <p>
                      The requested amount of{" "}
                      <span className="font-medium">
                        ${loan.loanAmount.toLocaleString()}
                      </span>{" "}
                      cannot be offered under any available loan period. Please
                      try a lower amount.
                    </p>
                  ) : (
                    <>
                      <p>
                        We could not approve your request as submitted. Here is
                        the best offer we can make:
                      </p>
                      <p>
                        Maximum amount:{" "}
                        <span className="font-medium">
                          ${loan.loanAmount.toLocaleString()}
                        </span>
                        {amountChanged && (
                          <span className="text-muted-foreground">
                            {" "}
                            (you requested ${submittedAmount?.toLocaleString()})
                          </span>
                        )}
                      </p>
                      <p>
                        Required period:{" "}
                        <span className="font-medium">
                          {loan.loanPeriod} months
                        </span>
                        {periodChanged && (
                          <span className="text-muted-foreground">
                            {" "}
                            (you requested {submittedPeriod} months)
                          </span>
                        )}
                      </p>
                    </>
                  )}
                </AlertDescription>
              </Alert>
            )}
          </CardContent>

          <CardFooter>
            <Button
              type="submit"
              className="w-full gap-2"
              disabled={!isValid || isLoading}
            >
              <Send className="h-4 w-4" />
              {isLoading ? "Submitting…" : "Submit Application"}
            </Button>
          </CardFooter>
        </form>
      </Card>
    </main>
  );
}
