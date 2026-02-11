package hairtist.repository;

import hairtist.model.TheOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<TheOrder, UUID> {

}
