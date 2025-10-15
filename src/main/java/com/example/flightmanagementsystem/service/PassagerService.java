package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Passager;
import java.util.List;

public interface PassagerService {
    List<Passager> getAllPassagers();
    Passager getPassagerById(Long id);
    Passager createPassager(Passager passager);
    Passager updatePassager(Long id, Passager passager);
    void deletePassager(Long id);
}
