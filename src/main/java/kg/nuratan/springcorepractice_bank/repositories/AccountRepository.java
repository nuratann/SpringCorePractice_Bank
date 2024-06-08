package kg.nuratan.springcorepractice_bank.repositories;

import jakarta.persistence.Table;
import kg.nuratan.springcorepractice_bank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
