package agh.iot.waterit.model.dao;

import agh.iot.waterit.model.jpa.WifiSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiSettingsRepository extends JpaRepository<WifiSettings, Long> {
}
