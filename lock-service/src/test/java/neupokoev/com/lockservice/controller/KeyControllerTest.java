package neupokoev.com.lockservice.controller;

import neupokoev.com.lockservice.controller.payload.KeyLockPayload;
import neupokoev.com.lockservice.service.KeyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class KeyControllerTest {

    @Mock
    KeyService keyService;

    @InjectMocks
    KeyController keyController;

    @Test
    void addKeyToLock_RequestIsValid() {
        // given
        KeyLockPayload payload = new KeyLockPayload("Вход 1", "007");

        doNothing()
                .when(keyService)
                .addKeyToLock("Вход 1", "007");

        // when
        var result = keyController.addKeyToLock(payload);

        // then
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), result);

        verify(keyService).addKeyToLock("Вход 1", "007");;
        verifyNoMoreInteractions(keyService);
    }

    @Test
    void deleteKeyFromLock_RequestIsValid() {
        // given
        KeyLockPayload payload = new KeyLockPayload("Вход 1", "007");

        doNothing()
                .when(keyService)
                .deleteKeyFromLock("Вход 1", "007");

        // when
        var result = keyController.deleteKeyFromLock(payload);

        // then
        assertEquals(ResponseEntity.noContent().build(), result);

        verify(keyService).deleteKeyFromLock("Вход 1", "007");;
        verifyNoMoreInteractions(keyService);
    }

    @Test
    void checkIsKeyForLock_RequestIsValid() {
        // given
        var nameLock = "Вход 1";
        var uidKey = "007";

        doReturn(true)
                .when(keyService)
                .isKeyForLock("Вход 1", "007");

        // when
        var result = keyController.checkIsKeyForLock(nameLock, uidKey);

        // then
        assertTrue(result);

        verify(keyService).isKeyForLock("Вход 1", "007");
        verifyNoMoreInteractions(keyService);
    }

    @Test
    void checkIsMasterKeyForLock_RequestIsValid() {
        // given
        var nameLock = "Вход 1";
        var uidKey = "007";

        doReturn(true)
                .when(keyService)
                .isMasterKeyForLock("Вход 1", "007");

        // when
        var result = keyController.checkIsMasterKeyForLock(nameLock, uidKey);

        // then
        assertTrue(result);

        verify(keyService).isMasterKeyForLock("Вход 1", "007");
        verifyNoMoreInteractions(keyService);
    }
}