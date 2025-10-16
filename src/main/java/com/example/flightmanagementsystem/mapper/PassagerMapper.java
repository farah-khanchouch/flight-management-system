package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.PassagerDTO;
import com.example.flightmanagementsystem.model.Passager;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassagerMapper {
    
    @Mapping(source = "user.id", target = "userId")
    PassagerDTO toDto(Passager passager);
    
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Passager toEntity(PassagerDTO passagerDTO);
    
    List<PassagerDTO> toDtoList(List<Passager> passagers);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PassagerDTO passagerDTO, @MappingTarget Passager passager);
}
