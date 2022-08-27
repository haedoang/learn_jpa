package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NeverChildService implements ChildService {
    private final ChildRepository childRepository;

    @Transactional(readOnly = true)
    public Long count() {
        return childRepository.count();
    }

    @Transactional(propagation = Propagation.NEVER)
    public Child save(Long parentId) {
        return childRepository.save(new Child(parentId));
    }


    @Transactional(propagation = Propagation.NEVER)
    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        childRepository.save(new Child(parentId));
        log.error("throw Error!");
        throw new RuntimeException("saveAndThrowRuntimeException Error");
    }
}
