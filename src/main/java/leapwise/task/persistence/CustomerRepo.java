package leapwise.task.persistence;

import leapwise.task.persistence.model.RootNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<RootNode, Integer> {

}
