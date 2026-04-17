package neupokoev.com.lockservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import neupokoev.com.lockservice.controller.payload.KeyLockPayload;
import neupokoev.com.lockservice.entity.Group;
import neupokoev.com.lockservice.entity.Key;
import neupokoev.com.lockservice.entity.Lock;
import neupokoev.com.lockservice.repository.GroupRepository;
import neupokoev.com.lockservice.repository.KeyGroupRepository;
import neupokoev.com.lockservice.repository.KeyRepository;
import neupokoev.com.lockservice.repository.LockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class KeyControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private GroupRepository groupRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private LockRepository lockRepository;

    @Autowired
    private KeyGroupRepository keyGroupRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddKeyToLock_whenKeyNotExists() throws Exception {
        // GIVEN
        String lockName = "front-door";
        String keyUid = "123-UID";

        Group group = new Group();
        group.setName("group1");
        groupRepository.save(group);

        Lock lock = new Lock();
        lock.setName(lockName);
        lock.setSecret("123");
        lock.setGroup(group);

        lockRepository.save(lock);

        KeyLockPayload payload = new KeyLockPayload(lockName, keyUid);

        // WHEN
        mockMvc.perform(post("/lock-service-api/app/addKeyToLock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        // THEN
        Key key = keyRepository.findByUid(keyUid).orElseThrow();

        boolean exists = keyGroupRepository.existsByKeyAndGroup(key, group);

        assertTrue(exists);
    }
}