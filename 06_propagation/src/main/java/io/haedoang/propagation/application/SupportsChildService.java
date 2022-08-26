package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-26
 * description : 현재 트랜잭션을 지원하고 트랜잭션이 없으면 트랜잭션이 없이 실행한다
 */
@Service
public class SupportsChildService extends ChildService {
    public SupportsChildService(ChildRepository childRepository) {
        super(childRepository);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Child save(Long parentId) {
        return super.save(parentId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        super.saveAndThrowRuntimeException(parentId);
    }
}
