package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Loan;

public interface ILoanRepository extends JpaRepository<Loan, Long>{

}
