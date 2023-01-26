package agh.iot.waterit.model.dao;

import agh.iot.waterit.model.jpa.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Optional<Confirmation> findFirstByNameOrderById(String name);
}
