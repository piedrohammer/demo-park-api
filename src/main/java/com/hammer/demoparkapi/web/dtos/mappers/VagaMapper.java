package com.hammer.demoparkapi.web.dtos.mappers;

import com.hammer.demoparkapi.entities.Vaga;
import com.hammer.demoparkapi.web.dtos.VagaCreateDTO;
import com.hammer.demoparkapi.web.dtos.VagaResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDTO dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDTO toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDTO.class);
    }
}
