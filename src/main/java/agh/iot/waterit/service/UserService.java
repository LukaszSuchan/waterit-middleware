package agh.iot.waterit.service;

import agh.iot.waterit.model.dao.UserRepository;
import agh.iot.waterit.model.dto.UserDto;
import agh.iot.waterit.model.jpa.User;
import agh.iot.waterit.utils.CryptoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Long create(UserDto request) throws Exception {
        final var encryptedPassword = CryptoUtils.encrypt(request.password());
        final var user = User.builder()
                .email(request.email())
                .password(encryptedPassword)
                .name(request.name())
                .build();
        var savedUser = repository.save(user);

        return savedUser.getId();
    }

//    public UserDto getUserById(Long id) {
//        final var user = repository.findById(id).get();
//    }
}
