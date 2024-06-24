package com.microfinance.loans_services.loansRepository;

import com.microfinance.loans_services.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {


}
