package com.elkard.tripmanager.services;

import com.elkard.tripmanager.dto.requests.BaseRequest;
import com.elkard.tripmanager.exceptions.UnexpectedException;
import com.elkard.tripmanager.model.entities.User;
import com.elkard.tripmanager.model.dao.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User getUserByDeviceId(String deviceId)
    {
        List<User> users = userRepository.findByDeviceId(deviceId);
        if (users.size() != 1)
        {
            throw new UnexpectedException("Got " + users.size() + " users but expected 1. DeviceId=" + deviceId);
        }

        return users.get(0);
    }

    public User getUserFromRequest(BaseRequest request)
    {
        return getUserByDeviceId(request.getDeviceId());
    }

    public User getOrCreateUserByDeviceId(String deviceId)
    {
        List<User> users = userRepository.findByDeviceId(deviceId);

        if (users.size() == 1)
            return users.get(0);

        if (users.size() == 0)
        {
            log.info("Creating new user for deviceId=" + deviceId);

            User user = new User();
            user.setDeviceId(deviceId);
            userRepository.save(user);
            return user;
        }

        throw new UnexpectedException("Got " + users.size() + " users but expected 0 or 1");
    }

    public User getOrCreateUserFromRequest(BaseRequest request)
    {
        return getOrCreateUserByDeviceId(request.getDeviceId());
    }
}
