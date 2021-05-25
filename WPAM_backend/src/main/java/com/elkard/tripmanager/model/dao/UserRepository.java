package com.elkard.tripmanager.model.dao;

import com.elkard.tripmanager.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByDeviceId(String deviceId);
}
