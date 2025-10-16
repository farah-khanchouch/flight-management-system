package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.ReservationDTO;
import com.example.flightmanagementsystem.model.Passager;
import com.example.flightmanagementsystem.model.Reservation;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    
    @Mapping(source = "vol.id", target = "volId")
    @Mapping(source = "vol", target = "volCodeVol", qualifiedByName = "getCodeVol")
    @Mapping(source = "vol.aeroportDepart.nom", target = "volAeroportDepart")
    @Mapping(source = "vol.aeroportArrivee.nom", target = "volAeroportArrivee")
    @Mapping(source = "passagers", target = "passagerIds", qualifiedByName = "passagersToIds")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "userUsername")
    ReservationDTO toDto(Reservation reservation);
    
    @Mapping(source = "volId", target = "vol.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "passagers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);
    
    List<ReservationDTO> toDtoList(List<Reservation> reservations);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroReservation", ignore = true)
    @Mapping(target = "vol", ignore = true)
    @Mapping(target = "passagers", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ReservationDTO reservationDTO, @MappingTarget Reservation reservation);
    
    @Named("getCodeVol")
    default String getCodeVol(com.example.flightmanagementsystem.model.Vol vol) {
        return vol != null ? vol.getCodeVol() : null;
    }
    
    @Named("passagersToIds")
    default List<Long> passagersToIds(Set<Passager> passagers) {
        return passagers != null ? 
            passagers.stream().map(Passager::getId).collect(Collectors.toList()) : null;
    }
}
