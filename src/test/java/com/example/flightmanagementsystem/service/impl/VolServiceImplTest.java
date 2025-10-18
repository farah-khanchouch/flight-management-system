package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import com.example.flightmanagementsystem.repository.VolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolServiceImplTest {

    @Mock
    private VolRepository volRepository;

    @InjectMocks
    private VolServiceImpl volService;

    private Vol vol;
    private VolDTO volDTO;

    @BeforeEach
    void setUp() {
        vol = new Vol();
        vol.setId(1L);
        vol.setNumeroVol("AF123");
        vol.setStatut(StatutVol.PROGRAMME);
        vol.setPrixBase(100.0);

        volDTO = new VolDTO();
        volDTO.setId(1L);
        volDTO.setNumeroVol("AF123");
        volDTO.setPrixBase(100.0);
    }

    @Test
    void testGetAllVols() {
        // Arrange
        when(volRepository.findAll()).thenReturn(Arrays.asList(vol));

        // Act
        List<VolDTO> result = volService.getAllVols();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(volDTO.getNumeroVol(), result.get(0).getNumeroVol());
        verify(volRepository, times(1)).findAll();
    }

    @Test
    void testGetVolById() {
        // Arrange
        when(volRepository.findById(1L)).thenReturn(Optional.of(vol));

        // Act
        VolDTO result = volService.getVolById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(volDTO.getId(), result.getId());
        verify(volRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateVol() {
        // Arrange
        when(volRepository.save(any(Vol.class))).thenReturn(vol);

        // Act
        VolDTO result = volService.createVol(volDTO);

        // Assert
        assertNotNull(result);
        assertEquals(volDTO.getNumeroVol(), result.getNumeroVol());
        verify(volRepository, times(1)).save(any(Vol.class));
    }
}
