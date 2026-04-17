package neupokoev.com.lockservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import neupokoev.com.lockservice.entity.Key;
import neupokoev.com.lockservice.entity.Lock;
import neupokoev.com.lockservice.entity.TypeKey;
import neupokoev.com.lockservice.repository.KeyRepository;
import neupokoev.com.lockservice.service.KeyGroupService;
import neupokoev.com.lockservice.service.KeyService;
import neupokoev.com.lockservice.service.LockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeyServiceImpl implements KeyService {

    private final KeyRepository keyRepository;
    private final LockService lockService;
    private final KeyGroupService keyGroupService;

    @Override
    public boolean isKeyForLock(String name, String uid) {
        Lock lock = lockService.getLockByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Lock not found"));

        Key key = keyRepository.findByUid(uid)
                .orElseThrow(() -> new NullPointerException("Key not  found"));

        return keyGroupService.existsByKeyAndGroup(
                key,
                lock.getGroup()
        );
    }

    @Override
    public boolean isMasterKeyForLock(String name, String uid) {
        Key key = keyRepository.findByUid(uid)
                .orElseThrow(() -> new NullPointerException("Key not  found"));

        return key.getType().equals(TypeKey.MASTER) && isKeyForLock(name, uid);
    }

    @Override
    public Optional<Key> getKey(int keyId) {
        return keyRepository.findById(keyId);
    }

    @Transactional
    @Override
    public void addKeyToLock(String nameLock, String uid) {
        Key key = keyRepository.findByUid(uid)
                .orElseGet(() -> keyRepository.save(
                        Key.builder()
                                .uid(uid)
                                .type(TypeKey.COMMON)
                                .build()));

        Lock lock = lockService.getLockByName(nameLock)
                .orElseThrow(() -> new EntityNotFoundException("Lock not found"));

        if (!keyGroupService.existsByKeyAndGroup(key, lock.getGroup())) {
            keyGroupService.addKeyToGroup(lock.getGroup(), key);
        }
    }

    @Transactional
    @Override
    public void deleteKeyFromLock(String nameLock, String uid) {
        Key key = keyRepository.findByUid(uid)
                .orElseThrow(() -> new NullPointerException("Key not  found"));

        Lock lock = lockService.getLockByName(nameLock)
                .orElseThrow(() -> new EntityNotFoundException("Lock not found"));

        keyGroupService.deleteKeyFromGroup(lock.getGroup(), key);
    }
}
