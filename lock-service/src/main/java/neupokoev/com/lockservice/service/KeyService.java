package neupokoev.com.lockservice.service;

import neupokoev.com.lockservice.entity.Key;

import java.util.Optional;

public interface KeyService {

    boolean isKeyForLock(String name, String uid);

    boolean isMasterKeyForLock(String name, String uid);

    Optional<Key> getKey(int keyId);

    void addKeyToLock(String nameLock, String uid);

    void deleteKeyFromLock(String nameLock, String uid);
}
