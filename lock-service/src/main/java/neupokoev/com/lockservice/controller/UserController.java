package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.dto.UserReadDto;
import neupokoev.com.lockservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lock-service-api/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("registration")
    public String addUser(@RequestBody UserCreateDto userCreateDto) {
        UserReadDto userReadDto = userService.addUser(userCreateDto);
        return "Add user";
    }
}
