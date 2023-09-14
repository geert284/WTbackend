package nl.workingtalent.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {

	// Vind alle accounts met die email adres
	Optional<Account> findByEmail(String email);
	
	// Tel aantal accounts met die email adres
	long countByEmail(String email);
	
	// Optional<Account> findByIdSortingDesc(long id);
}
