package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.domain.Parent;
import io.haedoang.propagation.infra.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;

    private final ChildService childService;

    @Transactional(readOnly = true)
    public Long count() {
        return parentRepository.count();
    }

    @Transactional
    public Parent save() {
        Parent parent = parentRepository.save(new Parent());
        log.info("savedParent : {}", parent);
        Child child = childService.save(parent.getId());
        log.info("savedChild: {}", child);
        return parent;
    }

    @Transactional
    public void saveFailByChildThrowRuntimeException() {
        Parent parent = parentRepository.save(new Parent());
        childService.saveAndThrowRuntimeException(parent.getId());
    }

    @Transactional
    public void saveFailByChildThrowRuntimeExceptionCatchParentTransaction() {
        Parent parent = parentRepository.save(new Parent());
        try {
            childService.saveAndThrowRuntimeException(parent.getId());
        } catch (RuntimeException e) {
            log.info("catch Error: {}" , e.getMessage());
        }
    }
}
