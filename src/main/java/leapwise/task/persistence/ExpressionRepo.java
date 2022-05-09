package leapwise.task.persistence;

import leapwise.task.persistence.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpressionRepo extends JpaRepository<Expression, Integer> {

}