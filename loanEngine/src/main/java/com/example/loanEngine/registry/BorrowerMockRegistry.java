package com.example.loanEngine.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.loanEngine.entity.Borrower;
import com.example.loanEngine.entity.Borrower.Builder;
import com.example.loanEngine.entity.Segment;

/**
 * BorrowerMockRegistry
 */
@Component
public class BorrowerMockRegistry {

  private static Map<String, Borrower> borrowers = new HashMap<>();

  static {
    Builder builder = Borrower.Builder.getInstance();
    Borrower borrower1 = builder.personalCode("1111").segment(new Segment(100)).build();
    Borrower borrower2 = builder.personalCode("2222").segment(new Segment(400)).build();
    Borrower borrower3 = builder.personalCode("3333").segment(new Segment(800)).build();
    Borrower borrower4 = builder.personalCode("4444").segment(new Segment(1000)).build();

    borrowers.put(borrower1.getPersonalCode(), borrower1);
    borrowers.put(borrower2.getPersonalCode(), borrower2);
    borrowers.put(borrower3.getPersonalCode(), borrower3);
    borrowers.put(borrower4.getPersonalCode(), borrower4);
  }

  public Optional<Borrower> findBorrowerByPersonalCode(String personalCode) {

    return Optional.ofNullable(borrowers.get(personalCode));
  }
}
