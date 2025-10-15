package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Vol;
import java.util.List;

public interface VolService {
    List<Vol> getAllVols();
    Vol getVolById(Long id);
    Vol createVol(Vol vol);
    Vol updateVol(Long id, Vol vol);
    void deleteVol(Long id);
}
