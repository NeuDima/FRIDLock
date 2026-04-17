package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.dto.LockCreateDto;
import neupokoev.com.lockservice.dto.LockReadDto;
import neupokoev.com.lockservice.dto.UserCreateDto;
import neupokoev.com.lockservice.dto.UserReadDto;
import neupokoev.com.lockservice.service.LockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lock-service-api/lock/")
@RequiredArgsConstructor
public class LockController {

    private final LockService lockService;

    @PostMapping("registration")
    public String addUser(@RequestBody LockCreateDto lockCreateDto) {
        LockReadDto lockReadDto = lockService.addLock(lockCreateDto);
        return "Add lock";
    }
}
