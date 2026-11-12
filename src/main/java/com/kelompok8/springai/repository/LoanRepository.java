package com.kelompok8.springai.repository;
import com.kelompok8.springai.model.*;
import org.springframework.data.jpa.repository.*;
import java.util.List;
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByOrderByLoanDateDesc();
    long countByStatus(LoanStatus status);
}
