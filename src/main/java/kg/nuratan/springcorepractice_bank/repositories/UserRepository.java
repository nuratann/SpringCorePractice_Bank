package kg.nuratan.springcorepractice_bank.repositories;

import jakarta.persistence.Table;
import kg.nuratan.springcorepractice_bank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
