package neupokoev.com.lockservice.controller;

import neupokoev.com.lockservice.controller.payload.KeyLockPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional  // нужен для отката бд к исходному состоянию
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc // можно добавить addFilters = false, чтобы отключить защиту
class KeyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Sql("/sql/keys.sql")
    @Test
    void addKeyToLock_RequestIsValid() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders
                .post("/lock-service-api/app/addKeyToLock")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"lockName": "Lock 2", "keyUid": "001"}
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Sql("/sql/keys.sql")
    @Test
    void deleteKeyFromLock_RequestIsValid() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders
                .delete("/lock-service-api/app/deleteKeyFromLock")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"lockName": "Lock 1", "keyUid": "001"}
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Sql("/sql/keys.sql")
    @Test
    void checkIsKeyForLock_RequestIsValid() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders
                .get("/lock-service-api/app/check")
                .with(jwt())
                .param("lockName", "Lock 1")
                .param("keyUid", "001");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().string("true")
                );
    }

    @Sql("/sql/keys.sql")
    @Test
    void checkIsMasterKeyForLock_RequestIsValid() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders
                .get("/lock-service-api/app/checkMasterKey")
                .with(jwt())
                .param("lockName", "Lock 2")
                .param("keyUid", "002");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().string("true")
                );
    }
}