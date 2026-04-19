package neupokoev.com.lockservice.controller;

import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.controller.payload.KeyLockPayload;
import neupokoev.com.lockservice.service.KeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lock-service-api/app/")
@RequiredArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @GetMapping("check")
    public ResponseEntity<Boolean> checkIsKeyForLock(@RequestParam("lockName") String lockName,
                                     @RequestParam("keyUid") String uid) {
        boolean result = keyService.isKeyForLock(lockName, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("checkMasterKey")
    public ResponseEntity<Boolean> checkIsMasterKeyForLock(@RequestParam("lockName") String lockName,
                                           @RequestParam("keyUid") String uid) {
        boolean result = keyService.isMasterKeyForLock(lockName, uid);
        return ResponseEntity.ok(result);
    }

    @PostMapping("addKeyToLock")
    public ResponseEntity<Void> addKeyToLock(@RequestBody KeyLockPayload keyLockPayload) {
        keyService.addKeyToLock(keyLockPayload.lockName(), keyLockPayload.keyUid());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("deleteKeyFromLock")
    public ResponseEntity<Void> deleteKeyFromLock(@RequestBody KeyLockPayload keyLockPayload) {
        keyService.deleteKeyFromLock(keyLockPayload.lockName(), keyLockPayload.keyUid());
        return ResponseEntity.noContent().build();
    }
}
