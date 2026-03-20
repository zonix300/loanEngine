package com.example.loanEngine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.loanEngine.dto.LoanApplicationRequest;
import com.example.loanEngine.service.LoanDecisionService;

/**
 * LoanDecisionController
 */
@Controller
@RequestMapping("/api/v1/loan")
public class LoanDecisionController {

  private final LoanDecisionService loanDecisionService;

  public LoanDecisionController(LoanDecisionService loanDecisionService) {
    this.loanDecisionService = loanDecisionService;
  }

  @GetMapping
  public ResponseEntity<?> test() {
    return ResponseEntity.ok().build();
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping
  public ResponseEntity<?> decideLoan(@RequestBody LoanApplicationRequest request) {

    return ResponseEntity.ok(loanDecisionService.calculateLoan(request));
  }

}
