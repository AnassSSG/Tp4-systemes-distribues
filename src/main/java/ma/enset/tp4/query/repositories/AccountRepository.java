package ma.enset.tp4.query.repositories;

import ma.enset.tp4.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
