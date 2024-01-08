package com.hammer.demoparkapi.web.dtos.mappers;

import com.hammer.demoparkapi.entities.Cliente;
import com.hammer.demoparkapi.web.dtos.ClienteCreateDTO;
import com.hammer.demoparkapi.web.dtos.ClienteResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDTO dto){
        return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDTO toDTO(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDTO.class);
    }
}
