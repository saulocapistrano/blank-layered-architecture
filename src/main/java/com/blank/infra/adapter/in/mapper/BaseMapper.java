package com.blank.infra.adapter.in.mapper;

import com.blank.domain.model.BaseEntity;
import com.blank.infra.adapter.dto.BaseRequestDTO;
import com.blank.infra.adapter.dto.response.BaseResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BaseMapper {

    public BaseEntity toEntity(BaseRequestDTO dto){

        BaseEntity entity = new BaseEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        return entity                ;
    }

    public BaseResponseDTO toResponseDTO(BaseEntity entity) {
        return new BaseResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );

    }

    public BaseEntity entityToUpdate(BaseEntity entity, BaseRequestDTO dto) {
        if (dto.name() != null && !dto.name().isEmpty()) {
            entity.setName(dto.name());
        }

        if (dto.description() != null && !dto.description().isEmpty()) {
            entity.setDescription(dto.description());
        }

        return entity;
    }
}
