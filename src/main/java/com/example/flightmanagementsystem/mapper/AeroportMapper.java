package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.AeroportDTO;
import com.example.flightmanagementsystem.model.Aeroport;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AeroportMapper {
    
    AeroportDTO toDto(Aeroport aeroport);
    
    @Mapping(target = "volsDepart", ignore = true)
    @Mapping(target = "volsArrivee", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Aeroport toEntity(AeroportDTO aeroportDTO);
    
    List<AeroportDTO> toDtoList(List<Aeroport> aeroports);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "volsDepart", ignore = true)
    @Mapping(target = "volsArrivee", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AeroportDTO aeroportDTO, @MappingTarget Aeroport aeroport);
}
