package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.model.Vol;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VolMapper {
    
    @Mapping(source = "aeroportDepart.id", target = "aeroportDepartId")
    @Mapping(source = "aeroportDepart.nom", target = "aeroportDepartNom")
    @Mapping(source = "aeroportDepart.codeIATA", target = "aeroportDepartCodeIATA")
    @Mapping(source = "aeroportArrivee.id", target = "aeroportArriveeId")
    @Mapping(source = "aeroportArrivee.nom", target = "aeroportArriveeNom")
    @Mapping(source = "aeroportArrivee.codeIATA", target = "aeroportArriveeCodeIATA")
    @Mapping(source = "avion.id", target = "avionId")
    @Mapping(source = "avion.typeAvion", target = "avionType")
    @Mapping(source = "avion.modele", target = "avionModele")
    VolDTO toDto(Vol vol);
    
    @Mapping(source = "aeroportDepartId", target = "aeroportDepart.id")
    @Mapping(source = "aeroportArriveeId", target = "aeroportArrivee.id")
    @Mapping(source = "avionId", target = "avion.id")
    @Mapping(target = "equipages", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Vol toEntity(VolDTO volDTO);
    
    List<VolDTO> toDtoList(List<Vol> vols);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipages", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(VolDTO volDTO, @MappingTarget Vol vol);
}
