package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.controller.payload.KeyLockPayload;
import neupokoev.com.lockservice.service.KeyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lock-service-api/app/")
@RequiredArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @GetMapping("check")
    public boolean checkIsKeyForLock(@RequestParam("lockName") String name,
                                     @RequestParam("keyUid") String uid) {
        return keyService.isKeyForLock(name, uid);
    }

    @GetMapping("checkMasterKey")
    public boolean checkIsMasterKeyForLock(@RequestParam("lockName") String name,
                                           @RequestParam("keyUid") String uid) {
        return keyService.isMasterKeyForLock(name, uid);
    }

    @PostMapping("addKeyToLock")
    public void addKeyToLock(@RequestBody KeyLockPayload keyLockPayload) {
        keyService.addKeyToLock(keyLockPayload.lockName(), keyLockPayload.keyUid());
    }

    @DeleteMapping("deleteKeyFromLock")
    public void deleteKeyFromLock(@RequestBody KeyLockPayload keyLockPayload) {
        keyService.deleteKeyFromLock(keyLockPayload.lockName(), keyLockPayload.keyUid());
    }
}
