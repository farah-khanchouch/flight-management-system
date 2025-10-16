package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.EquipageDTO;
import com.example.flightmanagementsystem.model.Equipage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EquipageMapper {
    
    EquipageDTO toDto(Equipage equipage);
    
    @Mapping(target = "vols", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Equipage toEntity(EquipageDTO equipageDTO);
    
    List<EquipageDTO> toDtoList(List<Equipage> equipages);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vols", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(EquipageDTO equipageDTO, @MappingTarget Equipage equipage);
}
