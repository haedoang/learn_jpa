package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-25
 * description :
 */
@Primary
@Service
public class RequiredChildService extends ChildService {

    public RequiredChildService(ChildRepository childRepository) {
        super(childRepository);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Child save(Long parentId) {
        return super.save(parentId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        super.saveAndThrowRuntimeException(parentId);
    }
}
