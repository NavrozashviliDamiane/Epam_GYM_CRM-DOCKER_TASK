package com.epam.trainerworkload.unit.config;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpointAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/test"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testProtectedEndpointAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/protected/test"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
