package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName : io.haedoang.querydsl.repository
 * fileName : MemberRepository
 * author : haedoang
 * date : 2022-05-19
 * description :
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsername(String username);
}
