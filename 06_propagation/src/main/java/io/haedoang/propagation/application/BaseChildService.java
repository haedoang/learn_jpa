package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
public interface BaseChildService {
    Long count();
    Child save(Long parentId);

    void saveAndThrowRuntimeException(Long parentId) throws RuntimeException;
}
