package agh.iot.waterit.model.dao;

import agh.iot.waterit.model.jpa.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    Data findFirstByDeviceIdOrderByDateOfMeasurementDesc(Long deviceId);
}
