package com.recordAI.record_ai.mapper.base;

public interface BaseMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}