package hairtist.repository;

import hairtist.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExtraRepository extends JpaRepository<Extra, UUID> {

    Optional<Extra> findByName(String name);
}
