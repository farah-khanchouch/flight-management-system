package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.AvionDTO;
import com.example.flightmanagementsystem.model.Avion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvionMapper {
    
    AvionDTO toDto(Avion avion);
    
    @Mapping(target = "vols", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Avion toEntity(AvionDTO avionDTO);
    
    List<AvionDTO> toDtoList(List<Avion> avions);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vols", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AvionDTO avionDTO, @MappingTarget Avion avion);
}
