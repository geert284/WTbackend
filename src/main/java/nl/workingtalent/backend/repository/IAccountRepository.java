package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {

}
