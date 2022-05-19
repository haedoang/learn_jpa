package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.dto.MemberSearchCondition;
import io.haedoang.querydsl.dto.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * packageName : io.haedoang.querydsl.repository
 * fileName : MemberRepositoryCustom
 * author : haedoang
 * date : 2022-05-19
 * description :
 */
public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);

    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
