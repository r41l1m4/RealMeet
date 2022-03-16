package com.ironia.realmeet.mapper;

import com.ironia.realmeet.api.model.CreateRoomDTO;
import com.ironia.realmeet.api.model.RoomDTO;
import com.ironia.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {

    public abstract RoomDTO fromEntityToDto(Room room);

    public abstract Room fromCreateRoomDtoToEntity(CreateRoomDTO createRoomDTO);
}
