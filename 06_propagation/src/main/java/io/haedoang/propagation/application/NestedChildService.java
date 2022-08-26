package io.haedoang.propagation.application;

import io.haedoang.propagation.domain.Child;
import io.haedoang.propagation.infra.ChildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * author : haedoang
 * date : 2022-08-26
 * description : 현재 트랜잭션이 있으면 중첩된 트랜잭션 내에서 실행하고, 그렇지 않으면 REQIRED 처럼 동작한다
 */
@Service
public class NestedChildService extends ChildService {
    public NestedChildService(ChildRepository childRepository) {
        super(childRepository);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public Child save(Long parentId) {
        return super.save(parentId);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void saveAndThrowRuntimeException(Long parentId) throws RuntimeException {
        super.saveAndThrowRuntimeException(parentId);
    }
}
