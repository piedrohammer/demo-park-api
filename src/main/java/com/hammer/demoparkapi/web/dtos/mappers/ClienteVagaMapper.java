package com.hammer.demoparkapi.web.dtos.mappers;

import com.hammer.demoparkapi.entities.ClienteVaga;
import com.hammer.demoparkapi.web.dtos.EstacionamentoCreateDTO;
import com.hammer.demoparkapi.web.dtos.EstacionamentoResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDTO dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDTO toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDTO.class);
    }
}
