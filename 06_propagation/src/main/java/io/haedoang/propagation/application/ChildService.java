package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@Slf4j
@RequiredArgsConstructor
public abstract class ChildService {
    private final ChildRepository childRepository;

    @Transactional(readOnly = true)
    public Long count() {
        return childRepository.count();
    }

    public Child save(Long parentId) {
        return childRepository.save(new Child(parentId));
    }

    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        childRepository.save(new Child(parentId));
        log.error("throw Error!");
        throw new RuntimeException("saveAndThrowRuntimeException Error");
    }
}
