package com.blank.domain.service;

import com.blank.domain.exception.ResourceNotFoundException;
import com.blank.domain.model.BaseEntity;
import com.blank.domain.repository.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    private BaseRepository repository;

    public BaseService(BaseRepository repository) {
        this.repository = repository;
    }

    public BaseEntity create(BaseEntity entity){
        if (entity.getName() == null || entity.getName().isEmpty()) {

            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        return repository.save(entity);
    }

    public BaseEntity findById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado com Id: " + id));
    }

     public BaseEntity update(BaseEntity entityWithChanges) {
         BaseEntity existingEntity = findById(entityWithChanges.getId());


         existingEntity.setName(entityWithChanges.getName());
         existingEntity.setDescription(entityWithChanges.getDescription());

         return repository.save(existingEntity);
     }

    public void delete(Long id) {
        findById(id);

        repository.deleteById(id);
    }
}
