package com.recordAI.record_ai.service.base;

import com.recordAI.record_ai.mapper.base.BaseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<E, D> implements BaseService<D> {

    protected final JpaRepository<E, Long> repository;
    protected final BaseMapper<D, E> mapper;

    @Override
    public List<D> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public D findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id " + id));
    }

    @Override
    public D create(D dto) {
        E entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public D update(Long id, D dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found with id " + id);
        }
        E entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found with id " + id);
        }
        repository.deleteById(id);
    }
}
