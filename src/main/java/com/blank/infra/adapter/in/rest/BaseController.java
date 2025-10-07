package com.blank.infra.adapter.in.rest;

import com.blank.domain.model.BaseEntity;
import com.blank.domain.service.BaseService;
import com.blank.infra.adapter.dto.BaseRequestDTO;
import com.blank.infra.adapter.dto.response.BaseResponseDTO;
import com.blank.infra.adapter.in.mapper.BaseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/base")
public class BaseController {

    private final BaseService baseService;
    private final BaseMapper mapper;

    public BaseController(BaseService baseService, BaseMapper mapper) {
        this.baseService = baseService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<BaseResponseDTO> createBase(@RequestBody BaseRequestDTO request){

        BaseEntity entity = mapper.toEntity(request);

        BaseEntity createdEntity = baseService.create(entity);

        BaseResponseDTO responseDTO = mapper.toResponseDTO(createdEntity);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> getById(@PathVariable Long id){
        BaseEntity entity = baseService.findById(id);
        return  ResponseEntity.ok(mapper.toResponseDTO(entity));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> putById(@PathVariable Long id,
                                                   @RequestBody BaseRequestDTO request) {

        BaseEntity existingEntity = baseService.findById(id);

        BaseEntity entityWithChanges = mapper.entityToUpdate(existingEntity, request);

        BaseEntity updatedEntity = baseService.update(entityWithChanges);

        BaseResponseDTO responseDTO = mapper.toResponseDTO(updatedEntity);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteById(@PathVariable Long id){
        baseService.delete(id);
        return ResponseEntity.noContent().build();

    }

}
