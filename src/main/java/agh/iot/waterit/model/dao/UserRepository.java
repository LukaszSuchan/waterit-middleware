package agh.iot.waterit.model.dao;

import agh.iot.waterit.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
