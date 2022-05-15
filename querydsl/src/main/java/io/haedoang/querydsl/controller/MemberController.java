package io.haedoang.querydsl.controller;

import io.haedoang.querydsl.dto.MemberSearchCondition;
import io.haedoang.querydsl.dto.MemberTeamDto;
import io.haedoang.querydsl.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author : haedoang
 * date : 2022/05/15
 * description :
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }
}
