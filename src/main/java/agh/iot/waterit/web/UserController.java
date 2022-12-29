package agh.iot.waterit.web;

import agh.iot.waterit.model.dto.UserDto;
import agh.iot.waterit.service.UserService;
import agh.iot.waterit.utils.UriBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;
    private final UriBuilder uriBuilder;

    @PostMapping("register")
    public ResponseEntity<Void> createUser(@RequestBody UserDto user) throws Exception {
        var id = userService.create(user);
        var locationUri = uriBuilder.requestUriWithId(id);
        return ResponseEntity.created(locationUri).build();
    }
}
