package agh.iot.waterit.model.dao;

import agh.iot.waterit.model.jpa.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByName(String name);

    List<Device> findAllByName(String name);

    Device findFirstByNameOrderById(String name);
}
