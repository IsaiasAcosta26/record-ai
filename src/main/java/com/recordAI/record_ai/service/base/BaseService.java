package com.recordAI.record_ai.service.base;

import java.util.List;

public interface BaseService<D> {
    List<D> findAll();
    D findById(Long id);
    D create(D dto);
    D update(Long id, D dto);
    void delete(Long id);
}
