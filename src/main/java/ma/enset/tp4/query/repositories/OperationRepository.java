package ma.enset.tp4.query.repositories;

import ma.enset.tp4.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
