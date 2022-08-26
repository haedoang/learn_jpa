package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-26
 * description :
 */
@Service
public class MandatoryChildService extends ChildService {

    public MandatoryChildService(ChildRepository childRepository) {
        super(childRepository);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Child save(Long parentId) {
        return super.save(parentId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        super.saveAndThrowRuntimeException(parentId);
    }
}
