package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.service.VolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolController.class)
class VolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VolService volService;

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllVols() throws Exception {
        // Arrange
        VolDTO volDTO = new VolDTO();
        volDTO.setId(1L);
        volDTO.setNumeroVol("AF123");
        volDTO.setPrixBase(100.0);

        when(volService.getAllVols()).thenReturn(Arrays.asList(volDTO));

        // Act & Assert
        mockMvc.perform(get("/api/vols")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].numeroVol").value("AF123"));
    }
}
